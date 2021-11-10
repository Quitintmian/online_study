package com.gyq.eduservice.controller;


import com.gyq.commonutils.R;
import com.gyq.eduservice.entity.vo.CourseInfoVo;
import com.gyq.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author guyaqing
 * @since 2021-11-08
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    EduCourseService courseService;

    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        // 添加信息 从前端传来的json 封装而来的Vo对象
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }

    // 根据课程id 查询课程信息
    @GetMapping("getCouseInfo/{courseId}")
    public R getCouseInfo(@PathVariable("courseId") String courseId){
        CourseInfoVo courseInfoVo = courseService.getCourseInfoVo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    // 修改课程信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        int update = courseService.updateCourseInfo(courseInfoVo);
        return update > 0 ? R.ok() : R.error();
    }

}

