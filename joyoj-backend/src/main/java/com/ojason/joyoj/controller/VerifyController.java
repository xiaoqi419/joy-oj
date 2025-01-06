package com.ojason.joyoj.controller;

import com.ojason.joyoj.common.BaseResponse;
import com.ojason.joyoj.common.ErrorCode;
import com.ojason.joyoj.exception.BusinessException;
import com.ojason.joyoj.service.VerifyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 验证码接口
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
    public BaseResponse<String> getArithmetic(HttpServletRequest request, HttpServletResponse response) {
        return verifyService.getArithmetic(request, response);
    }

    /**
     * 获取邮箱验证码
     */
    @GetMapping("/email")
    public BaseResponse<Boolean> getEmailCode(@RequestParam("email") String email) {
        if (StringUtils.isBlank(email)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return verifyService.getEmailCode(email);
    }
}
