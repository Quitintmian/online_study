package com.gyq.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.gyq.oss.service.OssService;
import com.gyq.oss.utils.ConstantPropertiesUtils;
import com.gyq.oss.utils.FileNameUtil;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
@Slf4j
public class OssServiceImpl implements OssService {

    @Override
    public String uploadAvatar(MultipartFile file) {

        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        String uploadUrl = null;
        OSS ossClient = null;
        try {
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret); // 创建OSSClient实例
            InputStream inputStream = file.getInputStream(); // 使用UUID生成文件名
            String datePath = new DateTime().toString("yyyy/MM/dd");
            String fileName = FileNameUtil.generateName(file);
            String fileUrl = datePath + "/" + fileName;
            ossClient.putObject(bucketName, fileUrl, inputStream);
            // 形如 https://onlinedemo.oss-cn-chengdu.aliyuncs.com/mengzi.jpg
            uploadUrl = "https://"+bucketName+"."+endpoint+"/"+fileUrl;
            log.info("文件上传成功，地址为" + uploadUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            assert ossClient != null;
            ossClient.shutdown();
        }
        return uploadUrl;
    }
}
