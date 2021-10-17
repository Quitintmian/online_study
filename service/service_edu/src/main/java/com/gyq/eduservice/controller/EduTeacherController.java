package com.gyq.eduservice.controller;


import com.gyq.eduservice.entity.EduTeacher;
import com.gyq.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author guyaqing
 * @since 2021-10-17
 */
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    // 注入讲师对应的service
    @Autowired
    EduTeacherService teacherService;

    // restful 风格
    // 1.查询讲师的所有数据
    @GetMapping("findAll")
    public List<EduTeacher> finAllTeacher(){
        return teacherService.list(null);
    }

    // 2.逻辑删除讲师
    @DeleteMapping("{id}")
    public boolean removeTeacher(@PathVariable("id") String id){
        return teacherService.removeById(id);
    }

}

