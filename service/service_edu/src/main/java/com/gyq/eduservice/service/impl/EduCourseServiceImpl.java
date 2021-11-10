package com.gyq.eduservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gyq.eduservice.entity.EduCourse;
import com.gyq.eduservice.entity.EduCourseDescription;
import com.gyq.eduservice.entity.vo.CourseInfoVo;
import com.gyq.eduservice.mapper.EduCourseMapper;
import com.gyq.eduservice.service.EduCourseDescriptionService;
import com.gyq.eduservice.service.EduCourseService;
import com.gyq.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author guyaqing
 * @since 2021-11-08
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    // 添加课程信息
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        // 向课程表中加
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse); // 对应属性复制
        int modifyNum = baseMapper.insert(eduCourse);
        if (modifyNum <= 0) {
            throw new GuliException(20001,"添加课程信息失败");
        }
        // 向简介表中加
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setId(eduCourse.getId()); // 一对一 id 相同
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.save(courseDescription);

        return eduCourse.getId();
    }

    // 查询课程Vo
    @Override
    public CourseInfoVo getCourseInfoVo(String courseId) {
        // 课程中 包含描述
        EduCourse course = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(course,courseInfoVo);

        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;

    }

    @Override
    public int updateCourseInfo(CourseInfoVo courseInfoVo) {

        int update = 0;

        // 修改课程
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        update += baseMapper.updateById(eduCourse);
        // 修改简介
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        update += courseDescriptionService.updateById(description) ? 1 : 0;

        return update;

    }
}
