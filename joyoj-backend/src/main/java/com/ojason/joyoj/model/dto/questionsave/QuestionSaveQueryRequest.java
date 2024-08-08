package com.ojason.joyoj.model.dto.questionsave;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 */
@Data
public class QuestionSaveQueryRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 题目 id
     */
    private Long questionId;
    /**
     * 题目 id
     */
    private Long userId;
}