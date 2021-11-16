package com.gyq.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gyq.eduservice.client.VodClient;
import com.gyq.eduservice.entity.EduVideo;
import com.gyq.eduservice.mapper.EduVideoMapper;
import com.gyq.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author guyaqing
 * @since 2021-11-08
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    // 远程调用
    @Autowired
    VodClient vodClient;

    @Override // 删除小节，还要删除视频文件
    public void removeVideoByCourseId(String courseId) {

        // 根据课程id 查出所有 视频id 封装成集合

        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        wrapperVideo.select("video_source_id");
        List<EduVideo> videoList = baseMapper.selectList(wrapperVideo);

        List<String> ids = videoList.stream().filter(id->!StringUtils.isEmpty(id))
                .map(EduVideo::getVideoSourceId).collect(Collectors.toList());

        if (ids.size() > 0) vodClient.deleteBatch(ids); // 执行批量删除

        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
