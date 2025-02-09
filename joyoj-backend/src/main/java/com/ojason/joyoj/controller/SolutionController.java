package com.ojason.joyoj.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.ojason.joyoj.common.BaseResponse;
import com.ojason.joyoj.common.ResultUtils;
import com.ojason.joyoj.model.dto.solution.SolutionTagsRequest;
import com.ojason.joyoj.model.vo.SolutionTagsVO;
import com.ojason.joyoj.service.TagsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/solution")
@Slf4j
public class SolutionController {

    @Resource
    private TagsService tagsService;

    /**
     * 获取题解标签分类
     */
    @PostMapping("/tags")
    @SaCheckLogin
    public BaseResponse<List<SolutionTagsVO>> getTags(@RequestBody SolutionTagsRequest solutionTagsRequest) {
        // 如果有参数则返回相同分类的标签，否则返回所有标签
        List<SolutionTagsVO> tags = tagsService.getTags(solutionTagsRequest);
        return ResultUtils.success(tags);
    }

    /**
     * 随机获取五个标签
     */
    @PostMapping("/randomTags")
    public BaseResponse<List<SolutionTagsVO>> getRandomTags() {
        List<SolutionTagsVO> tags = tagsService.getRandomTags();
        return ResultUtils.success(tags);
    }


}
