package com.joy.joyojcodesandbox.docker;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.*;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.ExecStartResultCallback;
import com.joy.joyojcodesandbox.common.ErrorCode;
import com.joy.joyojcodesandbox.exception.BusinessException;
import com.joy.joyojcodesandbox.model.ExecuteCodeRequest;
import com.joy.joyojcodesandbox.model.ExecuteCodeResponse;
import com.joy.joyojcodesandbox.model.ExecuteMessage;
import com.joy.joyojcodesandbox.model.JudgeInfo;
import com.joy.joyojcodesandbox.utils.ProcessUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

@Slf4j
public class JavaCodeExecution extends JavaCodeSandboxTemplate {

    private static final String GLOBAL_CODE_DIR_NAME = "tmpCode";
    private static final String GLOBAL_JAVA_CLASS_NAME = "Main.java";
    private static final Boolean FIRST_INIT = true;
    private static final Long TIME_OUT = 5000L;

    private File userCodeFile;
    private String userCodeParentPath;
    private String containerId;
    private DockerClient dockerClient;

    public JavaCodeExecution(ExecuteCodeRequest executeCodeRequest) {
        super(executeCodeRequest);
    }

    @Override
    protected void saveCodeToFile() {
        String code = executeCodeRequest.getCode();
        String language = executeCodeRequest.getLanguage();

        String userDir = System.getProperty("user.dir");
        String globalCodePathName = userDir + File.separator + GLOBAL_CODE_DIR_NAME;
        // 判断全局代码目录是否存在，没有则新建
        if (!FileUtil.exist(globalCodePathName)) {
            FileUtil.mkdir(globalCodePathName);
        }
        // 把用户的代码隔离存放
        userCodeParentPath = globalCodePathName + File.separator + UUID.randomUUID();
        String userCodePath = userCodeParentPath + File.separator + GLOBAL_JAVA_CLASS_NAME;
        userCodeFile = FileUtil.writeString(code, userCodePath, StandardCharsets.UTF_8);
    }

    @Override
    protected void compileCode() {
        String compileCmd = String.format("javac -encoding utf-8 %s", userCodeFile.getAbsolutePath());
        try {
            Process compileProcess = Runtime.getRuntime().exec(compileCmd);
            ExecuteMessage executeMessage = ProcessUtils.runProcessAndGetMessage(compileProcess, "编译");
            System.out.println(executeMessage);
        } catch (Exception e) {
            handleError(e);
        }
    }

