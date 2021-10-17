package com.gyq.eduservice.controller;


import com.gyq.commonutils.R;
import com.gyq.eduservice.entity.EduTeacher;
import com.gyq.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 * 有点像后端封装，前端解析的过程
 * @author guyaqing
 * @since 2021-10-17
 */

@Api(tags = "讲师Controller")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    // 注入讲师对应的service
    @Autowired
    EduTeacherService teacherService;

    // restful 风格
    // 1.查询讲师的所有数据
    @ApiOperation(value = "查询所有讲师列表")
    @GetMapping("findAll")
    public R finAllTeacher(){
        List<EduTeacher> list = teacherService.list(null);
        /* 以下语句做了两件事，R.ok()创建了R对象，并设置3个标识，同时返回r。
         r.data(k,v)则把一个键值对赋值给r对象的data(HashMap)属性，返回r。
         k : String v : list */
        return R.ok().data("items",list);
    }

    // 2.逻辑删除讲师
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true)
                                     @PathVariable("id") String id){
        boolean flag = teacherService.removeById(id);
        return flag ? R.ok() : R.error();
    }

}

