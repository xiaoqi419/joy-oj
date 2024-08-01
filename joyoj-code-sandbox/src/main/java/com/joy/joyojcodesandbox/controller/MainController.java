package com.joy.joyojcodesandbox.controller;

import com.joy.joyojcodesandbox.common.ErrorCode;
import com.joy.joyojcodesandbox.exception.BusinessException;
import com.joy.joyojcodesandbox.model.ExecuteCodeRequest;
import com.joy.joyojcodesandbox.model.ExecuteCodeResponse;
import com.joy.joyojcodesandbox.service.CodeSandbox;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @program: joyoj-code-sandbox
 * @ClassName: MainController
 * @description: 主控制器
 * @author: Jason
 * @create: 2024-07-12 20:18
 * @from: <a href="https://www.ojason.top">我的博客</a>
 */
@RestController("/")
public class MainController {


    private static final String AUTH_HEARD = "authorization";
    private static final String AUTH_REQUEST_SECRET = "jason";

    @Resource
    private CodeSandbox codeSandbox;


    /**
     * 执行代码
     *
     * @param executeCodeRequest 请求参数
     * @return 执行结果
     */
    @PostMapping("/executeCode")
    ExecuteCodeResponse executeCode(@RequestBody ExecuteCodeRequest executeCodeRequest, HttpServletRequest httpServletRequest) {
        String authHeader = httpServletRequest.getHeader(AUTH_HEARD);
        if (!AUTH_REQUEST_SECRET.equals(authHeader)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无权限访问，请提供正确的secretKey");
        }
        if (executeCodeRequest == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "参数不能为空");
        }
        return codeSandbox.executeCode(executeCodeRequest);
    }

}
