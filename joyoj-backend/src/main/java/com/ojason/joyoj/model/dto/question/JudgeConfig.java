package com.ojason.joyoj.model.dto.question;

import lombok.Data;

/**
 * @program: joyoj-backend
 * @ClassName: JudgeConfig
 * @description: 题目配置
 * @author: Jason
 * @create: 2024-06-23 20:03
 * @from: <a href="https://www.ojason.top">我的博客</a>
 */
@Data
public class JudgeConfig {

    /**
     * 内存限制(KB)
     */
    private Long memoryLimit;

    /**
     * 时间限制(ms)
     */
    private Long timeLimit;

    /**
     * 堆栈限制(KB)
     */
    private Long stackLimit;
}
