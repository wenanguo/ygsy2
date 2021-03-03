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

import com.cmtt.base.entity.SsStationBank;
import com.cmtt.base.service.ISsStationBankService;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 水站绑定的银行卡信息 前端控制器
 * </p>
 *
 * @author yuzm
 * @since 2021-01-31
 */
@RestController
@RequestMapping("/api/base/ss-station-bank")
public class SsStationBankController {

    private final Logger logger = LoggerFactory.getLogger(SsStationBankController.class);

    @Autowired
    public ISsStationBankService ssStationBankService;


    /**
     * 分页获取列表
     */
    @GetMapping("/list")
    @ResponseBody
    public R list(SsStationBank ssStationBank) {
        try {
            // 构建分页类
            IPage<SsStationBank> ssStationBankPage = new Page<>(ssStationBank.getPageNo(), ssStationBank.getPageSize());

            // 构造查询及排序方式
            QueryWrapper<SsStationBank> queryWrapper = new QueryWrapper<>(ssStationBank);
            queryWrapper.orderBy(true, ssStationBank.getIsAsc(), ssStationBank.getIsSortField());

            // 执行查询
            //ssStationBankPage = ssStationBankService.page(ssStationBankPage, queryWrapper);
            ssStationBankPage = ssStationBankService.getStationBankPage(ssStationBankPage, queryWrapper);

            // 设置返回数据
            return R.ok().setPageResult(ssStationBankPage);
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
    public R add(@RequestBody @Validated({GroupAdd.class}) SsStationBank ssStationBank) {
        try {
            ssStationBankService.save(ssStationBank);
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
    public R edit(@RequestBody @Validated({GroupEdit.class}) SsStationBank ssStationBank) {
        try {
            ssStationBankService.updateById(ssStationBank);
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
    public R delete(@RequestBody @Validated({GroupDelete.class}) SsStationBank ssStationBank) {
        try {
            ssStationBankService.removeById(ssStationBank.getId());
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
            ssStationBankService.removeByIds(ids);
            return R.ok().setMessage("批量删除成功");
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return R.err().setMessage("批量删除失败");
        }
    }

    /**
     * 获取开户行列表
     */
    @GetMapping("/bankList")
    @ResponseBody
    public R getBankList() {
        try {
            List<Map<Integer, String>> list = ssStationBankService.getBankList();
            // 设置返回数据
            return R.ok().setResult(list);
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return R.err().setMessage("系统错误");
        }
    }

}
