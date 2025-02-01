package com.ojason.joyoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ojason.joyoj.model.entity.SystemBasic;
import com.ojason.joyoj.service.SystemBasicService;
import com.ojason.joyoj.mapper.SystemBasicMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author PC
* @description 针对表【system_basic】的数据库操作Service实现
* @createDate 2025-02-01 14:27:23
*/
@Service
public class SystemBasicServiceImpl extends ServiceImpl<SystemBasicMapper, SystemBasic>
    implements SystemBasicService{

    @Resource
    private SystemBasicMapper systemBasicMapper;

    @Override
    public boolean addSystemBasic(SystemBasic systemBasic) {
        // 向数据库中新增的同时将上一条配置的值设置为1
        // 查询数据库条数，如果为1则执行不同的查询语句
        QueryWrapper<SystemBasic> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        Long count = systemBasicMapper.selectCount(wrapper);
        if (count == 1) {
            wrapper.last("LIMIT 1");
        }else {
            wrapper.last("LIMIT 1 OFFSET 1");
        }
        SystemBasic delSystemBasic = systemBasicMapper.selectOne(wrapper);
        if (delSystemBasic != null) {
            systemBasicMapper.deleteById(delSystemBasic.getId());
        }
        return save(systemBasic);

    }

    @Override
    public SystemBasic getSystemBasic() {
        QueryWrapper<SystemBasic> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("LIMIT 1");
        return systemBasicMapper.selectOne(wrapper);
    }
}




