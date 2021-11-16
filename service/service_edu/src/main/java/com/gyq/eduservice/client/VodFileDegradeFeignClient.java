package com.gyq.eduservice.client;

import com.gyq.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

// 当服务宕机时进行熔断处理，执行实现类的默认方法
@Component
public class VodFileDegradeFeignClient implements VodClient{
    @Override
    public R removeAlyVideo(String id) {
        return R.error().message("服务不可用，删除视频出错了！");
    }

    @Override
    public R deleteBatch(List<String> ids) {
        return R.error().message("服务不可用，删除视频出错了！");
    }
}
