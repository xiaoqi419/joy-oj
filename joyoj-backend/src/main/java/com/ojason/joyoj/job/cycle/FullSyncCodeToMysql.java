package com.ojason.joyoj.job.cycle;

import com.ojason.joyoj.model.dto.questionsave.QuestionSaveAddRequest;
import com.ojason.joyoj.service.QuestionSaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

import static com.ojason.joyoj.constant.RedisConstant.QUESTION_SAVE;

/**
 * 全量同步代码到mysql
 *
 * @author Jason
 */
@Component
@Slf4j
public class FullSyncCodeToMysql {

    @Resource
    private QuestionSaveService questionSaveService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 每分钟执行一次
     */
    @Scheduled(fixedRate = 60 * 1000)
    public void run() {
        // 从redis中获取joy:question:save:分组下的所有key
        Set<String> keys = redisTemplate.keys(QUESTION_SAVE + "*");
        // 得到的key值为a_b的形式，a为用户id，b为题目id
        long total = 0;
        if (keys != null) {
            log.info("FullSyncCodeToMysql start, total {}", keys.size());
            for (String key : keys) {
                // 从最后一个:开始截取
                String newKey = key.substring(key.lastIndexOf(":") + 1);
                String questionId = newKey.split("_")[1];
                String userId = newKey.split("_")[0];
                String code = (String) redisTemplate.opsForValue().get(key);
                // 更新到mysql中
                QuestionSaveAddRequest questionSaveAddRequest = new QuestionSaveAddRequest();
                questionSaveAddRequest.setCode(code);
                questionSaveAddRequest.setUserId(Long.valueOf(userId));
                questionSaveAddRequest.setQuestionId(Long.valueOf(questionId));
                boolean result = questionSaveService.saveQuestionCodeInMysql(questionSaveAddRequest);
                if (result) {
                    total++;
                }
            }
            log.info("FullSyncCodeToMysql success, total {}", total);
        }
    }
}
