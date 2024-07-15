package com.joy.joyojcodesandbox.model;

import lombok.Data;

/**
 * @program: joyoj-code-sandbox
 * @ClassName: ExecuteMessage
 * @description: 进程执行信息
 * @author: Jason
 * @create: 2024-07-15 14:51
 * @from: <a href="https://www.ojason.top">我的博客</a>
 */
@Data
public class ExecuteMessage {

    private Integer exitValue;

    private String message;

    private String errorMessage;

    private Long time;
}
