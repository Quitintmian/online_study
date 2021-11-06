package com.gyq.eduservice.controller;


import com.gyq.commonutils.R;
import com.gyq.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author guyaqing
 * @since 2021-11-06
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    // 添加课程分类 从上传文件读取
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        // 这里传入subjectService的目的是为了将service对象传入SubjectExcelListener，原因是监听器不能交由spring管理，使用自动注入
        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }
}

