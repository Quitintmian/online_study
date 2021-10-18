package com.gyq.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gyq.commonutils.R;
import com.gyq.eduservice.entity.EduTeacher;
import com.gyq.eduservice.entity.vo.TeacherVo;
import com.gyq.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 * 有点像后端封装，前端解析的过程
 * @author guyaqing
 * @since 2021-10-17
 */

@Api(tags = "讲师Controller")
@RestController // = @Controller + @ResponseBody
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

    // 3.分页查询讲师
    @ApiOperation(value = "分页查询讲师")
    @GetMapping(value = "pageTeacher/{current}/{limit}")
    public R pageListTeacher(
                             @ApiParam(name = "current",value = "当前页码",required = true)
                             @PathVariable long current,
                             @ApiParam(name = "limit",value = "每一页记录数",required = true)
                             @PathVariable long limit){
        // 创建page对象 , 实际执行sql是 limit current - 1,limit
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        // 分页后的讲师集合 自封装到pageTeacher中
        teacherService.page(pageTeacher,null);
        long total = pageTeacher.getTotal(); // 总页数
        List<EduTeacher> teachers = pageTeacher.getRecords(); //数据list集合
        Map map = new HashMap();
        map.put("total",total);
        map.put("rows",teachers);
        return R.ok().data(map);
    }

    // 4.条件查询带分页
    // GET方式无请求体，所以使用@RequestBody接收数据时，前端不能使用GET方式提交数据，而是用POST方式进行提交；
    @ApiOperation(value = "条件查询带分页")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageQueryWithCondition(@PathVariable long current,
                                    @PathVariable long limit,
                                    @RequestBody(required = false) TeacherVo teacherVo){
        Page<EduTeacher> pageVo = new Page<>(current,limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        // 条件查询,创建QueryWrapper 类似动态sql过程
        String name = teacherVo.getName();
        Integer level = teacherVo.getLevel();
        String begin = teacherVo.getBegin();
        String end = teacherVo.getEnd();
        if (!StringUtils.isEmpty(name)){
            // creat conditions
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(level)){
            wrapper.eq("level",level); // ==
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_creat",begin); // >=
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("gmt_creat",end); // <=
        }

        teacherService.page(pageVo,wrapper);
        long total = pageVo.getTotal();
        List<EduTeacher> records = pageVo.getRecords();

        return R.ok().data("total",total).data("rows",records);
    }


}

