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

import com.cmtt.base.entity.SsTagInfo;
import com.cmtt.base.service.ISsTagInfoService;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 标签信息 前端控制器
 * </p>
 *
 * @author yuzm
 * @since 2021-01-29
 */
@RestController
@RequestMapping("/api/base/ss-tag-info")
public class SsTagInfoController {

    private final Logger logger = LoggerFactory.getLogger(SsTagInfoController.class);

    @Autowired
    public ISsTagInfoService ssTagInfoService;


    /**
     * 分页获取列表
     */
    @GetMapping("/list")
    @ResponseBody
    public R list(SsTagInfo ssTagInfo) {

        try {
            String tagName = ssTagInfo.getTagName();
            ssTagInfo.setTagName(null);

            // 构建分页类
            IPage<SsTagInfo> ssTagInfoPage = new Page<>(ssTagInfo.getPageNo(), ssTagInfo.getPageSize());

            // 构造查询及排序方式
            QueryWrapper<SsTagInfo> queryWrapper = new QueryWrapper<>(ssTagInfo);
            queryWrapper.orderBy(true, ssTagInfo.getIsAsc(), ssTagInfo.getIsSortField());
            if (tagName != null && !tagName.equals("")) {
                queryWrapper.like("tag_name", tagName);
            }
            // 执行查询
            ssTagInfoPage = ssTagInfoService.page(ssTagInfoPage, queryWrapper);

            // 设置返回数据
            return R.ok().setPageResult(ssTagInfoPage);


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
    public R add(@RequestBody @Validated({GroupAdd.class}) SsTagInfo ssTagInfo) {

        try {
            ssTagInfoService.save(ssTagInfo);

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
    public R edit(@RequestBody @Validated({GroupEdit.class}) SsTagInfo ssTagInfo) {


        try {

            ssTagInfoService.updateById(ssTagInfo);

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
    public R delete(@RequestBody @Validated({GroupDelete.class}) SsTagInfo ssTagInfo) {

        try {

            ssTagInfoService.removeById(ssTagInfo.getId());

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

            ssTagInfoService.removeByIds(ids);

            return R.ok().setMessage("批量删除成功");
        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("批量删除失败");
        }
    }

    /**
     * 获取tag字典
     */
    @GetMapping("/tagDict")
    @ResponseBody
    public R getTagDict() {

        try {
            List<Map<Integer, String>> list = ssTagInfoService.getTagDict(RC.B_NORMAL.code());
            // 设置返回数据
            return R.ok().setResult(list);
        } catch (Exception e) {

            logger.warn(e.getMessage());

            return R.err().setMessage("系统错误");
        }
    }

}
