package com.gyq.servicebase.exceptionhandler;

import com.gyq.commonutils.ExceptionUtil;
import com.gyq.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 出现了什么异常就执行哪个方法，返回对应的json
    @ExceptionHandler(Exception.class)
    @ResponseBody // 为了返回数据
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message(e.getMessage());
    }

    // 自定义异常处理
    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public R error(GuliException e){
        log.error(ExceptionUtil.getMessage(e)); // 向日志文件中添加异常信息
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
