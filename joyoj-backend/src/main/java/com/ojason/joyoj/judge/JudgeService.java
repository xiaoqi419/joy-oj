package com.ojason.joyoj.judge;

import com.ojason.joyoj.model.entity.QuestionSubmit;

/**
 * @ClassName : JudgeService
 * @Description : 判题服务
 * @Author : Jason
 * @Date: 2024-07-12 17:02
 */
public interface JudgeService {

    /**
     * 判题
     *
     * @param questionSubmitId
     * @return
     */
    QuestionSubmit doJudge(long questionSubmitId);

    QuestionSubmit doLocalJudge(Long questionSubmitId);
}
