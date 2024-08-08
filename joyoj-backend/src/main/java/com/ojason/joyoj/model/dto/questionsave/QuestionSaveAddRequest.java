package com.ojason.joyoj.model.dto.questionsave;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 */
@Data
public class QuestionSaveAddRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 编程语言
     */
    private String language;
    /**
     * 用户代码
     */
    private String code;
    /**
     * 题目 id
     */
    private Long questionId;
    /**
     * 题目 id
     */
    private Long userId;
}