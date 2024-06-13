package com.ojason.joyoj.service;

import com.ojason.joyoj.common.BaseResponse;

import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName : VerifyService
 * @Description : 验证服务
 * @Author : Jason
 * @Date: 2024-06-10 19:16
 */
public interface VerifyService {


    /**
     * 获取验证码
     *
     * @param response 响应
     */
    void getArithmetic(HttpServletResponse response);

    /**
     * 验证验证码
     */
    boolean verifyCaptcha(String captchaCode);

    /**
     * 获取邮箱验证码
     */
    BaseResponse<Boolean> getEmailCode(String email);
}
