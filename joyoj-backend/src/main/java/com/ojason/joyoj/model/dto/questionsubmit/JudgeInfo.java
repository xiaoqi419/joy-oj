package com.ojason.joyoj.model.dto.questionsubmit;

import lombok.Data;

/**
 * @program: joyoj-backend
 * @ClassName: JudgeInfo
 * @description: 判题信息
 * @author: Jason
 * @create: 2024-06-23 20:20
 * @from: <a href="https://www.ojason.top">我的博客</a>
 */
@Data
public class JudgeInfo {

    /**
     * 判题结果
     */
    private String message;

    /**
     * 执行内存
     */
    private Long memory;

    /**
     * 消耗时间
     */
    private Long time;
}
