package com.gyq.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {

    String uploadVideo(MultipartFile file);

    void removeVideo(String id);

    void removeManyVideo(List<String> ids);
}
