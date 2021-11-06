package com.gyq.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gyq.eduservice.entity.EduSubject;
import com.gyq.eduservice.entity.excel.SubjectData;
import com.gyq.eduservice.listener.SubjectExcelListener;
import com.gyq.eduservice.mapper.EduSubjectMapper;
import com.gyq.eduservice.service.EduSubjectService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;


/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author guyaqing
 * @since 2021-11-06
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    // 添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
        try {
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
