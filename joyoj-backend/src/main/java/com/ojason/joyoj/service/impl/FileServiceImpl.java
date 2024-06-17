package com.ojason.joyoj.service.impl;

import cn.hutool.core.util.IdUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import com.ojason.joyoj.config.OSSConfig;
import com.ojason.joyoj.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 文件上传业务类
 *
 * @author lixiang
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Resource
    private OSSConfig ossConfig;

    /**
     * 阿里云OSS文件上传
     *
     * @param file 文件
     */
    @Override
    public String upload(MultipartFile file) {

        //获取相关配置
        String bucketName = ossConfig.getBucketName();
        String endPoint = ossConfig.getEndPoint();
        String accessKeyId = ossConfig.getAccessKeyId();
        String accessKeySecret = ossConfig.getAccessKeySecret();
        String domain = ossConfig.getDomain();

        //创建OSS对象
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);

        //获取原生文件名
        String originalFilename = file.getOriginalFilename();
        //日期格式
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter dft = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        //拼装OSS上存储的路径
        String folder = dft.format(time);
        String fileName = generateUUID();
        String extension = null;
        if (originalFilename != null) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        //在OSS上bucket下的文件名
        String uploadFileName = "api/" + folder + '/' + fileName + extension;
        try {
            PutObjectResult result = ossClient.putObject(bucketName, uploadFileName, file.getInputStream());
            //拼装返回路径
            if (result != null) {
                return "https://" + domain + "/" + uploadFileName;
            }
        } catch (IOException e) {
            log.error("文件上传失败:{}", e.getMessage());
        } finally {
            //OSS关闭服务，不然会造成OOM
            ossClient.shutdown();
        }
        return null;
    }

    /**
     * 获取随机字符串
     *
     * @return 随机字符串
     */
    private String generateUUID() {
        return IdUtil.simpleUUID();
    }
}