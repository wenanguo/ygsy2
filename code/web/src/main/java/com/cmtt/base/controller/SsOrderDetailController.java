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

import com.cmtt.base.entity.SsOrderDetail;
import com.cmtt.base.service.ISsOrderDetailService;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单详情 前端控制器
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-30
 */
@RestController
@RequestMapping("/api/base/ss-order-detail")
public class SsOrderDetailController {

private final Logger logger = LoggerFactory.getLogger(SsOrderDetailController.class);

@Autowired
public ISsOrderDetailService ssOrderDetailService;


/**
* 分页获取列表
*/
@GetMapping("/list")
@ResponseBody
public R list(SsOrderDetail ssOrderDetail) {

try {

// 构建分页类
IPage<SsOrderDetail> ssOrderDetailPage = new Page<>(ssOrderDetail.getPageNo(), ssOrderDetail.getPageSize());

    // 构造查询及排序方式
    QueryWrapper<SsOrderDetail> queryWrapper = new QueryWrapper<>(ssOrderDetail);
        queryWrapper.orderBy(true, ssOrderDetail.getIsAsc(), ssOrderDetail.getIsSortField());

        // 执行查询
        ssOrderDetailPage = ssOrderDetailService.page(ssOrderDetailPage, queryWrapper);

        // 设置返回数据
        return R.ok().setPageResult(ssOrderDetailPage);


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
        public R add(@RequestBody @Validated({GroupAdd.class})SsOrderDetail ssOrderDetail) {

        try {
        ssOrderDetailService.save(ssOrderDetail);

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
        public R edit(@RequestBody  @Validated({GroupEdit.class})SsOrderDetail ssOrderDetail) {


        try {

        ssOrderDetailService.updateById(ssOrderDetail);

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
        public R delete(@RequestBody @Validated({GroupDelete.class})SsOrderDetail ssOrderDetail) {

        try {

        ssOrderDetailService.removeById(ssOrderDetail.getId());

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

            ssOrderDetailService.removeByIds(ids);

            return R.ok().setMessage("批量删除成功");
            } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("批量删除失败");
            }
            }



}
