package com.ojason.joyoj.job.cycle;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ojason.joyoj.mapper.QuestionSaveMapper;
import com.ojason.joyoj.model.entity.QuestionSave;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @program: joyoj-backend
 * @ClassName: DelOvertimeCode
 * @description: 删除超过一天的代码片段
 * @author: Jason
 * @create: 2024-08-09 00:58
 * @from: <a href="https://www.ojason.top">我的博客</a>
 */
@Component
@Slf4j
public class DelOvertimeCode {

    @Resource
    private QuestionSaveMapper questionSaveMapper;


    /**
     * 每天凌晨2点执行一次
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void run() {
        log.info("DelOvertimeCode run");
        // 查询创建时间到执行该方法时候，超过一天的数据
        QueryWrapper<QuestionSave> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().ge(QuestionSave::getCreateTime, new Date(new Date().getTime() - 24 * 60 * 60 * 1000L));
        questionSaveMapper.delete(queryWrapper);
        log.info("DelOvertimeCode success");
    }

}
