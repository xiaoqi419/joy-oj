package com.joy.joyojcodesandbox.service;

import com.joy.joyojcodesandbox.model.ExecuteCodeRequest;
import com.joy.joyojcodesandbox.model.ExecuteCodeResponse;

/**
 * @ClassName : CodeSandbox
 * @Description :
 * @Author : Jason
 * @Date: 2024-07-15 13:58
 */
public interface CodeSandbox {

    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);

    
}
