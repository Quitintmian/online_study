package com.gyq.eduservice.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<TestData> {
    // 从第二行开始逐行读取数据
    @Override
    public void invoke(TestData testData, AnalysisContext analysisContext) {
        System.out.println("每一行 -->" + testData);
    }

    // 读取表头
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头 -->" + headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("完成");
    }
}
