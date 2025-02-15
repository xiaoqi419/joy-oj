package com.ojason.joyoj.model.dto.post;

import com.ojason.joyoj.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 查询请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PostQueryRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;
    /**
     * id
     */
    private Long notId;
    /**
     * ids
     */
    private List<Long> ids;
    /**
     * 搜索词
     */
    private String searchText;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 标签列表
     */
    private List<String> tags;
    /**
     * 至少有一个标签
     */
    private List<String> orTags;
    /**
     * 创建用户 id
     */
    private Long userId;
    /**
     * 收藏用户 id
     */
    private Long favourUserId;
}