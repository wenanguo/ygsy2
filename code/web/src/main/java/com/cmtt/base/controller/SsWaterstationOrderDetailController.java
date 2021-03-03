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

import com.cmtt.base.entity.SsWaterstationOrderDetail;
import com.cmtt.base.service.ISsWaterstationOrderDetailService;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单详情 前端控制器
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-02-21
 */
@RestController
@RequestMapping("/api/base/ss-waterstation-order-detail")
public class SsWaterstationOrderDetailController {

private final Logger logger = LoggerFactory.getLogger(SsWaterstationOrderDetailController.class);

@Autowired
public ISsWaterstationOrderDetailService ssWaterstationOrderDetailService;


/**
* 分页获取列表
*/
@GetMapping("/list")
@ResponseBody
public R list(SsWaterstationOrderDetail ssWaterstationOrderDetail) {

try {

// 构建分页类
IPage<SsWaterstationOrderDetail> ssWaterstationOrderDetailPage = new Page<>(ssWaterstationOrderDetail.getPageNo(), ssWaterstationOrderDetail.getPageSize());

    // 构造查询及排序方式
    QueryWrapper<SsWaterstationOrderDetail> queryWrapper = new QueryWrapper<>(ssWaterstationOrderDetail);
        queryWrapper.orderBy(true, ssWaterstationOrderDetail.getIsAsc(), ssWaterstationOrderDetail.getIsSortField());

        // 执行查询
        ssWaterstationOrderDetailPage = ssWaterstationOrderDetailService.page(ssWaterstationOrderDetailPage, queryWrapper);

        // 设置返回数据
        return R.ok().setPageResult(ssWaterstationOrderDetailPage);


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
        public R add(@RequestBody @Validated({GroupAdd.class})SsWaterstationOrderDetail ssWaterstationOrderDetail) {

        try {
        ssWaterstationOrderDetailService.save(ssWaterstationOrderDetail);

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
        public R edit(@RequestBody  @Validated({GroupEdit.class})SsWaterstationOrderDetail ssWaterstationOrderDetail) {


        try {

        ssWaterstationOrderDetailService.updateById(ssWaterstationOrderDetail);

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
        public R delete(@RequestBody @Validated({GroupDelete.class})SsWaterstationOrderDetail ssWaterstationOrderDetail) {

        try {

        ssWaterstationOrderDetailService.removeById(ssWaterstationOrderDetail.getId());

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

            ssWaterstationOrderDetailService.removeByIds(ids);

            return R.ok().setMessage("批量删除成功");
            } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("批量删除失败");
            }
            }



}
