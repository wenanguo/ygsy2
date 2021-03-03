package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmtt.base.config.ss.configuration.JwtAuthenticationToken;
import com.cmtt.base.controller.miniappparams.OrderDeliveryInputParam;
import com.cmtt.base.entity.R;
import com.cmtt.base.entity.SsOrderDelivery;
import com.cmtt.base.entity.SysUser;
import com.cmtt.base.entity.validated.GroupAdd;
import com.cmtt.base.entity.validated.GroupDelete;
import com.cmtt.base.entity.validated.GroupEdit;
import com.cmtt.base.service.ISsOrderDeliveryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 水站订单配送信息 前端控制器
 * </p>
 *
 * @author yuzm
 * @since 2021-02-14
 */
@RestController
@RequestMapping("/api/base/ss-waterstation-order-delivery")
@Api(tags = "配送员接单相关")
public class SsWaterstationOrderDeliveryController {

    private final Logger logger = LoggerFactory.getLogger(SsWaterstationOrderDeliveryController.class);

    @Autowired
    public ISsOrderDeliveryService ssOrderDeliveryService;


    /**
     * 分页获取列表
     */
    @GetMapping("/list")
    @ResponseBody
    public R list(SsOrderDelivery ssOrderDelivery) {

        try {

            // 构建分页类
            IPage<SsOrderDelivery> ssOrderDeliveryPage = new Page<>(ssOrderDelivery.getPageNo(), ssOrderDelivery.getPageSize());

            // 构造查询及排序方式
            QueryWrapper<SsOrderDelivery> queryWrapper = new QueryWrapper<>(ssOrderDelivery);
            queryWrapper.orderBy(true, ssOrderDelivery.getIsAsc(), ssOrderDelivery.getIsSortField());

            // 执行查询
            ssOrderDeliveryPage = ssOrderDeliveryService.page(ssOrderDeliveryPage, queryWrapper);

            // 设置返回数据
            return R.ok().setPageResult(ssOrderDeliveryPage);


        } catch (Exception e) {

            logger.warn(e.getMessage());

            return R.err().setMessage("系统错误");
        }
    }


    /**
     * 接单
     */
    @PostMapping("/add")
    @ResponseBody
    @ApiOperation("配送员接单")
    public R add(@RequestBody @Validated({GroupAdd.class}) OrderDeliveryInputParam param, Principal principal) {
        try {
            SsOrderDelivery ssOrderDelivery = new SsOrderDelivery();
            ssOrderDelivery.setOrderNumber(param.getOrderNumber());
            if(null != principal){
                SysUser sysUser = (SysUser) ((JwtAuthenticationToken) principal).getPrincipal();
                ssOrderDelivery.setDeliveryManId(sysUser.getId());
            }else{
                return R.err().setMessage("配送员信息获取失败");
            }
            ssOrderDelivery.setDeliveryType(2);//设置为水厂配送
            ssOrderDelivery.setReceiveTime(LocalDateTime.now());
            ssOrderDeliveryService.save(ssOrderDelivery);//保存接单配送信息
            ssOrderDeliveryService.updateOrderStatusOfStation(2, param.getOrderNumber());//订单更新为已接单
            return R.ok().setMessage("接单成功");
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return R.err().setMessage("接单失败");
        }
    }

    /**
     * 开始配送
     */
    @PostMapping("/delivery")
    @ResponseBody
    @ApiOperation("配送员开始配送订单")
    public R delivery(@RequestBody @Validated({GroupEdit.class}) OrderDeliveryInputParam param, Principal principal) {
        try {
            Integer userId = null;
            if(null != principal){
                SysUser sysUser = (SysUser) ((JwtAuthenticationToken) principal).getPrincipal();
                userId = sysUser.getId();
            }else{
                return R.err().setMessage("配送员信息获取失败");
            }

            ssOrderDeliveryService.delivery(LocalDateTime.now(), param.getOrderNumber(), userId);
            ssOrderDeliveryService.updateOrderStatusOfStation(3, param.getOrderNumber());//订单更新为配送中
            return R.ok().setMessage("开始配送");
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return R.err().setMessage("更新配送中状态失败");
        }
    }

    /**
     * 完成配送
     */
    @PostMapping("/finish")
    @ResponseBody
    @ApiOperation("配送员完成配送")
    public R finish(@RequestBody @Validated({GroupEdit.class}) OrderDeliveryInputParam param, Principal principal) {
        try {
            Integer userId = null;
            if(null != principal){
                SysUser sysUser = (SysUser) ((JwtAuthenticationToken) principal).getPrincipal();
                userId = sysUser.getId();
            }else{
                return R.err().setMessage("配送员信息获取失败");
            }

            ssOrderDeliveryService.finish(LocalDateTime.now(),param.getOrderNumber(), userId);
            ssOrderDeliveryService.updateOrderStatusOfStation(4, param.getOrderNumber());//订单更新为已完成
            return R.ok().setMessage("配送完成");
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return R.err().setMessage("更新配送完成状态失败");
        }
    }


    /**
     * 修改
     */
    @PutMapping("/edit")
    @ResponseBody
    public R edit(@RequestBody @Validated({GroupEdit.class}) SsOrderDelivery ssOrderDelivery) {


        try {

            ssOrderDeliveryService.updateById(ssOrderDelivery);

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
    public R delete(@RequestBody @Validated({GroupDelete.class}) SsOrderDelivery ssOrderDelivery) {

        try {

            ssOrderDeliveryService.removeById(ssOrderDelivery.getId());

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

            ssOrderDeliveryService.removeByIds(ids);

            return R.ok().setMessage("批量删除成功");
        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("批量删除失败");
        }
    }


}
