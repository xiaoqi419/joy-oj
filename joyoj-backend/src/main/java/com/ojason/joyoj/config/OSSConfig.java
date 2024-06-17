package com.ojason.joyoj.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author jason
 * @description OSS配置类
 */
@ConfigurationProperties(prefix = "aliyun.oss")
@Configuration
@Data
public class OSSConfig {

    private String endPoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String domain;

}