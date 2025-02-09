package com.ojason.joyoj.model.dto.solution;

import lombok.Data;

import java.io.Serializable;

@Data
public class SolutionTagsRequest implements Serializable {

    /**
     * 标签名称
     */
    private String tagName;
}
