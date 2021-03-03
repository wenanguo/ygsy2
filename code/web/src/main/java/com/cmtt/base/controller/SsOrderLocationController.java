package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.cmtt.base.config.ss.configuration.JwtAuthenticationToken;
import com.cmtt.base.controller.miniappparams.LocationInputParam;
import com.cmtt.base.entity.R;
import com.cmtt.base.entity.SysUser;
import com.cmtt.base.entity.validated.GroupAdd;
import com.cmtt.base.entity.validated.GroupDelete;
import com.cmtt.base.entity.validated.GroupEdit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.cmtt.base.entity.SsOrderLocation;
import com.cmtt.base.service.ISsOrderLocationService;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * 订单轨迹信息 前端控制器
 * </p>
 *
 * @author yuzm
 * @since 2021-02-18
 */
@RestController
@RequestMapping("/api/base/ss-order-location")
@Api(tags = "订单配送轨迹")
public class SsOrderLocationController {

    private final Logger logger = LoggerFactory.getLogger(SsOrderLocationController.class);

    @Autowired
    public ISsOrderLocationService ssOrderLocationService;


    /**
     * 分页获取列表
     */
    /*@GetMapping("/list")
    @ResponseBody
    public R list(SsOrderLocation ssOrderLocation) {

        try {

            // 构建分页类
            IPage<SsOrderLocation> ssOrderLocationPage = new Page<>(ssOrderLocation.getPageNo(), ssOrderLocation.getPageSize());

            // 构造查询及排序方式
            QueryWrapper<SsOrderLocation> queryWrapper = new QueryWrapper<>(ssOrderLocation);
            queryWrapper.orderBy(true, ssOrderLocation.getIsAsc(), ssOrderLocation.getIsSortField());

            // 执行查询
            ssOrderLocationPage = ssOrderLocationService.page(ssOrderLocationPage, queryWrapper);

            // 设置返回数据
            return R.ok().setPageResult(ssOrderLocationPage);


        } catch (Exception e) {

            logger.warn(e.getMessage());

            return R.err().setMessage("系统错误");
        }
    }*/

    /**
     * 获取配送轨迹信息列表
     */
    @PostMapping("/list.app")
    @ResponseBody
    @ApiOperation("获取配送轨迹信息")
    public R listNoPage(@RequestBody @Valid LocationInputParam param) {
        try {
            Map<BigDecimal, BigDecimal> locList = ssOrderLocationService.getLocationList(param.getOrderNumber());

            // 设置返回数据
            return R.ok().setResult(locList);
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return R.err().setMessage("系统错误");
        }
    }


    /**
     * 小程序端：新增配送员定位信息
     */
    @PostMapping("/add.app")
    @ResponseBody
    @ApiOperation("新增配送员的定位信息")
    public R add(@RequestBody @Validated({GroupAdd.class}) SsOrderLocation ssOrderLocation, Principal principal) {

        try {
            if(null != principal){
                SysUser sysUser = (SysUser) ((JwtAuthenticationToken) principal).getPrincipal();
                ssOrderLocation.setDeliveryManId(sysUser.getId());
            }else{
                return R.err().setMessage("配送员信息获取失败");
            }
            ssOrderLocation.setUpdateTime(LocalDateTime.now());
            ssOrderLocationService.save(ssOrderLocation);

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
    public R edit(@RequestBody @Validated({GroupEdit.class}) SsOrderLocation ssOrderLocation) {


        try {

            ssOrderLocationService.updateById(ssOrderLocation);

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
    public R delete(@RequestBody @Validated({GroupDelete.class}) SsOrderLocation ssOrderLocation) {

        try {

            ssOrderLocationService.removeById(ssOrderLocation.getId());

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

            ssOrderLocationService.removeByIds(ids);

            return R.ok().setMessage("批量删除成功");
        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("批量删除失败");
        }
    }


}
