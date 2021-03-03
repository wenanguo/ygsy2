package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.cmtt.base.entity.R;
import com.cmtt.base.entity.validated.GroupAdd;
import com.cmtt.base.entity.validated.GroupDelete;
import com.cmtt.base.entity.validated.GroupEdit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.cmtt.base.entity.SsPaymentPoints;
import com.cmtt.base.service.ISsPaymentPointsService;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户消费积分信息 前端控制器
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-02-11
 */
@RestController
@RequestMapping("/api/base/ss-payment-points")
public class SsPaymentPointsController {

private final Logger logger = LoggerFactory.getLogger(SsPaymentPointsController.class);

@Autowired
public ISsPaymentPointsService ssPaymentPointsService;


/**
* 分页获取列表
*/
@GetMapping("/list")
@ResponseBody
public R list(SsPaymentPoints ssPaymentPoints,String customerId) {

try {



// 构建分页类
IPage<SsPaymentPoints> ssPaymentPointsPage = new Page<>(ssPaymentPoints.getPageNo(), ssPaymentPoints.getPageSize());

    // 构造查询及排序方式
    QueryWrapper<SsPaymentPoints> queryWrapper = new QueryWrapper<>(ssPaymentPoints);
    if(customerId==null){
        queryWrapper.eq("customer_Id",null);
    }

        queryWrapper.orderBy(true, ssPaymentPoints.getIsAsc(), ssPaymentPoints.getIsSortField());

        // 执行查询
        ssPaymentPointsPage = ssPaymentPointsService.page(ssPaymentPointsPage, queryWrapper);

        // 设置返回数据
        return R.ok().setPageResult(ssPaymentPointsPage);


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
        public R add(@RequestBody @Validated({GroupAdd.class})SsPaymentPoints ssPaymentPoints) {

        try {
        ssPaymentPointsService.save(ssPaymentPoints);

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
        public R edit(@RequestBody  @Validated({GroupEdit.class})SsPaymentPoints ssPaymentPoints) {


        try {

        ssPaymentPointsService.updateById(ssPaymentPoints);

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
        public R delete(@RequestBody @Validated({GroupDelete.class})SsPaymentPoints ssPaymentPoints) {

        try {

        ssPaymentPointsService.removeById(ssPaymentPoints.getId());

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

            ssPaymentPointsService.removeByIds(ids);

            return R.ok().setMessage("批量删除成功");
            } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("批量删除失败");
            }
            }



}
