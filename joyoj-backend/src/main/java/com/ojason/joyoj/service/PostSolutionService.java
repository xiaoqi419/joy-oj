package com.ojason.joyoj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ojason.joyoj.model.entity.PostSolution;

import java.util.List;

/**
 * @author 26504
 * @description 针对表【post_solution(题解表)】的数据库操作Service
 * @createDate 2024-08-21 16:32:51
 */
public interface PostSolutionService extends IService<PostSolution> {


    /**
     * 根据题目 id 获取题解
     *
     * @param id 题目 id
     * @return 题解列表
     */
    List<PostSolution> listSolutionByQuestionId(Long id);


}
