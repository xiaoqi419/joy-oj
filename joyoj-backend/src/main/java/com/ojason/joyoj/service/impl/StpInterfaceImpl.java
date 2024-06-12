package com.ojason.joyoj.service.impl;

import cn.dev33.satoken.stp.StpInterface;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ojason.joyoj.mapper.UserMapper;
import com.ojason.joyoj.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: joyoj-backend
 * @ClassName: StpInterfaceImpl
 * @description: Stp接口实现类
 * @author: Jason
 * @create: 2024-06-12 20:14
 * @from: <a href="https://www.ojason.top">我的博客</a>
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    private final UserMapper userMapper;

    @Autowired
    public StpInterfaceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 本 list 仅做模拟，实际项目中要根据具体业务逻辑来查询权限
        List<String> list = new ArrayList<>();
        list.add("101");
        list.add("user.add");
        list.add("user.update");
        list.add("user.get");
        list.add("art.*");
        return list;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        // 通过loginId查询用户角色
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", loginId);
        User user = userMapper.selectOne(queryWrapper);
        List<String> roleList = new ArrayList<>();
        roleList.add(user.getUserRole());
        return roleList;
    }

}
