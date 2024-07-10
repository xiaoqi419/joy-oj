package com.ojason.joyoj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ojason.joyoj.model.entity.QuestionLanguage;

import java.util.List;

/**
 * @author Jason
 * @description 针对表【question_language(支持的编程语言)】的数据库操作Service
 * @createDate 2024-07-10 21:45:05
 */
public interface QuestionLanguageService extends IService<QuestionLanguage> {

    /**
     * 获取支持的编程语言
     */
    List<QuestionLanguage> getQuestionLanguage();
}
