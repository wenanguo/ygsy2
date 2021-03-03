package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.cmtt.base.controller.miniappparams.BannerInputParam;
import com.cmtt.base.entity.R;
import com.cmtt.base.entity.validated.GroupAdd;
import com.cmtt.base.entity.validated.GroupDelete;
import com.cmtt.base.entity.validated.GroupEdit;
import com.cmtt.base.service.ISsCustomerAddressService;
import com.cmtt.base.utils.RC;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import com.cmtt.base.entity.SsBannerInfo;
import com.cmtt.base.service.ISsBannerInfoService;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * banner信息 前端控制器
 * </p>
 *
 * @author yuzm
 * @since 2021-01-28
 */
@RestController
@RequestMapping("/api/base/ss-banner-info")
@Api(tags = "广告相关")
public class SsBannerInfoController {

    private final Logger logger = LoggerFactory.getLogger(SsBannerInfoController.class);

    @Autowired
    public ISsBannerInfoService ssBannerInfoService;

    /**
     * 分页获取列表
     */
    @GetMapping("/list")
    @ResponseBody
    public R list(SsBannerInfo ssBannerInfo) {

        try {
            //获取查询条件，然后置空，防止框架因为有值而做等值查询——而这里需要做like查询
            String bannerName = ssBannerInfo.getBannerName();
            ssBannerInfo.setBannerName(null);
            // 构建分页类
            IPage<SsBannerInfo> ssBannerInfoPage = new Page<>(ssBannerInfo.getPageNo(), ssBannerInfo.getPageSize());

            // 构造查询及排序方式
            QueryWrapper<SsBannerInfo> queryWrapper = new QueryWrapper<>(ssBannerInfo);

            if(bannerName!=null && !bannerName.equals("")) {
                queryWrapper.like("banner_name", bannerName);
            }

            queryWrapper.orderBy(true, ssBannerInfo.getIsAsc(), ssBannerInfo.getIsSortField());

            // 执行查询
            ssBannerInfoPage = ssBannerInfoService.page(ssBannerInfoPage, queryWrapper);

            // 设置返回数据
            return R.ok().setPageResult(ssBannerInfoPage);
        } catch (Exception e) {

            logger.warn(e.getMessage());

            return R.err().setMessage("系统错误");
        }
    }
    /**
     * 小程序获取广告列表
     */
    @PostMapping("/bannerList.app")
    @ResponseBody
    @ApiOperation("获取主页广告列表")
    public R bannerList(@RequestBody @Valid BannerInputParam param) {

        try {
            // 默认前10条数据，数据大小应该放到数据库中，方便以后更改，目前写死
            IPage<SsBannerInfo> ssBannerInfoPage = new Page<>(0, 10);
            // 构造查询及排序方式
            LambdaQueryWrapper<SsBannerInfo> queryWrapper = Wrappers.<SsBannerInfo>lambdaQuery()
                    .gt(SsBannerInfo::getExpiredTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()))//失效时间之前
                    .orderByDesc(SsBannerInfo::getSort)//序号降序
                    .eq(SsBannerInfo::getStatus, RC.B_NORMAL.code())//状态是启用
                    .eq(SsBannerInfo::getUseFlag, param.getUseFlag());//使用界面
            // 执行查询
            ssBannerInfoPage = ssBannerInfoService.page(ssBannerInfoPage, queryWrapper);

            // 设置返回数据
            return R.ok().setPageResult(ssBannerInfoPage);
        } catch (Exception e) {

            logger.warn(e.getMessage());

            return R.err().setMessage("系统错误");
        }
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    @ResponseBody
    public R add(@RequestBody @Validated({GroupAdd.class})SsBannerInfo ssBannerInfo) {
        try {
            //ssBannerInfo.setCreateBy();
            ssBannerInfoService.save(ssBannerInfo);

            return R.ok().setMessage("新增成功");

        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("新增失败");
        }
    }


    /**
     * 修改
     */
    @PutMapping("/edit")
    @ResponseBody
    public R edit(@RequestBody  @Validated({GroupEdit.class})SsBannerInfo ssBannerInfo) {
        try {
            //ssBannerInfo.setLastUpdateBy();
            ssBannerInfo.setCreateTime(null);//置空，避免框架更新成当前时间
            ssBannerInfoService.updateById(ssBannerInfo);

            return R.ok().setMessage("修改成功");

        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("修改失败");
        }
    }


    /**
     * 删除
     */
    @DeleteMapping("/delete")
    @ResponseBody
    public R delete(@RequestBody @Validated({GroupDelete.class})SsBannerInfo ssBannerInfo) {

        try {

            ssBannerInfoService.removeById(ssBannerInfo.getId());

            return R.ok().setMessage("删除成功");
        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("删除失败");
        }
    }


    /**
     * 删除
     */
    @DeleteMapping("/batchDelete")
    @ResponseBody
    public R batchDelete(@RequestBody List<Integer> ids) {
        try {

            ssBannerInfoService.removeByIds(ids);

            return R.ok().setMessage("批量删除成功");
        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("批量删除失败");
        }
    }



}
