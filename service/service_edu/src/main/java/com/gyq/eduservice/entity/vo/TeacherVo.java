package com.gyq.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeacherVo {
    @ApiModelProperty(value = "教师名称")
    private String name;
    @ApiModelProperty(value = "头衔")
    private Integer level;
    @ApiModelProperty(value = "开始时间")
    private String begin; // 查询开始时间
    @ApiModelProperty(value = "结束时间")
    private String end; // 查询结束时间


}
