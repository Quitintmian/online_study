package com.gyq.oss.utils;

import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.UUID;

public class FileNameUtil {

    //根据UUID生成文件名
    public static String getUUIDFileName() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    //根据给定的文件名和后缀截取文件名
    public static String getFileType(String fileName){
        int index = fileName.lastIndexOf(".");
        return fileName.substring(index);
    }

    // 生成完整的uuid文件名
    public static String generateName(MultipartFile file){
        return getUUIDFileName() + getFileType(Objects.requireNonNull(file.getOriginalFilename()));
    }

}
