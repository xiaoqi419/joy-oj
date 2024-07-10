package com.ojason.joyoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ojason.joyoj.mapper.QuestionLanguageMapper;
import com.ojason.joyoj.model.entity.QuestionLanguage;
import com.ojason.joyoj.service.QuestionLanguageService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 26504
 * @description 针对表【question_language(支持的编程语言)】的数据库操作Service实现
 * @createDate 2024-07-10 21:45:05
 */
@Service
public class QuestionLanguageServiceImpl extends ServiceImpl<QuestionLanguageMapper, QuestionLanguage>
        implements QuestionLanguageService {


    @Override
    public List<QuestionLanguage> getQuestionLanguage() {
        return baseMapper.selectList(new QueryWrapper<>());
    }
}




