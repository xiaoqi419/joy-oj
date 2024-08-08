package com.ojason.joyoj.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * 题目代码返回包装类
 */
@Data
public class QuestionSaveVO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /**
     * 代码片段
     */
    private String code;

}