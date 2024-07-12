package com.ojason.joyoj.judge.codesandbox.impl;

import com.ojason.joyoj.judge.codesandbox.CodeSandbox;
import com.ojason.joyoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.ojason.joyoj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * @program: joyoj-backend
 * @ClassName: CodeSandboxImpl
 * @description: 第三方代码沙箱
 * @author: Jason
 * @create: 2024-07-12 15:51
 * @from: <a href="https://www.ojason.top">我的博客</a>
 */
public class ThirdPartyCodeSandboxImpl implements CodeSandbox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
