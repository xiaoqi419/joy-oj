package com.ojason.joyoj.judge.codesandbox;

import com.ojason.joyoj.judge.codesandbox.impl.ExampleCodeSandboxImpl;
import com.ojason.joyoj.judge.codesandbox.impl.RemoteCodeSandboxImpl;
import com.ojason.joyoj.judge.codesandbox.impl.ThirdPartyCodeSandboxImpl;

/**
 * @program: joyoj-backend
 * @ClassName: CodeSandboxFactory
 * @description: 代码沙箱工厂（根据字符串参数创建指定的代码沙箱实例）
 * @author: Jason
 * @create: 2024-07-12 16:08
 * @from: <a href="https://www.ojason.top">我的博客</a>
 */
public class CodeSandboxFactory {

    /**
     * 创建代码沙箱实例
     *
     * @param type 沙箱类型
     * @return
     */
    public static CodeSandbox newInstance(String type) {
        switch (type) {
            case "third-party":
                return new ThirdPartyCodeSandboxImpl();
            case "remote":
                return new RemoteCodeSandboxImpl();
            case "example":
            default:
                return new ExampleCodeSandboxImpl();
        }
    }
}
