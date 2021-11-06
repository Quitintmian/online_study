package com.gyq.oss.controller;

import com.gyq.commonutils.R;
import com.gyq.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/file")
@CrossOrigin
@Api(tags = "阿里云文件接口")
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping("")
    @ApiOperation(value = "文件上传")
    public R uploadOssFile(MultipartFile file){
        // 获取上传文件,返回Oss资源地址
        String url = ossService.uploadAvatar(file);
        return R.ok().data("url",url);

    }
}
