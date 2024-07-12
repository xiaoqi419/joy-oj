package com.ojason.joyoj.judge.codesandbox.impl;

import com.ojason.joyoj.judge.codesandbox.CodeSandbox;
import com.ojason.joyoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.ojason.joyoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.ojason.joyoj.judge.codesandbox.model.JudgeInfo;
import com.ojason.joyoj.model.enums.JudgeInfoEnum;
import com.ojason.joyoj.model.enums.QuestionSubmitStatusEnum;

import java.util.List;

/**
 * @program: joyoj-backend
 * @ClassName: CodeSandboxImpl
 * @description: 示例代码沙箱
 * @author: Jason
 * @create: 2024-07-12 15:51
 * @from: <a href="https://www.ojason.top">我的博客</a>
 */
public class ExampleCodeSandboxImpl implements CodeSandbox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();

        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试执行成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoEnum.ACCEPTED.getValue());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }
}
