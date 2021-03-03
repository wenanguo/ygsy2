package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.cmtt.base.entity.R;
import com.cmtt.base.entity.validated.GroupAdd;
import com.cmtt.base.entity.validated.GroupDelete;
import com.cmtt.base.entity.validated.GroupEdit;
import com.cmtt.base.utils.BaiduMapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import com.cmtt.base.entity.SsRegionInfo;
import com.cmtt.base.service.ISsRegionInfoService;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 区域信息表 前端控制器
 * </p>
 *
 * @author yuzm
 * @since 2021-02-19
 */
@RestController
@RequestMapping("/api/base/ss-region-info")
public class SsRegionInfoController {

    private final Logger logger = LoggerFactory.getLogger(SsRegionInfoController.class);

    @Autowired
    public ISsRegionInfoService ssRegionInfoService;


    /**
     * 分页获取列表
     */
    @GetMapping("/list")
    @ResponseBody
    public R list(SsRegionInfo ssRegionInfo) {

        try {

// 构建分页类
            IPage<SsRegionInfo> ssRegionInfoPage = new Page<>(ssRegionInfo.getPageNo(), ssRegionInfo.getPageSize());

            // 构造查询及排序方式
            QueryWrapper<SsRegionInfo> queryWrapper = new QueryWrapper<>(ssRegionInfo);
            queryWrapper.orderBy(true, ssRegionInfo.getIsAsc(), ssRegionInfo.getIsSortField());

            // 执行查询
            ssRegionInfoPage = ssRegionInfoService.page(ssRegionInfoPage, queryWrapper);

            /*//根据区域中文查询国家行政区域编码并更新
            List<Map<String, String>> regionList = ssRegionInfoService.getRegionList();
            BaiduMapUtil util = new BaiduMapUtil();
            for(Map<String, String> region : regionList){
                String areacode = region.get("areacode");
                String areadesc = region.get("areadesc");
                String rcode = util.parseAddress(areadesc);
                System.out.println(areacode+"--"+areadesc+"=="+rcode);

                int r = ssRegionInfoService.updateRegion(rcode, areacode);
                System.out.println("--------------"+r+"--------------");
            }*/

            // 设置返回数据
            return R.ok().setPageResult(ssRegionInfoPage);


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
    public R add(@RequestBody @Validated({GroupAdd.class}) SsRegionInfo ssRegionInfo) {

        try {
            ssRegionInfoService.save(ssRegionInfo);

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
    public R edit(@RequestBody @Validated({GroupEdit.class}) SsRegionInfo ssRegionInfo) {


        try {

            ssRegionInfoService.updateById(ssRegionInfo);

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
    public R delete(@RequestBody @Validated({GroupDelete.class}) SsRegionInfo ssRegionInfo) {

        try {

            ssRegionInfoService.removeById(ssRegionInfo.getAreaId());

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

            ssRegionInfoService.removeByIds(ids);

            return R.ok().setMessage("批量删除成功");
        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("批量删除失败");
        }
    }


}
