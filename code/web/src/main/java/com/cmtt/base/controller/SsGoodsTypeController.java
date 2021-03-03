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

import com.cmtt.base.entity.SsGoodsType;
import com.cmtt.base.service.ISsGoodsTypeService;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品分类 前端控制器
 * </p>
 *
 * @author yuzm
 * @since 2021-01-29
 */
@RestController
@RequestMapping("/api/base/ss-goods-type")
public class SsGoodsTypeController {

private final Logger logger = LoggerFactory.getLogger(SsGoodsTypeController.class);

@Autowired
public ISsGoodsTypeService ssGoodsTypeService;


/**
* 分页获取列表
*/
@GetMapping("/list")
@ResponseBody
public R list(SsGoodsType ssGoodsType) {

try {

// 构建分页类
IPage<SsGoodsType> ssGoodsTypePage = new Page<>(ssGoodsType.getPageNo(), ssGoodsType.getPageSize());

    // 构造查询及排序方式
    QueryWrapper<SsGoodsType> queryWrapper = new QueryWrapper<>(ssGoodsType);
        queryWrapper.orderBy(true, ssGoodsType.getIsAsc(), ssGoodsType.getIsSortField());

        // 执行查询
        ssGoodsTypePage = ssGoodsTypeService.page(ssGoodsTypePage, queryWrapper);

        // 设置返回数据
        return R.ok().setPageResult(ssGoodsTypePage);


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
        public R add(@RequestBody @Validated({GroupAdd.class})SsGoodsType ssGoodsType) {

        try {
        ssGoodsTypeService.save(ssGoodsType);

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
        public R edit(@RequestBody  @Validated({GroupEdit.class})SsGoodsType ssGoodsType) {


        try {
            ssGoodsType.setCreateTime(null); // 置空，避免框架更新成当前时间
        ssGoodsTypeService.updateById(ssGoodsType);

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
        public R delete(@RequestBody @Validated({GroupDelete.class})SsGoodsType ssGoodsType) {

        try {

        ssGoodsTypeService.removeById(ssGoodsType.getId());

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

            ssGoodsTypeService.removeByIds(ids);

            return R.ok().setMessage("批量删除成功");
            } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("批量删除失败");
            }
            }

    /**
     * 获取商品分类字典
     */
    @GetMapping("/typeDict")
    @ResponseBody
    public R getGoodsType() {

        try {
            List<Map<Integer, String>> list = ssGoodsTypeService.getGoodsType(RC.B_NORMAL.code());
            // 设置返回数据
            return R.ok().setResult(list);
        } catch (Exception e) {

            logger.warn(e.getMessage());

            return R.err().setMessage("系统错误");
        }
    }

}
