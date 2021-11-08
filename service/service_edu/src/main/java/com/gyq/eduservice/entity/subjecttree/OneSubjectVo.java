package com.gyq.eduservice.entity.subjecttree;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

// 树形 一级分类
@Data
public class OneSubjectVo {
    private String id;
    private String title;
    // 所含的二级分类集合
    private List<TwoSubjectVo> children = new ArrayList<>();
}
