package com.ojason.joyoj.judge.codesandbox.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.ojason.joyoj.common.ErrorCode;
import com.ojason.joyoj.exception.BusinessException;
import com.ojason.joyoj.judge.codesandbox.CodeSandbox;
import com.ojason.joyoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.ojason.joyoj.judge.codesandbox.model.ExecuteCodeResponse;
import org.apache.commons.lang3.StringUtils;

/**
 * @program: joyoj-backend
 * @ClassName: CodeSandboxImpl
 * @description: 远程代码沙箱（实际调用接口的沙箱）
 * @author: Jason
 * @create: 2024-07-12 15:51
 * @from: <a href="https://www.ojason.top">我的博客</a>
 */
public class RemoteCodeSandboxImpl implements CodeSandbox {

    private static final String AUTH_HEARD = "authorization";
    private static final String AUTH_REQUEST_SECRET = "jason";

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        String url = "http://110.40.52.124:8105/api/executeCode";
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        String body = HttpUtil.createPost(url)
                .header(AUTH_HEARD, AUTH_REQUEST_SECRET)
                .body(json)
                .execute()
                .body();
        if (StringUtils.isBlank(body)) {
            throw new BusinessException(ErrorCode.API_ERROR, body);
        }
        return JSONUtil.toBean(body, ExecuteCodeResponse.class);
    }
}
