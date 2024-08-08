package com.ojason.joyoj.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ojason.joyoj.mapper.QuestionSaveMapper;
import com.ojason.joyoj.model.dto.questionsave.QuestionSaveAddRequest;
import com.ojason.joyoj.model.dto.questionsave.QuestionSaveQueryRequest;
import com.ojason.joyoj.model.entity.QuestionSave;
import com.ojason.joyoj.model.entity.User;
import com.ojason.joyoj.model.vo.QuestionSaveVO;
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
    public boolean saveQuestionCodeInRedis(QuestionSaveAddRequest questionSaveAddRequest, User loginUser) {
        // 1.从redis中查找该用户对应该题目是否记录，有则替换为最新的代码片段，没有则创建新的
        redisTemplate.opsForValue().set(QUESTION_SAVE + loginUser.getId() + "_" + questionSaveAddRequest.getQuestionId(), questionSaveAddRequest.getCode());
        return true;
    }

    @Override
    public boolean saveQuestionCodeInMysql(QuestionSaveAddRequest questionSaveAddRequest) {
        QuestionSave questionSave = new QuestionSave();
        BeanUtil.copyProperties(questionSaveAddRequest, questionSave);
        // 从Mysql中查询，如果存在则更新，不存在则新增
        QuestionSave oldQuestionSave = this.lambdaQuery()
                .eq(QuestionSave::getUserId, questionSave.getUserId())
                .eq(QuestionSave::getQuestionId, questionSave.getQuestionId()).one();
        if (oldQuestionSave != null) {
            questionSave.setId(oldQuestionSave.getId());
            this.updateById(questionSave);
        } else {
            this.save(questionSave);
        }
        return true;
    }

    @Override
    public QuestionSaveVO getQuestionSaveVO(QuestionSaveQueryRequest questionSaveQueryRequest) {

        // 从redis中查找该用户对应该题目是否记录，有则返回，没有则从Mysql中查找
        String code = (String) redisTemplate.opsForValue().get(QUESTION_SAVE + questionSaveQueryRequest.getUserId() + "_" + questionSaveQueryRequest.getQuestionId());
        if (code != null) {
            QuestionSaveVO questionSaveVO = new QuestionSaveVO();
            BeanUtil.copyProperties(questionSaveQueryRequest, questionSaveVO);
            questionSaveVO.setCode(code);
            return questionSaveVO;
        }
        QuestionSave questionSave = this.lambdaQuery()
                .eq(QuestionSave::getUserId, questionSaveQueryRequest.getUserId())
                .eq(QuestionSave::getQuestionId, questionSaveQueryRequest.getQuestionId()).one();
        if (questionSave == null) {
            return null;
        }
        QuestionSaveVO questionSaveVO = new QuestionSaveVO();
        BeanUtil.copyProperties(questionSave, questionSaveVO);
        return questionSaveVO;

    }
}




