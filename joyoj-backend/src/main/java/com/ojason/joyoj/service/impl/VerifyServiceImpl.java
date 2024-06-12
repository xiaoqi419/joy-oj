package com.ojason.joyoj.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.captcha.generator.MathGenerator;
import com.ojason.joyoj.service.VerifyService;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: joyoj-backend
 * @ClassName: VerifyServiceImpl
 * @description:
 * @author: Jason
 * @create: 2024-06-12 22:17
 * @from: <a href="https://www.ojason.top">我的博客</a>
 */
@Service
public class VerifyServiceImpl implements VerifyService {


    @Override
    public void getArithmetic(HttpServletResponse response) {
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(200, 45, 4, 4);
        response.setContentType("image/png");
        // 自定义验证码内容为四则运算方式
        captcha.setGenerator(new MathGenerator());
        // 重新生成code
        captcha.createCode();
        // 输出code到浏览器的流
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            captcha.write(outputStream);
            StpUtil.getSession().set("CaptchaCode", captcha.getCode());
        } catch (IOException e) {
            throw new IllegalArgumentException("验证码获取失败");
        }

    }
}
