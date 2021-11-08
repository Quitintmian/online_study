package com.gyq.eduservice.service;

import com.gyq.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gyq.eduservice.entity.subjecttree.OneSubjectVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author guyaqing
 * @since 2021-11-06
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file,EduSubjectService subjectService);

    List<OneSubjectVo> getAllOneTwoSubject();
}
