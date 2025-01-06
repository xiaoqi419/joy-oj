package com.ojason.joyoj.service.impl;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.session.SaSessionCustomUtil;
import com.ojason.joyoj.common.BaseResponse;
import com.ojason.joyoj.common.ErrorCode;
import com.ojason.joyoj.common.ResultUtils;
import com.ojason.joyoj.exception.BusinessException;
import com.ojason.joyoj.service.VerifyService;
import com.ojason.joyoj.utils.RandomSmsNumUtils;
import com.ojason.joyoj.utils.RegExpUtil;
import io.springboot.captcha.GifCaptcha;
import io.springboot.captcha.base.Captcha;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.ojason.joyoj.constant.RedisConstant.SEND_EMAIL_CODE;

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

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private TemplateEngine templateEngine;
    @Resource
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String username;

    @Override
    public BaseResponse<String> getArithmetic(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 三个参数分别为宽、高、位数
            GifCaptcha captcha = new GifCaptcha(130, 48, 4);
            // 设置类型,字母数字混合
            captcha.setCharType(Captcha.TYPE_NUM_AND_UPPER);
            SaSession session = SaSessionCustomUtil.getSessionById("CaptchaCode");
            session.set("code", captcha.text());
            // 输出图片流
            return ResultUtils.success(captcha.toBase64());
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, e.getMessage());
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
        // 如果验证码正确，清除session
        session.clear();
        return true;
    }

    @Override
    public BaseResponse<Boolean> getEmailCode(String email) {
        // 校验邮箱
        RegExpUtil.regExpVerify(RegExpUtil.emailRegExp, email, "邮箱格式错误");
        // 从redis中查看有没有该邮箱的验证码
        String verifyCode = (String) redisTemplate.opsForValue().get(SEND_EMAIL_CODE + email);
        if (!StringUtils.isAnyBlank(verifyCode)) {
            // 如果redis有该手机号验证码，则返回
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "验证码已发送，请注意查收");
        }
        // 如果redis没有该手机号验证码，则获取验证码并发送
        verifyCode = RandomSmsNumUtils.getSixBitRandom(); // 获取六位验证码
        sendMessageToEmail(verifyCode, email);
        // 将该验证码存入redis
        redisTemplate.opsForValue().set(
                SEND_EMAIL_CODE + email,
                verifyCode,
                5,
                TimeUnit.MINUTES);
        return ResultUtils.success(true);

    }

    /**
     * 发送邮箱验证码
     *
     * @param verifyCode 验证码
     * @param email      邮箱
     */
    private void sendMessageToEmail(String verifyCode, String email) {
        // 发送邮件验证码
        Context context = new Context(); // 引入Template的Context
        // 设置模板中的变量（分割验证码）
        context.setVariable("verifyCode", Arrays.asList(verifyCode.split("")));
        //将验证码存入redis中
        redisTemplate.opsForValue().set("joy:sendCode:" + email, verifyCode, 5, TimeUnit.MINUTES);
        // 第一个参数为模板的名称(html不用写全路径)
        String process = templateEngine.process("EmailCode.html", context); // 这里不用写全路径
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject("【Joy Judge】验证码"); // 邮件的标题
            helper.setFrom(username); // 发送者
            helper.setTo(email); // 接收者
            helper.setSentDate(new Date()); // 时间
            helper.setText(process, true); // 第二个参数true表示这是一个html文本
        } catch (MessagingException e) {
            throw new IllegalArgumentException("发送邮件失败");
        }
        javaMailSender.send(mimeMessage);

    }
}
