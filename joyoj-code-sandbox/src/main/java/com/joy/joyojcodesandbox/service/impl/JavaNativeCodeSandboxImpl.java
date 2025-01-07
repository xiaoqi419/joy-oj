package com.joy.joyojcodesandbox.service.impl;

import com.joy.joyojcodesandbox.docker.JavaNativeCodeSandbox;
import com.joy.joyojcodesandbox.model.ExecuteCodeRequest;
import com.joy.joyojcodesandbox.model.ExecuteCodeResponse;
import com.joy.joyojcodesandbox.service.CodeSandbox;
import org.springframework.stereotype.Service;

/**
 * @program: joyoj-code-sandbox
 * @ClassName: JavaNativeCodeSandboxImpl
 * @description:
 * @author: Jason
 * @create: 2024-07-15 13:59
 * @from: <a href="https://www.ojason.top">我的博客</a>
 */
@Service
public class JavaNativeCodeSandboxImpl implements CodeSandbox {


    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
//        JavaDockerCodeSandbox codeSandbox = new JavaDockerCodeSandbox();
        JavaNativeCodeSandbox codeSandbox = new JavaNativeCodeSandbox();
        return codeSandbox.executeCode(executeCodeRequest);
    }
}
