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

import com.cmtt.base.entity.SsBannerInfo;
import com.cmtt.base.service.ISsBannerInfoService;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * banner信息 前端控制器
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-28
 */
@RestController
@RequestMapping("/api/base/ss-banner-info")
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

// 构建分页类
IPage<SsBannerInfo> ssBannerInfoPage = new Page<>(ssBannerInfo.getPageNo(), ssBannerInfo.getPageSize());

    // 构造查询及排序方式
    QueryWrapper<SsBannerInfo> queryWrapper = new QueryWrapper<>(ssBannerInfo);
        queryWrapper.orderBy(true, ssBannerInfo.getIsAsc(), ssBannerInfo.getIsSortField());

        // 执行查询
        ssBannerInfoPage = ssBannerInfoService.getBaseMapper().selectPage(ssBannerInfoPage, queryWrapper);

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
