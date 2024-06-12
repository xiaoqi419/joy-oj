package com.ojason.joyoj.controller;

import com.ojason.joyoj.service.VerifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @program: joyoj-backend
 * @ClassName: VerifyController
 * @description: 验证控制器
 * @author: Jason
 * @create: 2024-06-10 17:15
 * @from: <a href="https://www.ojason.top">我的博客</a>
 */
@RestController
@RequestMapping("/verify")
@Slf4j
public class VerifyController {

    private final VerifyService verifyService;

    @Autowired
    public VerifyController(VerifyService verifyService) {
        this.verifyService = verifyService;
    }

    /**
     * 获取验证码
     */
    @GetMapping("/captcha")
    public void getArithmetic(HttpServletResponse response) {
        verifyService.getArithmetic(response);
    }
}
