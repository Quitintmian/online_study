package com.gyq.eduservice.service;

import com.gyq.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gyq.eduservice.entity.vo.CourseInfoVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author guyaqing
 * @since 2021-11-08
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfoVo(String courseId);

    int updateCourseInfo(CourseInfoVo courseInfoVo);
}
