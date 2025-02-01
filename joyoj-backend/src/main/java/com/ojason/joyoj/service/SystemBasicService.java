package com.ojason.joyoj.service;

import com.ojason.joyoj.model.entity.SystemBasic;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author PC
* @description 针对表【system_basic】的数据库操作Service
* @createDate 2025-02-01 14:27:23
*/
public interface SystemBasicService extends IService<SystemBasic> {

    /**
     *  修改系统基本配置（新增）
     * @param SystemBasic
     * @return boolean
     */
    boolean addSystemBasic(SystemBasic SystemBasic);

    /**
     * 获取最新的基础配置
     * @return SystemBasic
     */
    SystemBasic getSystemBasic();

}
