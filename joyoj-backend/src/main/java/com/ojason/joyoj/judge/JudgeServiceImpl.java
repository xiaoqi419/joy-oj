package com.ojason.joyoj.judge;

import cn.hutool.json.JSONUtil;
import com.ojason.joyoj.common.ErrorCode;
import com.ojason.joyoj.exception.BusinessException;
import com.ojason.joyoj.judge.codesandbox.CodeSandbox;
import com.ojason.joyoj.judge.codesandbox.CodeSandboxFactory;
import com.ojason.joyoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.ojason.joyoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.ojason.joyoj.judge.codesandbox.model.JudgeInfo;
import com.ojason.joyoj.judge.strategy.JudgeContext;
import com.ojason.joyoj.model.dto.question.JudgeCase;
import com.ojason.joyoj.model.entity.Question;
import com.ojason.joyoj.model.entity.QuestionSubmit;
import com.ojason.joyoj.model.enums.QuestionSubmitStatusEnum;
import com.ojason.joyoj.service.QuestionService;
import com.ojason.joyoj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @program: joyoj-backend
 * @ClassName: JudgeServiceImpl
 * @description:
 * @author: Jason
 * @create: 2024-07-12 17:07
 * @from: <a href="https://www.ojason.top">我的博客</a>
 */
@Service
public class JudgeServiceImpl implements JudgeService {

    @Value("${codesandbox.type}")
    private String codesandboxType;

    @Resource
    private QuestionService questionService;
    @Resource
    private QuestionSubmitService questionSubmitService;
    @Resource
    private JudgeManage judgeManage;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中");
        }
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新题目提交状态失败");
        }
        // 调用沙箱，执行获取结果
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(codesandboxType);
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        List<String> outputList = executeCodeResponse.getOutputList();
        // 根据沙箱的执行结果，设置题目的判题状态和信息
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);
        JudgeInfo judgeInfo = judgeManage.doJudge(judgeContext);
        // 修改数据库的判题结果
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionSubmitService.updateById(questionSubmitUpdate);
        // 如果通过更新题目状态
        if (Objects.equals(judgeInfo.getMessage(), "Accepted")) {
            // 更新题目 时通过率加一
            Question questionUpdate = new Question();
            questionUpdate.setId(questionId);
            questionUpdate.setAcceptedNum(question.getAcceptedNum() + 1);
            update = questionService.updateById(questionUpdate);
        }
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新题目提交状态失败");
        }
        QuestionSubmit questionSubmitResult = questionSubmitService.getById(questionId);
        return questionSubmitResult;
    }
}
