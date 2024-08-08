package com.ojason.joyoj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ojason.joyoj.model.dto.questionsave.QuestionSaveAddRequest;
import com.ojason.joyoj.model.entity.QuestionSave;
import com.ojason.joyoj.model.entity.User;

/**
 * @author 26504
 * @description 针对表【question_save(题目代码保存)】的数据库操作Service
 * @createDate 2024-08-08 18:07:53
 */
public interface QuestionSaveService extends IService<QuestionSave> {


    /**
     * 保存用户代码
     *
     * @param questionSaveAddRequest
     * @param loginUser
     * @return
     */
    boolean saveQuestionCode(QuestionSaveAddRequest questionSaveAddRequest, User loginUser);
}
