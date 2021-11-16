package com.gyq.vod.service;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.gyq.servicebase.exceptionhandler.GuliException;
import com.gyq.vod.utils.ConstantPropertiesUtils;
import com.gyq.vod.utils.InitVodClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Slf4j
@Service
public class VodServiceImpl implements VodService{


    @Override
    public String uploadVideo(MultipartFile file) {

        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;

        String fileName = file.getOriginalFilename();
        String title = fileName.substring(0,fileName.lastIndexOf(".")); // 不要后缀作为标题

        try {
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(accessKeyId,accessKeySecret,title,fileName,inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            return response.getVideoId();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void removeVideo(String id) {

        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;

        try {
            // 初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(accessKeyId,accessKeySecret);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);
            log.info("删除视频，id:"+id);
            client.getAcsResponse(request);
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败!");
        }


    }

    @Override
    public void removeManyVideo(List ids) {
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;

        try {
            // 初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(accessKeyId,accessKeySecret);
            DeleteVideoRequest request = new DeleteVideoRequest();

            // id之间用逗号分开
            String videoIds = StringUtils.join(ids.toArray(), ",");
            request.setVideoIds(videoIds);
            log.info("删除视频，ids:"+videoIds);
            client.getAcsResponse(request);
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败!");
        }
    }
}

