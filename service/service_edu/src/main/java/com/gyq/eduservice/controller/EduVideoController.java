package com.gyq.eduservice.controller;


import com.gyq.commonutils.R;
import com.gyq.eduservice.client.VodClient;
import com.gyq.eduservice.entity.EduVideo;
import com.gyq.eduservice.service.EduVideoService;
import com.gyq.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author guyaqing
 * @since 2021-11-08
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    // 远程调用 属于 httpclient
    @Autowired
    private VodClient vodClient;

    // 添加
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.save(eduVideo);
        return R.ok();
    }

    // 删小节 然后删视频
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id){
        // 查询出视频id
        String videoSourceId = eduVideoService.getById(id).getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)){
            // 若删除不成功，会执行熔断处理
            R result = vodClient.removeAlyVideo(videoSourceId);
            if (result.getCode() == 20001){
                throw new GuliException(20001,"视频删除出错，熔断器处理...");
            }
        }
        // 最后删除小节
        eduVideoService.removeById(id);
        return R.ok();
    }

    // 获取小节信息
    @GetMapping("getVideo/{id}")
    public R getVideo(@PathVariable String id){
        EduVideo eduVideo = eduVideoService.getById(id);
        return R.ok().data("curVideo",eduVideo);
    }

    // 修改小节
    @PutMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        return eduVideoService.updateById(eduVideo) ? R.ok() : R.error();
    }

}

