package com.ojason.joyoj.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolutionTagsVO implements Serializable {

    /**
     * 标签ID
     */
    private Integer id;

    /**
     * 标签名称
     */
    private String tagName;
}
