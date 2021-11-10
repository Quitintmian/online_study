package com.gyq.eduservice.service;

import com.gyq.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gyq.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author guyaqing
 * @since 2021-11-08
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);
    List<ChapterVo> getChapterVideo(String courseId);
    List<EduChapter> getAll();
}
