package com.ojason.joyoj.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 题目代码保存
 *
 * @TableName question_save
 */
@TableName(value = "question_save")
@Data
public class QuestionSave implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Integer id;
    /**
     * 题目id
     */
    private Integer questionId;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 代码片段
     */
    private String code;
}