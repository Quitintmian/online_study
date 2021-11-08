package com.gyq.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gyq.eduservice.entity.EduSubject;
import com.gyq.eduservice.entity.excel.ExcelSubjectData;
import com.gyq.eduservice.service.EduSubjectService;
import com.gyq.servicebase.exceptionhandler.GuliException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 从Excel中读取数据，并添加到数据库中
 */
public class SubjectExcelListener extends AnalysisEventListener<ExcelSubjectData>{

    // 日志工厂
    public static final Logger logger = LoggerFactory.getLogger(SubjectExcelListener.class);

    // 不能使用@AutoWired，因此手动赋值
    private EduSubjectService subjectService;
    public SubjectExcelListener(){}
    public SubjectExcelListener(EduSubjectService subjectService){
        this.subjectService = subjectService;
    }

    @Override
    public void invoke(ExcelSubjectData subjectData, AnalysisContext analysisContext) {
        logger.info("正在读取..." + subjectData);
        if (subjectData == null)
            throw new GuliException(20001,"文件数据为空");
        // 先读一级分类 , 再读二级分类

        // 获得该行的一级分类，是否已经在数据库中存在
        EduSubject existOneSubject = existOneSubject(subjectData.getOneSubjectName()); // null or exist
        // 不存在，添加一级分类
        if (existOneSubject == null){
            existOneSubject = new EduSubject();
            existOneSubject.setTitle(subjectData.getOneSubjectName());
            existOneSubject.setParentId("0"); //设为1级分类 , 1级分类的 parent_id = 0
            subjectService.save(existOneSubject); // excute SQL
        }

        // 通过一级分类 id , 并处理二级分类
        String pid = existOneSubject.getId();

        // 二级分类属于一级id
        EduSubject existTwoSubject = existTwoSubject(subjectData.getTwoSubjectName(),pid);
        // 二级不存在添加
        if (existTwoSubject == null){
            existTwoSubject = new EduSubject();
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());
            existTwoSubject.setParentId(pid);
            subjectService.save(existTwoSubject);
        }
    }

    private EduSubject existOneSubject(String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",0);
        EduSubject oneSubject = subjectService.getOne(wrapper);
        return oneSubject;
    }

    private EduSubject existTwoSubject(String name,String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject twoSubject = subjectService.getOne(wrapper);
        return twoSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        logger.info("读取完成");
    }

}
