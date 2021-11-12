package com.gyq.eduservice.controller;


import com.gyq.commonutils.R;
import com.gyq.eduservice.entity.EduChapter;
import com.gyq.eduservice.entity.chapter.ChapterVo;
import com.gyq.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author guyaqing
 * @since 2021-11-08
 */
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    EduChapterService chapterService;

    // 根据 [课程id] 进行查询。课程大纲列表(包含章节和小节)
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable("courseId") String courseId){
        List<ChapterVo> list = chapterService.getChapterVideo(courseId);
        return R.ok().data("chapterAndVideo",list);
    }

    // 添加章节
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        chapterService.save(eduChapter);
        return R.ok();
    }

    // 查询章节by Id
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId){
        EduChapter eduChapter = chapterService.getById(chapterId);
        return R.ok().data("chapter",eduChapter);
    }

    // 修改章节
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        chapterService.updateById(eduChapter);
        return R.ok();
    }

    // 删除 , 如果章节中包含小节则不能删除
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId){
        boolean flag = chapterService.deleteChapter(chapterId);
        return flag ? R.ok() : R.error();
    }


}

