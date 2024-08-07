package com.joy.joyojcodesandbox.service;

import com.joy.joyojcodesandbox.model.ExecuteCodeRequest;
import com.joy.joyojcodesandbox.model.ExecuteCodeResponse;

/**
 * 代码沙箱接口定义
 */
public interface CodeSandbox {

    /**
     * 执行代码
     *
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
