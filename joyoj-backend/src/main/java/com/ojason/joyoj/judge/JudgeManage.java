package com.ojason.joyoj.judge;

import com.ojason.joyoj.judge.strategy.DefaultJudgeStrategyImpl;
import com.ojason.joyoj.judge.strategy.JavaJudgeStrategyImpl;
import com.ojason.joyoj.judge.strategy.JudgeContext;
import com.ojason.joyoj.judge.strategy.JudgeStrategy;
import com.ojason.joyoj.model.dto.questionsubmit.JudgeInfo;
import com.ojason.joyoj.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * @program: joyoj-backend
 * @ClassName: JudgeManage
 * @description: 判题管理（简化调用）
 * @author: Jason
 * @create: 2024-07-12 18:35
 * @from: <a href="https://www.ojason.top">我的博客</a>
 */
@Service
public class JudgeManage {

    /**
     * 执行判题
     *
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategyImpl();
        if ("java".equals(language)) {
            judgeStrategy = new JavaJudgeStrategyImpl();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
