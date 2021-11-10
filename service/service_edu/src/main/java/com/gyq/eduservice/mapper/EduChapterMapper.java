package com.gyq.eduservice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gyq.eduservice.entity.EduChapter;
import com.gyq.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author guyaqing
 * @since 2021-11-08
 */
public interface EduChapterMapper extends BaseMapper<EduChapter> {
    List<ChapterVo> getChapterVideo(String courseId);
    List<EduChapter> getAll();
}
