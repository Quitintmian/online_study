package com.gyq.cms.controller;

import com.gyq.cms.entity.CrmBanner;
import com.gyq.cms.service.CrmBannerService;
import com.gyq.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * <p>
 * banner客户 控制器
 * </p>
 *
 * @author guyaqing
 * @since 2021-11-14
 */
@RestController
@RequestMapping("/cms/banner/front")
@CrossOrigin
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;


    @GetMapping("getAllBanner")
    public R getAllBanner(){
        List<CrmBanner> list = bannerService.selectAllBanner();
        return R.ok().data("bannerList",list);
    }
}
