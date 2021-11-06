package com.gyq.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    /**
     * 上传文件到阿里云Oss
     */
    String uploadAvatar(MultipartFile file);
}
