package com.ojason.joyoj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ojason.joyoj.model.dto.solution.SolutionTagsRequest;
import com.ojason.joyoj.model.entity.Tags;
import com.ojason.joyoj.model.vo.SolutionTagsVO;

import java.util.List;

/**
 * @author PC
 * @description 针对表【tags】的数据库操作Service
 * @createDate 2025-02-09 17:49:09
 */
public interface TagsService extends IService<Tags> {


    /**
     * 获取标签
     *
     * @param solutionTagsRequest
     * @return
     */
    List<SolutionTagsVO> getTags(SolutionTagsRequest solutionTagsRequest);

    /**
     * 获取五个随机标签
     *
     * @return
     */
    List<SolutionTagsVO> getRandomTags();
}