    @Override
    protected void uploadToContainer() {
        // 拼接路径
//        DockerClientConfig config = DefaultDockerClientConfig
//                .createDefaultConfigBuilder()
//                .withDockerHost("tcp://110.40.61.114:2375")
//                .withDockerTlsVerify(false)
//                .build();
//        DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
//                .dockerHost(config.getDockerHost())
//                .sslConfig(config.getSSLConfig())
//                .maxConnections(100)
//                .connectionTimeout(Duration.ofSeconds(30))
//                .responseTimeout(Duration.ofSeconds(45))
//                .build();
        dockerClient = DockerClientBuilder.getInstance().build();
        String image = "openjdk:8-alpine";
        if (Boolean.TRUE.equals(FIRST_INIT)) {
            PullImageCmd pullImageCmd = dockerClient.pullImageCmd(image);
            PullImageResultCallback pullImageResultCallback = new PullImageResultCallback() {
                @Override
                public void onNext(PullResponseItem item) {
                    System.out.println("下载镜像：" + item.getStatus());
                    super.onNext(item);
                }
            };
            try {
                pullImageCmd
                        .exec(pullImageResultCallback)
                        .awaitCompletion();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("下载完成");
        // 创建容器
        CreateContainerCmd containerCmd = dockerClient.createContainerCmd(image);
        HostConfig hostConfig = new HostConfig();
        hostConfig.withMemory(100 * 1000 * 1000L);
        hostConfig.withCpuCount(1L);
        hostConfig.withMemorySwap(0L);
        hostConfig.setBinds(new Bind(userCodeParentPath, new Volume("/home/oj")));
        UUID randomUUID = UUID.randomUUID();
        CreateContainerResponse containerResponse = containerCmd
                .withName("joy-docker-java" + "-" + randomUUID)
                .withHostConfig(hostConfig)
                .withAttachStdin(true)
                .withAttachStderr(true)
                .withAttachStdout(true)
                .withReadonlyRootfs(true)
                .withNetworkDisabled(true)
                .withTty(true)
                .exec();
        containerId = containerResponse.getId();
    }

    @Override
    protected List<ExecuteMessage> executeInContainer() {
        // 启动容器
        dockerClient.startContainerCmd(containerId).exec();
        List<String> inputList = executeCodeRequest.getInputList();
        List<ExecuteMessage> executeMessageList = new ArrayList<>();
        for (String inputArgs : inputList) {
            StopWatch stopWatch = new StopWatch();
            String[] inputArgsArray = inputArgs.split(" ");
            String[] cmdArray = ArrayUtil.append(new String[]{"java", "-cp", "/home/oj", "Main"}, inputArgsArray);
            ExecCreateCmdResponse execCreateCmdResponse = dockerClient.execCreateCmd(containerId)
                    .withCmd(cmdArray)
                    .withAttachStderr(true)
                    .withAttachStdin(true)
                    .withAttachStdout(true)
                    .exec();
            System.out.println("创建执行命令：" + execCreateCmdResponse);
            String execId = execCreateCmdResponse.getId();
            ExecuteMessage executeMessage = new ExecuteMessage();
            final String[] message = {null};
            final String[] errorMessage = {null};
            final CountDownLatch latch = new CountDownLatch(1);
            long time = 0L;
            final boolean[] timeout = {true};
            ExecStartResultCallback execStartResultCallback = new ExecStartResultCallback() {

                @Override
                public void onComplete() {
                    timeout[0] = false;
                    super.onComplete();
                    latch.countDown(); // 当 message 有值时，释放主线程v
                }

                @Override
                public void onNext(Frame frame) {
                    StreamType streamType = frame.getStreamType();
                    if (StreamType.STDERR.equals(streamType)) {
                        errorMessage[0] = new String(frame.getPayload());
                        System.out.println("输出错误结果：" + message[0]);
                    } else {
                        message[0] = new String(frame.getPayload());
                        System.out.println("输出结果：" + message[0]);
                    }
                    super.onNext(frame);
                }
            };

            final long[] maxMemory = {0L};
            // 获取占用内存
            StatsCmd statsCmd = dockerClient.statsCmd(containerId);
            statsCmd.exec(new ResultCallback<Statistics>() {
                @Override
                public void onStart(Closeable closeable) {

                }

                @Override
                public void onNext(Statistics statistics) {
                    maxMemory[0] = Math.max(statistics.getMemoryStats().getUsage(), maxMemory[0]);
                }

                @Override
                public void onError(Throwable throwable) {

                }

                @Override
                public void onComplete() {

                }

                @Override
                public void close() throws IOException {

                }
            });

            stopWatch.start();
            dockerClient.execStartCmd(execId)
                    .exec(execStartResultCallback);
            stopWatch.stop();
            time = stopWatch.getLastTaskTimeMillis();
            statsCmd.close();
            try {
                // 等待 message 有值
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            executeMessage.setMessage(message[0]);
            executeMessage.setErrorMessage(errorMessage[0]);
            executeMessage.setTime(time);
            executeMessage.setMemory(maxMemory[0]);
            executeMessageList.add(executeMessage);
        }

        return executeMessageList;
    }

    @Override
    protected ExecuteCodeResponse collectOutput(List<ExecuteMessage> executeMessageList) {
        // 收集整理输出结果
        System.out.println("列表：" + executeMessageList);
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        List<String> outputList = new ArrayList<>();
        long maxTime = 0L;
        long maxMemory = 0L;
        for (ExecuteMessage executeMessage : executeMessageList) {
            String errorMessage = executeMessage.getErrorMessage();
            if (StrUtil.isNotBlank(executeMessage.getErrorMessage())) {
                executeCodeResponse.setMessage(errorMessage);
                // 用户提交代码执行中存在错误
                executeCodeResponse.setStatus(3);
                break;
            }
            outputList.add(executeMessage.getMessage());
            Long time = executeMessage.getTime();
            Long memory = executeMessage.getMemory();
            if (time != null) {
                maxTime = Math.max(maxTime, time);
                maxMemory = Math.max(maxMemory, memory);
            }
        }
        try {
            outputList = outputList.stream()
                    .map(line -> line.replace("\n", "").replace("\r", ""))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            executeCodeResponse = handleError(e);
        }
        executeCodeResponse.setOutputList(outputList);
        executeCodeResponse.setMessage("");
        // 正常运行
        executeCodeResponse.setStatus(1);
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMemory(maxTime);
        judgeInfo.setTime(maxTime);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }

    @Override
    protected void cleanup() {
        if (userCodeFile.getParentFile() != null) {
            boolean del = FileUtil.del(userCodeParentPath);
            RemoveContainerCmd removeContainerCmd = dockerClient.removeContainerCmd(containerId);
            removeContainerCmd.withForce(true).exec();
            System.out.println("删除" + (del ? "成功" : "失败"));
            if (!del) {
                log.error("deleteFile error, userCodeFilePath = {}", userCodeFile.getAbsolutePath());
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "删除用户代码文件失败");
            }
        }
    }

    @Override
    protected ExecuteCodeResponse handleError(Exception e) {
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(new ArrayList<>());
        executeCodeResponse.setMessage(e.getMessage());
        // 代码沙箱错误
        executeCodeResponse.setStatus(2);
        executeCodeResponse.setJudgeInfo(new JudgeInfo());
        return executeCodeResponse;
    }

}
