package com.ojason.joyoj.judge.codesandbox;

import com.ojason.joyoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.ojason.joyoj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * @ClassName : CodeSandbox
 * @Description : 代码沙箱
 * @Author : Jason
 * @Date: 2024-07-12 15:03
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
