package com.ojason.joyoj.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName tags
 */
@TableName(value = "tags")
@Data
public class Tags implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 标签ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 标签名称
     */
    private String tagName;
    /**
     * 所属分类ID
     */
    private Integer categoryId;
    /**
     * 删除标记 0-正常 1-删除
     */
    private Integer isDelete;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 更新时间
     */
    private Date updatedTime;

    
}