package com.ojason.joyoj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ojason.joyoj.model.dto.questionsave.QuestionSaveAddRequest;
import com.ojason.joyoj.model.dto.questionsave.QuestionSaveQueryRequest;
import com.ojason.joyoj.model.entity.QuestionSave;
import com.ojason.joyoj.model.entity.User;
import com.ojason.joyoj.model.vo.QuestionSaveVO;

/**
 * @author 26504
 * @description 针对表【question_save(题目代码保存)】的数据库操作Service
 * @createDate 2024-08-08 18:07:53
 */
public interface QuestionSaveService extends IService<QuestionSave> {


    /**
     * 保存用户代码到Redis
     *
     * @param questionSaveAddRequest
     * @param loginUser
     * @return
     */
    boolean saveQuestionCodeInRedis(QuestionSaveAddRequest questionSaveAddRequest, User loginUser);


    /**
     * 保存用户代码到Mysql
     *
     * @param questionSaveAddRequest
     * @return
     */
    boolean saveQuestionCodeInMysql(QuestionSaveAddRequest questionSaveAddRequest);

    /**
     * 获取用户对于该题目保存的代码片段
     *
     * @param questionSaveQueryRequest
     * @return
     */
    QuestionSaveVO getQuestionSaveVO(QuestionSaveQueryRequest questionSaveQueryRequest);
}
