package com.ojason.joyoj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ojason.joyoj.mapper.QuestionSaveMapper;
import com.ojason.joyoj.model.dto.questionsave.QuestionSaveAddRequest;
import com.ojason.joyoj.model.entity.QuestionSave;
import com.ojason.joyoj.model.entity.User;
import com.ojason.joyoj.service.QuestionSaveService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.ojason.joyoj.constant.RedisConstant.QUESTION_SAVE;

/**
 * @author 26504
 * @description 针对表【question_save(题目代码保存)】的数据库操作Service实现
 * @createDate 2024-08-08 18:07:53
 */
@Service
public class QuestionSaveServiceImpl extends ServiceImpl<QuestionSaveMapper, QuestionSave>
        implements QuestionSaveService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean saveQuestionCode(QuestionSaveAddRequest questionSaveAddRequest, User loginUser) {
        // 1.从redis中查找该用户对应该题目是否记录，有则替换为最新的代码片段，没有则创建新的
        redisTemplate.opsForValue().set(QUESTION_SAVE + loginUser.getId() + "_" + questionSaveAddRequest.getQuestionId(), questionSaveAddRequest.getCode());

        // 2. TODO 定时任务 定时把redis中的代码片段更新到mysql中
        return true;
    }
}




