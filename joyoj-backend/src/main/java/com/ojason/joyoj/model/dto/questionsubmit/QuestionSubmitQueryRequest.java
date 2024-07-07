package com.ojason.joyoj.model.dto.questionsubmit;

import com.ojason.joyoj.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询请求
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionSubmitQueryRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 编程语言
     */
    private String language;
    /**
     * 提交状态
     */
    private Integer status;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 题目 id
     */
    private Long questionId;
}