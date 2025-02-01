package com.ojason.joyoj.controller;

import com.ojason.joyoj.common.BaseResponse;
import com.ojason.joyoj.common.ErrorCode;
import com.ojason.joyoj.common.ResultUtils;
import com.ojason.joyoj.exception.BusinessException;
import com.ojason.joyoj.model.entity.SystemBasic;
import com.ojason.joyoj.service.SystemBasicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/system_config")
@Slf4j
public class SystemConfigController {

    @Resource
    private SystemBasicService systemBasicService;

    /**
     * 修改系统基础配置
     * @param systemBasic 基础配置
     * @return BaseResponse<Boolean>
     */
    @PostMapping("/changeBasic")
    public BaseResponse<Boolean> changeBasicConfig(@RequestBody SystemBasic systemBasic) {
        if (systemBasic == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 为空或者空字符
        if (systemBasic.getWebsiteName() == null || systemBasic.getWebsiteName().isEmpty()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (systemBasic.getAnnouncement() == null || systemBasic.getAnnouncement().isEmpty()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = systemBasicService.addSystemBasic(systemBasic);
        return ResultUtils.success(result);
    }

    /**
     *  查询最新基础配置
     * @return BaseResponse<SystemBasic>
     */
    @GetMapping("/getBasicConfig")
    public BaseResponse<SystemBasic> getBasicConfig() {
        SystemBasic systemBasic = systemBasicService.getSystemBasic();
        if(systemBasic == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return ResultUtils.success(systemBasic);
    }

}
