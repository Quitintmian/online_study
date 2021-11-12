package com.gyq.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gyq.eduservice.entity.EduChapter;
import com.gyq.eduservice.entity.EduVideo;
import com.gyq.eduservice.entity.chapter.ChapterVo;
import com.gyq.eduservice.entity.chapter.VideoVo;
import com.gyq.eduservice.mapper.EduChapterMapper;
import com.gyq.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gyq.eduservice.service.EduVideoService;
import com.gyq.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author guyaqing
 * @since 2021-11-08
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    EduVideoService videoService;

    @Autowired
    EduChapterMapper chapterMapper;

    // 得到章节-小节 树
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {

        List<ChapterVo> resultChapterList = new ArrayList<>();

        // 查出对应课程id对应的章节
        QueryWrapper<EduChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        List<EduChapter> chapterList = baseMapper.selectList(queryWrapper);

        // 查出所有章节与小结
        QueryWrapper<EduVideo> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("course_id",courseId);
        List<EduVideo> videoList = videoService.list(queryWrapper1);


        // 通过章节[i]查出对应的小节集合
        /**
        for (int i = 0; i < chapterList.size(); i++) {

            ChapterVo tempChapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapterList.get(i),tempChapterVo);

            resultChapterList.add(tempChapterVo);

            List<VideoVo> videoVoList = new ArrayList<>();
            for (int j = 0; j < videoList.size(); j++) {

                if (videoList.get(j).getChapterId().equals(chapterList.get(i).getId())){
                    VideoVo tempVideoVo = new VideoVo();
                    BeanUtils.copyProperties(videoList.get(j),tempVideoVo);
                    videoVoList.add(tempVideoVo);
                }
            }
            resultChapterList.get(i).setChildren(videoVoList);
        }
        */

        // 花里胡哨
        IntStream.range(0, chapterList.size()).forEach(i -> {
            ChapterVo tempChapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapterList.get(i), tempChapterVo);
            resultChapterList.add(tempChapterVo);
            List<VideoVo> videoVoList = new ArrayList<>();
            videoList.stream().filter(eduVideo -> eduVideo.getChapterId().equals(chapterList.get(i).getId())).forEach(eduVideo -> {
                VideoVo tempVideoVo = new VideoVo();
                BeanUtils.copyProperties(eduVideo, tempVideoVo);
                videoVoList.add(tempVideoVo);
            });
            resultChapterList.get(i).setChildren(videoVoList);
        });

        return resultChapterList;


    }

    // 手写Mapper
    @Override
    public List<ChapterVo> getChapterVideo(String courseId){
        return chapterMapper.getChapterVideo(courseId);
    }

    @Override
    public List<EduChapter> getAll() {
        return chapterMapper.getAll();
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        // 通过章节id查小结表，是否有数据
        // select count(1) from video_table where chapter_id = #{chapterId}
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper();
        queryWrapper.eq("chapter_id",chapterId);
        int count = videoService.count(queryWrapper);
        if (count == 0){
            int result = baseMapper.deleteById(chapterId);
            return result > 0;
        }else throw new GuliException(20001,"不能删除");
    }

    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }

}
