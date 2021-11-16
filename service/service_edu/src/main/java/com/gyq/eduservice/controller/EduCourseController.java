package com.gyq.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gyq.commonutils.R;
import com.gyq.eduservice.entity.EduCourse;
import com.gyq.eduservice.entity.EduTeacher;
import com.gyq.eduservice.entity.vo.CourseInfoVo;
import com.gyq.eduservice.entity.vo.CoursePublishVo;
import com.gyq.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable("courseId") String courseId){
        CourseInfoVo courseInfoVo = courseService.getCourseInfoVo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    // 修改课程信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        int update = courseService.updateCourseInfo(courseInfoVo);
        return update > 0 ? R.ok() : R.error();
    }

    // 通过课程id 查询 课程发布信息 (汇总)
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id){
        CoursePublishVo coursePublishVo = courseService.CoursePublishInfo(id);
        return R.ok().data("publishCourse",coursePublishVo);
    }

    // 最终发布 未发布 -> 已发布 (通过已存在的Id 设置status字段)
    @PutMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        EduCourse course = new EduCourse();
        course.setId(id);
        course.setStatus("Normal");
        // update course set status = Normal where id = ?
        courseService.updateById(course);
        return R.ok();
    }

    //显示 [所有] 课程列表
    @GetMapping("courseList")
    public R courseList(){
        List<EduCourse> list = courseService.list(null);
        return R.ok().data("courseList",list);
    }

    // 课程查询 分页 条件查询
    @PostMapping(value = "pageCourse/{current}/{limit}")
    public R pageLishCourse(@PathVariable long current,@PathVariable long limit,@RequestBody(required = false) EduCourse course){
        Page<EduCourse> pageCourse = new Page<>(current,limit);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        String title = course.getTitle();
        String status = course.getStatus();
        if (!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        if (!StringUtils.isEmpty(status)){
            wrapper.like("status",status);
        }
        wrapper.orderByDesc("gmt_create");

        courseService.page(pageCourse,wrapper);
        long total = pageCourse.getTotal();
        List<EduCourse> list = pageCourse.getRecords();
        Map map = new HashMap();
        map.put("total",total);
        map.put("rows",list);
        return R.ok().data(map);
    }

    // 删除 课程信息 需要删除
    @DeleteMapping("{id}")
    public R deleteCourse(@PathVariable String id){
        courseService.removeCourse(id);
        return R.ok();
    }
}

