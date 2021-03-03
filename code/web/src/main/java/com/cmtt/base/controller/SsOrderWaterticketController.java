package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.cmtt.base.config.ss.configuration.JwtAuthenticationToken;
import com.cmtt.base.entity.R;
import com.cmtt.base.entity.SysUser;
import com.cmtt.base.entity.validated.GroupAdd;
import com.cmtt.base.entity.validated.GroupDelete;
import com.cmtt.base.entity.validated.GroupEdit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import com.cmtt.base.entity.SsOrderWaterticket;
import com.cmtt.base.service.ISsOrderWaterticketService;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单水票关系信息 前端控制器
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-30
 */
@RestController
@RequestMapping("/api/base/ss-order-waterticket")
public class SsOrderWaterticketController {

private final Logger logger = LoggerFactory.getLogger(SsOrderWaterticketController.class);

@Autowired
public ISsOrderWaterticketService ssOrderWaterticketService;


/**
* 分页获取列表
*/
@GetMapping("/list")
@ResponseBody
public R list(SsOrderWaterticket ssOrderWaterticket, Principal principal,String customerId, String status) {


try {

    // 构建分页类
    IPage<SsOrderWaterticket> ssOrderWaterticketPage = new Page<>(ssOrderWaterticket.getPageNo(), ssOrderWaterticket.getPageSize());
    //获取session用户信息
    SysUser sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal();
    if(sysUser==null){
        return R.err().setMessage("当前用户未登录");
    }else{
        // 构造查询及排序方式
        QueryWrapper<SsOrderWaterticket> queryWrapper = new QueryWrapper<>(ssOrderWaterticket);

        if(sysUser.getId()!=null && !"".equals(sysUser.getId())) {
            queryWrapper.like("customer_id", sysUser.getId());
        }

        if(sysUser.getStatus()!=null && !"".equals(sysUser.getStatus())) {
            queryWrapper.like("status", sysUser.getStatus());
        }

        queryWrapper.orderBy(true, ssOrderWaterticket.getIsAsc(), ssOrderWaterticket.getIsSortField());

        // 执行查询
        ssOrderWaterticketPage = ssOrderWaterticketService.page(ssOrderWaterticketPage, queryWrapper);

        // 设置返回数据
        return R.ok().setPageResult(ssOrderWaterticketPage);
    }

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
        public R add(@RequestBody @Validated({GroupAdd.class})SsOrderWaterticket ssOrderWaterticket) {

        try {
        ssOrderWaterticketService.save(ssOrderWaterticket);

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
        public R edit(@RequestBody  @Validated({GroupEdit.class})SsOrderWaterticket ssOrderWaterticket) {


        try {

        ssOrderWaterticketService.updateById(ssOrderWaterticket);

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
        public R delete(@RequestBody @Validated({GroupDelete.class})SsOrderWaterticket ssOrderWaterticket) {

        try {

        ssOrderWaterticketService.removeById(ssOrderWaterticket.getId());

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

            ssOrderWaterticketService.removeByIds(ids);

            return R.ok().setMessage("批量删除成功");
            } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("批量删除失败");
            }
            }



}
