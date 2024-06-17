package com.ojason.joyoj.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author : Jason
 * @Description : 文件上传Service
 * @Date: 2024-03-12 15:12
 */
public interface FileService {

    /**
     * 阿里云OSS文件上传
     *
     * @param file
     * @return
     */
    String upload(MultipartFile file);
}
