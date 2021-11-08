package com.gyq.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gyq.eduservice.entity.EduSubject;
import com.gyq.eduservice.entity.excel.ExcelSubjectData;
import com.gyq.eduservice.entity.subjecttree.OneSubjectVo;
import com.gyq.eduservice.entity.subjecttree.TwoSubjectVo;
import com.gyq.eduservice.listener.SubjectExcelListener;
import com.gyq.eduservice.mapper.EduSubjectMapper;
import com.gyq.eduservice.service.EduSubjectService;
import com.gyq.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


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
            EasyExcel.read(in, ExcelSubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
    } catch (IOException e) {
            e.printStackTrace();
            throw new GuliException(20001,"添加课程分类失败");
        }
    }

    // 获取所有一级和二级的列表树
    @Override
    public List<OneSubjectVo> getAllOneTwoSubject() {

        // 最后要返回的集合
        List<OneSubjectVo> resultSubjectList = new ArrayList<>();

        // 查询一级 parent_id = 0
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne); // 第一次查询数据库

        // 查询二级 parent_id != 0
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");
        List<EduSubject> towSubjectList = baseMapper.selectList(wrapperTwo); // 第二次查询数据库
        // 封装一级
        for (int i = 0; i < oneSubjectList.size(); i++) {
            EduSubject eduSubject = oneSubjectList.get(i);
            OneSubjectVo oneSubjectVo = new OneSubjectVo();
            // oneSubjectVo.setId(eduSubject.getId()); // 取出再封装
            // oneSubjectVo.setTitle(eduSubject.getTitle()); // 这两句等价于下面的工具类
            BeanUtils.copyProperties(eduSubject,oneSubjectVo);
            // 一级分类放入结果集合
            resultSubjectList.add(oneSubjectVo);

            // 对于当前一级分类，循环遍历二级分类集合。判断2级的id (parent_id) 是否 等于 该1级分类的id (id)
            // 存放[该一级分类]的对应[二级分类]的集合
            List<TwoSubjectVo> finalTwoSubjectList = new ArrayList<>();
            for (int j = 0; j < towSubjectList.size(); j++) {
                EduSubject eduSubject_2 = towSubjectList.get(j);
                // 判断2级的id (parent_id) 是否 等于 该1级分类的id (id)
                if (eduSubject_2.getParentId().equals(eduSubject.getId())){ // 1级.id == 2级.parentid
                    // 封装二级
                    TwoSubjectVo twoSubjectVo = new TwoSubjectVo();
                    BeanUtils.copyProperties(eduSubject_2,twoSubjectVo);
                    finalTwoSubjectList.add(twoSubjectVo);
                }
            }

            // finalTwoSubjectList 放入children
            oneSubjectVo.setChildren(finalTwoSubjectList);
        }
        return resultSubjectList;
    }
}
