package com.ojason.joyoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ojason.joyoj.mapper.TagsMapper;
import com.ojason.joyoj.model.dto.solution.SolutionTagsRequest;
import com.ojason.joyoj.model.entity.Tags;
import com.ojason.joyoj.model.vo.SolutionTagsVO;
import com.ojason.joyoj.service.TagsService;
import io.netty.util.internal.ThreadLocalRandom;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author PC
 * @description 针对表【tags】的数据库操作Service实现
 * @createDate 2025-02-09 17:49:09
 */
@Service
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags>
        implements TagsService {

    // 通用方法：将 Tags 转换为 SolutionTagsVO
    private List<SolutionTagsVO> convertToSolutionTagsVO(List<Tags> tags) {
        return tags.stream()
                .map(tag -> new SolutionTagsVO(tag.getId(), tag.getTagName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<SolutionTagsVO> getTags(SolutionTagsRequest solutionTagsRequest) {
        // 判断是否需要模糊查询
        QueryWrapper<Tags> queryWrapper = new QueryWrapper<>();
        if (solutionTagsRequest.getTagName() != null && !solutionTagsRequest.getTagName().isEmpty()) {
            queryWrapper.like("tagName", solutionTagsRequest.getTagName()); // 模糊查询条件
        }

        // 查询标签并转换为 SolutionTagsVO
        List<Tags> tags = this.baseMapper.selectList(queryWrapper);
        return convertToSolutionTagsVO(tags);
    }

    @Override
    public List<SolutionTagsVO> getRandomTags() {
        // 随机获取五个标签并返回
        long total = this.baseMapper.selectCount(null);
        Set<Integer> randomIndexes = new HashSet<>();
        Random random = ThreadLocalRandom.current();

        // 生成不重复随机索引（适用于总记录数远大于5的情况）
        while (randomIndexes.size() < Math.min(5, total)) {
            randomIndexes.add(random.nextInt((int) total));
        }

        return this.baseMapper.selectBatchIds(randomIndexes).stream()
                .map(tag -> new SolutionTagsVO(tag.getId(), tag.getTagName()))
                .collect(Collectors.toList());
    }
}




