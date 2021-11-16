package com.gyq.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gyq.commonutils.R;
import com.gyq.eduservice.entity.EduCourse;
import com.gyq.eduservice.entity.EduTeacher;
import com.gyq.eduservice.service.EduCourseService;
import com.gyq.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/index")
@CrossOrigin
public class IndexController {

    @Autowired
    EduCourseService courseService;
    @Autowired
    EduTeacherService teacherService;

    // 查询前八条热门课程，前4条名师
    @GetMapping("")
    // TODO redis注解
    // @Cacheable(key = "'selectIndexList'",value = "courseTeacher")
    public R index(){
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<EduCourse> courseList = courseService.list(wrapper);

        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        wrapperTeacher.last("limit 4");
        List<EduTeacher> teacherList = teacherService.list(wrapperTeacher);

        return R.ok().data("courseList",courseList).data("teacherList",teacherList);

    }
}
