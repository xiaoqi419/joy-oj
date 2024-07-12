package com.ojason.joyoj.judge.strategy;

import com.ojason.joyoj.model.dto.questionsubmit.JudgeInfo;

/**
 * @ClassName : JudgeStrategy
 * @Description : 判题策略
 * @Author : Jason
 * @Date: 2024-07-12 18:08
 */
public interface JudgeStrategy {

    /**
     * 执行判题
     *
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext);
}
