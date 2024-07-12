package com.ojason.joyoj.judge.codesandbox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: joyoj-backend
 * @ClassName: ExecuteCodeRequest
 * @description:
 * @author: Jason
 * @create: 2024-07-12 15:36
 * @from: <a href="https://www.ojason.top">我的博客</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeRequest {

    private List<String> inputList;

    private String code;

    private String language;
}
