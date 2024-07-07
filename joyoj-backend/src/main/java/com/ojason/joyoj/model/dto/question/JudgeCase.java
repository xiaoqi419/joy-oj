package com.ojason.joyoj.model.dto.question;

import lombok.Data;

/**
 * @program: joyoj-backend
 * @ClassName: JudgeCase
 * @description: 题目用例
 * @author: Jason
 * @create: 2024-06-23 20:02
 * @from: <a href="https://www.ojason.top">我的博客</a>
 */
@Data
public class JudgeCase {

    /**
     * 输入用例
     */
    private String input;

    /**
     * 输出用例
     */
    private String output;
}
