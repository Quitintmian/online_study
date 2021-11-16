package com.gyq.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gyq.cms.entity.CrmBanner;
import com.gyq.cms.mapper.CrmBannerMapper;
import com.gyq.cms.service.CrmBannerService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author guyaqing
 * @since 2021-11-14
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    /**
     * 根据方法对其返回结果进行缓存，下次请求时，如果缓存存在，则直接读取缓存数据返回；如果缓存不
     * 存在，则执行方法，并把返回的结果存入缓存中。
     */
    @Override
    // TODO redis注解
    // @Cacheable(key = "'selectIndexList'",value = "banner")
    public List<CrmBanner> selectAllBanner() {
        /**
         *从redis中查
         *根据id降序排列，显示前两条记录
         *SELECT * FROM crm_banner ORDER BY id DESC limit 2
         */
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 2"); // 拼接sql
        return baseMapper.selectList(wrapper);
    }
}
