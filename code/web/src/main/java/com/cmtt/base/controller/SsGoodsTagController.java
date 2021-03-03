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

import com.cmtt.base.entity.SsGoodsTag;
import com.cmtt.base.service.ISsGoodsTagService;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 商品标签信息 前端控制器
 * </p>
 *
 * @author yuzm
 * @since 2021-02-06
 */
@RestController
@RequestMapping("/api/base/ss-goods-tag")
public class SsGoodsTagController {

    private final Logger logger = LoggerFactory.getLogger(SsGoodsTagController.class);

    @Autowired
    public ISsGoodsTagService ssGoodsTagService;


    /**
     * 分页获取列表
     */
    @GetMapping("/list")
    @ResponseBody
    public R list(SsGoodsTag ssGoodsTag) {

        try {

// 构建分页类
            IPage<SsGoodsTag> ssGoodsTagPage = new Page<>(ssGoodsTag.getPageNo(), ssGoodsTag.getPageSize());

            // 构造查询及排序方式
            QueryWrapper<SsGoodsTag> queryWrapper = new QueryWrapper<>(ssGoodsTag);
            queryWrapper.orderBy(true, ssGoodsTag.getIsAsc(), ssGoodsTag.getIsSortField());

            // 执行查询
            ssGoodsTagPage = ssGoodsTagService.page(ssGoodsTagPage, queryWrapper);

            // 设置返回数据
            return R.ok().setPageResult(ssGoodsTagPage);


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
    public R add(@RequestBody @Validated({GroupAdd.class}) SsGoodsTag ssGoodsTag) {

        try {
            ssGoodsTagService.save(ssGoodsTag);

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
    public R edit(@RequestBody @Validated({GroupEdit.class}) SsGoodsTag ssGoodsTag) {


        try {

            ssGoodsTagService.updateById(ssGoodsTag);

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
    public R delete(@RequestBody @Validated({GroupDelete.class}) SsGoodsTag ssGoodsTag) {

        try {

            ssGoodsTagService.removeById(ssGoodsTag.getId());

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

            ssGoodsTagService.removeByIds(ids);

            return R.ok().setMessage("批量删除成功");
        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("批量删除失败");
        }
    }

    /**
     * 获取商品的tag id
     */
    @GetMapping("/tagIdList")
    @ResponseBody
    public R getTagIdsByGoodsId(HttpServletRequest request) {

        try {
            String idStr = request.getParameter("goodsId");
            if (null != idStr) {
                List<Integer> list = ssGoodsTagService.getTagIds(Integer.parseInt(idStr));
                // 设置返回数据
                return R.ok().setResult(list);
            } else {
                return R.err().setMessage("无商品ID");
            }

        } catch (Exception e) {

            logger.warn(e.getMessage());

            return R.err().setMessage("系统错误");
        }
    }

}
