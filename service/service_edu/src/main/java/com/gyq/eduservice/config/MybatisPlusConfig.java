package com.gyq.eduservice.config;

import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Configuration
@MapperScan("com.gyq.eduservice.mapper")
public class MybatisPlusConfig {
    /**
     * 性能分析
     */
    @Bean
    @Profile({"dev","test"}) // dev,test 开启以下配置
    public PerformanceInterceptor performanceInterceptor(){
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        performanceInterceptor.setMaxTime(100); //超过100ms则sql不执行,并抛出异常
        performanceInterceptor.setFormat(false);
        return performanceInterceptor;
    }
}
