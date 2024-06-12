package com.ojason.joyoj.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: joyoj-backend
 * @ClassName: UserForgetPasswordRequest
 * @description:
 * @author: Jason
 * @create: 2024-06-12 23:02
 * @from: <a href="https://www.ojason.top">我的博客</a>
 */
@Data
public class UserForgetPasswordRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 用户账号
     */
    private String userAccount;
    /**
     * 用户新密码
     */
    private String newPassword;
    /**
     * 验证码
     */
    private String code;
}
