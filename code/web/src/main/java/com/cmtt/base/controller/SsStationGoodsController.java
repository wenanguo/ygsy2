package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.cmtt.base.entity.R;
import com.cmtt.base.entity.validated.GroupAdd;
import com.cmtt.base.entity.validated.GroupDelete;
import com.cmtt.base.entity.validated.GroupEdit;
import com.cmtt.base.utils.RC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import com.cmtt.base.entity.SsStationGoods;
import com.cmtt.base.service.ISsStationGoodsService;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 水站商品信息 前端控制器
 * </p>
 *
 * @author yuzm
 * @since 2021-01-31
 */
@RestController
@RequestMapping("/api/base/ss-station-goods")
public class SsStationGoodsController {

    private final Logger logger = LoggerFactory.getLogger(SsStationGoodsController.class);

    @Autowired
    public ISsStationGoodsService ssStationGoodsService;


    /**
     * 分页获取列表
     */
    @GetMapping("/list")
    @ResponseBody
    public R list(SsStationGoods ssStationGoods) {

        try {
            //获取查询条件，然后置空，防止框架因为有值而做等值查询——而这里需要做like查询
            Integer stationId = ssStationGoods.getStationId();
            Integer status = ssStationGoods.getStatus();

            // 构建分页类
            IPage<SsStationGoods> ssStationGoodsPage = new Page<>(ssStationGoods.getPageNo(), ssStationGoods.getPageSize());

            // 构造查询及排序方式
            QueryWrapper<SsStationGoods> queryWrapper = new QueryWrapper<>(ssStationGoods);
            queryWrapper.orderBy(true, ssStationGoods.getIsAsc(), ssStationGoods.getIsSortField());
            if (stationId != null) {
                queryWrapper.eq("a.station_id", stationId);
            }
            if (status != null) {
                queryWrapper.eq("a.status", status);
            }

            // 执行查询
            // ssStationGoodsPage = ssStationGoodsService.page(ssStationGoodsPage, queryWrapper);

            // 重写的分页查询
            ssStationGoodsPage = ssStationGoodsService.getStationGoodsPage(ssStationGoodsPage, queryWrapper);

            // 设置返回数据
            return R.ok().setPageResult(ssStationGoodsPage);
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
    public R add(@RequestBody @Validated({GroupAdd.class}) SsStationGoods ssStationGoods) {

        try {
            String[] goodsIdArray = ssStationGoods.getGoodsIdArray();
            if (goodsIdArray.length > 0) {
                for (String id : goodsIdArray) {
                    Integer goodsId = Integer.parseInt(id);
                    ssStationGoods.setGoodsId(goodsId);
                    ssStationGoodsService.save(ssStationGoods);

                    //水站新增商品时，统一设置归属商品的价格
                    ssStationGoodsService.updateDefaultSalePrice(ssStationGoods.getStationId(), goodsId);
                }
            } else {
                return R.err().setMessage("未获取商品Id，新增失败");
            }

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
    public R edit(@RequestBody @Validated({GroupEdit.class}) SsStationGoods ssStationGoods) {
        try {
            ssStationGoodsService.updateById(ssStationGoods);
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
    public R delete(@RequestBody @Validated({GroupDelete.class}) SsStationGoods ssStationGoods) {

        try {

            ssStationGoodsService.removeById(ssStationGoods.getId());

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

            ssStationGoodsService.removeByIds(ids);

            return R.ok().setMessage("批量删除成功");
        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("批量删除失败");
        }
    }


    /**
     * 修改水站关联的商品售价
     */
    @PutMapping("/updateSalePrice")
    @ResponseBody
    public R updateSalePrice(@RequestBody @Validated({GroupEdit.class}) SsStationGoods ssStationGoods) {
        try {
            ssStationGoodsService.updateSalePrice(ssStationGoods);
            return R.ok().setMessage("修改成功");
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return R.err().setMessage("修改失败");
        }
    }


    /**
     * 获取商品信息字典
     */
    @GetMapping("/goodsDict")
    @ResponseBody
    public R getGoodsInfoDict(Integer stationId) {
        try {
            List<Map<Integer, String>> list = ssStationGoodsService.getGoodsDict(RC.B_NORMAL.code(), stationId);
            // 设置返回数据
            return R.ok().setResult(list);
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return R.err().setMessage("系统错误");
        }
    }
}
