package com.ojason.joyoj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ojason.joyoj.model.dto.post.PostAddRequest;
import com.ojason.joyoj.model.dto.post.PostQueryRequest;
import com.ojason.joyoj.model.entity.Post;
import com.ojason.joyoj.model.vo.PostVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 帖子服务
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
public interface PostService extends IService<Post> {

    /**
     * 校验
     *
     * @param post
     * @param add
     */
    void validPost(Post post, boolean add);

    /**
     * 获取查询条件
     *
     * @param postQueryRequest
     * @return
     */
    QueryWrapper<Post> getQueryWrapper(PostQueryRequest postQueryRequest);

    /**
     * 从 ES 查询
     *
     * @param postQueryRequest
     * @return
     */
    Page<Post> searchFromEs(PostQueryRequest postQueryRequest);

    /**
     * 获取帖子封装
     *
     * @param post
     * @param request
     * @return
     */
    PostVO getPostVO(Post post, HttpServletRequest request);

    /**
     * 分页获取帖子封装
     *
     * @param postPage
     * @return
     */
    Page<PostVO> getPostVOPage(Page<Post> postPage);

    /**
     * 发表题解
     */
    boolean addSolution(PostAddRequest postAddRequest);

    List<Long> listSolutionIdsByPostId(long id);
}
