package com.ojason.joyoj.service.impl;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.session.SaSessionCustomUtil;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
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
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 100, 4, 20);
        response.setContentType("image/png");
        // 重新生成code
        captcha.createCode();
        // 输出code到浏览器的流
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            captcha.write(outputStream);
            SaSession session = SaSessionCustomUtil.getSessionById("CaptchaCode");
            session.set("code", captcha.getCode());
        } catch (IOException e) {
            throw new IllegalArgumentException("验证码获取失败");
        }

    }

    @Override
    public boolean verifyCaptcha(String captchaCode) {
        SaSession session = SaSessionCustomUtil.getSessionById("CaptchaCode");
        String code = (String) session.get("code");
        // 不区分大小写
        if (!code.equalsIgnoreCase(captchaCode)) {
            throw new IllegalArgumentException("验证码错误");
        }
        return true;
    }
}
