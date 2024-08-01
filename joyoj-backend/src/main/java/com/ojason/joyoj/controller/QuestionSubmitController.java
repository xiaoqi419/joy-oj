package com.ojason.joyoj.controller;

import com.ojason.joyoj.judge.JudgeService;
import com.ojason.joyoj.service.QuestionSubmitService;
import com.ojason.joyoj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *
 */
@RestController
//@RequestMapping("/question_submit")
@Slf4j
@Deprecated
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;
    @Resource
    private UserService userService;

    @Resource
    @Lazy
    private JudgeService judgeService;
//
//    /**
//     * 提交题目
//     *
//     * @param questionSubmitAddRequest 提交请求
//     * @return resultNum 提交结果
//     */
//    @PostMapping("/")
//    public BaseResponse<Long> doQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest) {
//        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        // 登录才能判题
//        final User loginUser = userService.getLoginUser();
//        Long questionSubmitId = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
//        // 执行判题服务
//        CompletableFuture.runAsync(() -> {
//            judgeService.doJudge(questionSubmitId);
//        });
//
//        return ResultUtils.success(questionSubmitId);
//    }
//
//    /**
//     * 分页获取列表（除管理员外，普通用户只能看到非答案，提交代码等公开信息）
//     *
//     * @param questionSubmitQueryRequest
//     * @return
//     */
//    @PostMapping("/list/page")
//    @SaCheckLogin
//    public BaseResponse<Page<QuestionSubmitVO>> listQuestionSubmitByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest) {
//        long current = questionSubmitQueryRequest.getCurrent();
//        long size = questionSubmitQueryRequest.getPageSize();
//        Page<QuestionSubmit> questionPage = questionSubmitService.page(new Page<>(current, size),
//                questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
//        // 获取登录用户信息
//        User loginUser = userService.getLoginUser();
//        // 脱敏
//        return ResultUtils.success(questionSubmitService.getQuestionSubmitVOPage(questionPage, loginUser));
//    }

}
