package com.gyq.eduservice.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class EasyExcelTest {
    public static void main(String[] args) {
        easyRead();
    }

    /**
     * 写
     */
    public static void easyWrite(){
        String fileName = "C:\\Data.xlsx";
        EasyExcel.write(fileName,TestData.class).sheet("学生列表").doWrite(getData());
    }

    /**
     * 读
     */
    public static void easyRead(){
        String fileName = "C:\\Data.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, TestData.class, new ExcelListener()).sheet().doRead();
    }

    // 新建数据测试
    private static List<TestData> getData(){
        List<TestData> dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TestData testData = new TestData();
            testData.setSno(i);
            testData.setSname("lucy" + i);
            dataList.add(testData);
        }
        return dataList;
    }
}
