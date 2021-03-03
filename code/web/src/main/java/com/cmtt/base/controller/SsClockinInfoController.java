package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.cmtt.base.config.ss.configuration.JwtAuthenticationToken;
import com.cmtt.base.controller.miniappparams.ClockinInputParam;
import com.cmtt.base.entity.R;
import com.cmtt.base.entity.SysUser;
import com.cmtt.base.entity.validated.GroupDelete;
import com.cmtt.base.entity.validated.GroupEdit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.cmtt.base.entity.SsClockinInfo;
import com.cmtt.base.service.ISsClockinInfoService;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yuzm
 * @since 2021-02-20
 */
@RestController
@RequestMapping("/api/base/ss-clockin-info")
@Api(tags = "用户打卡相关")
public class SsClockinInfoController {

    private final Logger logger = LoggerFactory.getLogger(SsClockinInfoController.class);

    @Autowired
    public ISsClockinInfoService ssClockinInfoService;


    /**
     * 分页获取列表
     */
    @GetMapping("/list")
    @ResponseBody
    public R list(SsClockinInfo ssClockinInfo) {
        try {
            // 构建分页类
            IPage<SsClockinInfo> ssClockinInfoPage = new Page<>(ssClockinInfo.getPageNo(), ssClockinInfo.getPageSize());

            // 构造查询及排序方式
            QueryWrapper<SsClockinInfo> queryWrapper = new QueryWrapper<>(ssClockinInfo);
            queryWrapper.orderBy(true, ssClockinInfo.getIsAsc(), ssClockinInfo.getIsSortField());

            // 执行查询
            ssClockinInfoPage = ssClockinInfoService.page(ssClockinInfoPage, queryWrapper);

            // 设置返回数据
            return R.ok().setPageResult(ssClockinInfoPage);
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return R.err().setMessage("系统错误");
        }
    }

    /**
     * 分页获取列表
     */
    @PostMapping("/list.app")
    @ResponseBody
    @ApiOperation("获取用户月度打卡记录")
    public R list(@RequestBody @Valid  ClockinInputParam params, Principal principal) {
        try {
            SysUser sysUser = null;
            if(null != principal){
                sysUser = (SysUser) ((JwtAuthenticationToken) principal).getPrincipal();
            }else{
                return R.err().setMessage("无法获取用户信息");
            }
            if(null == params.getMonth()){  //无传入月份时，默认取当月
                params.setMonth(DateTimeFormatter.ofPattern("yyyyMM").format(LocalDateTime.now()));
            }

            // 执行查询
            List<SsClockinInfo> list= ssClockinInfoService.getClockinList(sysUser.getId(), params.getMonth());

            // 设置返回数据
            return R.ok().setResult(list);
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return R.err().setMessage("系统错误");
        }
    }


    /**
     * 新增
     */
    @PostMapping("/add.app")
    @ResponseBody
    @ApiOperation("新增用户打卡记录")
    public R add(@RequestBody @Valid ClockinInputParam params, Principal principal) {

        try {
            SysUser sysUser = null;
            if(null != principal){
                sysUser = (SysUser) ((JwtAuthenticationToken) principal).getPrincipal();
            }else{
                return R.err().setMessage("无法获取用户信息");
            }
            SsClockinInfo ssClockinInfo = new SsClockinInfo();
            ssClockinInfo.setUserId(sysUser.getId());
            boolean result = false;
            if(params.getFlag() == 1){ //上班打卡，新增打卡记录
                ssClockinInfo.setClockinDate(LocalDate.now());
                ssClockinInfo.setInTime(LocalTime.now());
                result = ssClockinInfoService.save(ssClockinInfo);
            }else if(params.getFlag() == 2){ //下班打卡，更新当日打卡的下班记录
                ssClockinInfo.setClockinDate(LocalDate.now());
                ssClockinInfo.setOutTime(LocalTime.now());
                result = ssClockinInfoService.updateOutTime(LocalTime.now(), sysUser.getId(), LocalDate.now())>0 ? true : false ;
            }

            if(result){
                return R.ok().setMessage("打卡成功");
            }else{
                return R.ok().setMessage("打卡失败");
            }

        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("打卡失败");
        }


    }


    /**
     * 修改
     */
    @PutMapping("/edit")
    @ResponseBody
    public R edit(@RequestBody @Validated({GroupEdit.class}) SsClockinInfo ssClockinInfo) {


        try {

            ssClockinInfoService.updateById(ssClockinInfo);

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
    public R delete(@RequestBody @Validated({GroupDelete.class}) SsClockinInfo ssClockinInfo) {

        try {

            ssClockinInfoService.removeById(ssClockinInfo.getId());

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

            ssClockinInfoService.removeByIds(ids);

            return R.ok().setMessage("批量删除成功");
        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("批量删除失败");
        }
    }


}
