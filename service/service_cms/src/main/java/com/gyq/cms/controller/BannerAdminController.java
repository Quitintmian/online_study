package com.gyq.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gyq.cms.entity.CrmBanner;
import com.gyq.cms.service.CrmBannerService;
import com.gyq.commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * banner管理员 控制器
 * </p>
 *
 * @author guyaqing
 * @since 2021-11-14
 */
@RestController
@RequestMapping("/cms/banner/admin")
@CrossOrigin
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    // 分页查询
    @GetMapping("{page}/{limit}")
    public R pageBanner(@PathVariable Long page, @PathVariable Long limit) {
        Page<CrmBanner> pageParam = new Page<>(page, limit);
        bannerService.page(pageParam,null);
        return R.ok().data("items", pageParam.getRecords()).data("total", pageParam.getTotal());
    }

    @ApiOperation(value = "获取Banner")
    @GetMapping("get/{id}")
    public R get(@PathVariable String id) {
        CrmBanner banner = bannerService.getById(id);
        return R.ok().data("item", banner);
    }

    @ApiOperation(value = "新增Banner")
    @PostMapping("add")
    public R addBanner(@RequestBody CrmBanner banner) {
        bannerService.save(banner);
        return R.ok();
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping("update")
    public R updateById(@RequestBody CrmBanner banner) {
        bannerService.updateById(banner);
        return R.ok();
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id) {
        bannerService.removeById(id);
        return R.ok();
    }


}

