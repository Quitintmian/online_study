package com.gyq.vod.controller;

import com.gyq.commonutils.R;
import com.gyq.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    VodService vodService;

    // 上传视频至阿里云
    @PostMapping("uploadVideo")
    public R uploadVideo(MultipartFile file){
        String videoId = vodService.uploadVideo(file);
        return R.ok().data("videoId",videoId);
    }

    // 删除视频by 视频Id
    @DeleteMapping("removeVideo/{id}")
    public R removeVideo(@PathVariable String id){
        vodService.removeVideo(id);
        return R.ok();
    }

    // 删除多个阿里云的视频
    @DeleteMapping("deleteBatch")
    public R deleteBatch(@RequestParam("ids") List<String> ids){
        vodService.removeManyVideo(ids);
        return R.ok();
    }


}
