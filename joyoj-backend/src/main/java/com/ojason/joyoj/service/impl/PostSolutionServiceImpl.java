package com.ojason.joyoj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ojason.joyoj.mapper.PostSolutionMapper;
import com.ojason.joyoj.model.entity.PostSolution;
import com.ojason.joyoj.service.PostSolutionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 26504
 * @description 针对表【post_solution(题解表)】的数据库操作Service实现
 * @createDate 2024-08-21 16:32:51
 */
@Service
public class PostSolutionServiceImpl extends ServiceImpl<PostSolutionMapper, PostSolution>
        implements PostSolutionService {

    @Override
    public List<PostSolution> listSolutionByQuestionId(Long id) {
        // 通过题目ID获取该ID所有的题解ID
        return this.lambdaQuery().eq(PostSolution::getQuestionId, id).list();
    }

}




