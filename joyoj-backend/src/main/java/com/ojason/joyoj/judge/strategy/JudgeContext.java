package com.ojason.joyoj.judge.strategy;

import com.ojason.joyoj.judge.codesandbox.model.JudgeInfo;
import com.ojason.joyoj.model.dto.question.JudgeCase;
import com.ojason.joyoj.model.entity.Question;
import com.ojason.joyoj.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * @program: joyoj-backend
 * @ClassName: JudgeContext
 * @description: 上下文（用于策略模式）
 * @author: Jason
 * @create: 2024-07-12 18:09
 * @from: <a href="https://www.ojason.top">我的博客</a>
 */
@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private List<JudgeCase> judgeCaseList;

    private QuestionSubmit questionSubmit;

    private Question question;
}
