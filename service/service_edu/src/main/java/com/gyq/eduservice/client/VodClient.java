package com.gyq.eduservice.client;

import com.gyq.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// 指定调用的服务 , 和 熔断处理的类
@FeignClient(value = "service-vod",fallback = VodFileDegradeFeignClient.class)
@Component // 交由spring
public interface VodClient {

    // 定义调用的方法路径  @Path 必须指定参数
    @DeleteMapping("/eduvod/video/removeVideo/{id}")
    public R removeAlyVideo(@PathVariable("id") String id);

    // 批量删除视频
    @DeleteMapping("/eduvod/video/deleteBatch")
    public R deleteBatch(@RequestParam("ids") List<String> ids);

}
