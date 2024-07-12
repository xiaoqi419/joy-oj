package com.ojason.joyoj.judge.codesandbox;

import com.ojason.joyoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.ojason.joyoj.judge.codesandbox.model.ExecuteCodeResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: joyoj-backend
 * @ClassName: CodeSandboxProxy
 * @description: 代码沙箱代理
 * @author: Jason
 * @create: 2024-07-12 16:38
 * @from: <a href="https://www.ojason.top">我的博客</a>
 */
@Slf4j
@AllArgsConstructor
public class CodeSandboxProxy implements CodeSandbox {

    private CodeSandbox codeSandbox;

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("代码沙箱请求信息：{}", executeCodeRequest.toString());
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        log.info("代码沙箱响应信息： {}", executeCodeResponse);
        return executeCodeResponse;
    }
}
