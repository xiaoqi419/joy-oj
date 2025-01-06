package com.joy.joyojcodesandbox.docker;

import com.joy.joyojcodesandbox.model.ExecuteCodeRequest;
import com.joy.joyojcodesandbox.model.ExecuteCodeResponse;
import com.joy.joyojcodesandbox.model.ExecuteMessage;

import java.util.List;

/**
 * 使用模板方法设计模式方便多语言调用
 */
public abstract class JavaCodeSandboxTemplate {

    protected ExecuteCodeRequest executeCodeRequest;

    // 构造函数传递参数
    protected JavaCodeSandboxTemplate(ExecuteCodeRequest executeCodeRequest) {
        this.executeCodeRequest = executeCodeRequest;
    }

    // 定义流程
    public ExecuteCodeResponse execute() {

        saveCodeToFile();
        compileCode();
        uploadToContainer();
        List<ExecuteMessage> executeMessages = executeInContainer();
        ExecuteCodeResponse outputResponse = collectOutput(executeMessages);
        if (outputResponse.getOutputList() != null) {
            cleanup();
        }
        System.out.println("判题结果： " + outputResponse);
        return outputResponse;
    }

    /**
     * 将用户的代码保存为文件
     */
    protected abstract void saveCodeToFile();

    /**
     * 编译代码，得到 class 文件
     */
    protected abstract void compileCode();

    /**
     * 把编译好的文件上传到容器环境内
     */
    protected abstract void uploadToContainer();

    /**
     * 在容器中执行代码，得到输出结果
     */
    protected abstract List<ExecuteMessage> executeInContainer();

    /**
     * 收集整理输出结果
     */
    protected abstract ExecuteCodeResponse collectOutput(List<ExecuteMessage> executeMessageList);

    /**
     * 文件清理，释放空间
     */
    protected abstract void cleanup();

    /**
     * 错误处理，提升程序健壮性
     *
     * @param e 错误
     */
    protected abstract ExecuteCodeResponse handleError(Exception e);
}
