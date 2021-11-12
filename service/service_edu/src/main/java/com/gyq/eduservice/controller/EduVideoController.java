package com.gyq.eduservice.controller;


import com.gyq.commonutils.R;
import com.gyq.eduservice.entity.EduVideo;
import com.gyq.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
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

    // 添加
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.save(eduVideo);
        return R.ok();
    }

    // TODO 视频待删除
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id){
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

