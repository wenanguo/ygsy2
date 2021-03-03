package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.cmtt.base.config.ss.configuration.JwtAuthenticationToken;
import com.cmtt.base.controller.miniappparams.SysUserInputParam;
import com.cmtt.base.controller.miniappparams.DeliverymanPageInputParam;
import com.cmtt.base.entity.R;
import com.cmtt.base.entity.SysUser;
import com.cmtt.base.entity.validated.GroupAdd;
import com.cmtt.base.entity.validated.GroupDelete;
import com.cmtt.base.entity.validated.GroupEdit;
import com.cmtt.base.service.ISysUserService;
import com.cmtt.base.utils.RC;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import com.cmtt.base.service.ISsDeliveryManService;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * 配送员信息 前端控制器
 * </p>
 *
 * @author yuzm
 * @since 2021-01-31
 */
@RestController
@RequestMapping("/api/base/ss-delivery-man")
@Api(tags = "配送员相关")
public class SsDeliveryManController {

    private final Logger logger = LoggerFactory.getLogger(SsDeliveryManController.class);

    @Autowired
    public ISsDeliveryManService ssDeliveryManService;

    @Autowired
    public ISysUserService sysUserService;


    /**
     * 分页获取列表
     */
    @GetMapping("/list")
    @ResponseBody
    public R list(SysUser sysUser) {
        try {
            // 构建分页类
            IPage<SysUser> userPage = new Page<>(sysUser.getPageNo(), sysUser.getPageSize());

            // 构造查询及排序方式
            QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>(sysUser);
            queryWrapper.orderBy(true, sysUser.getIsAsc(), sysUser.getIsSortField());
            queryWrapper.isNotNull("station_id");//归属了水站的所有配送员


            // 执行查询
            userPage = sysUserService.page(userPage, queryWrapper);

            // 设置返回数据
            return R.ok().setPageResult(userPage);

        } catch (Exception e) {
            logger.warn(e.getMessage());
            return R.err().setMessage("系统错误");
        }
    }

    /**
     * 分页获取列表
     */
    @PostMapping("/manlist.app")
    @ResponseBody
    @ApiOperation("分页获取配送员列表")
    public R appList(@RequestBody @Valid DeliverymanPageInputParam param, Principal principal) {
        try {
            // 构建分页类
            IPage<SysUser> userPage = new Page<>(param.getPageNo(), param.getPageSize());

            Integer stationId = -1;
            if(null != principal){
                SysUser sysUser = (SysUser) ((JwtAuthenticationToken) principal).getPrincipal();
                if(null != sysUser.getStationId()) {
                    stationId = sysUser.getStationId();
                }else{
                    return R.err().setMessage("水站获取失败");
                }
            }

            // 构造查询及排序方式
            LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.<SysUser>lambdaQuery()
                    .orderByAsc(SysUser::getId)//序号升序
                    .eq(SysUser::getStationId, stationId)//查询指定水站下的配送人员
                    .eq(SysUser::getStatus, RC.B_NORMAL.code());//正常状态

            // 执行查询
            userPage = sysUserService.page(userPage, queryWrapper);

            // 设置返回数据
            return R.ok().setPageResult(userPage);

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
    @ApiOperation("新增配送员信息")
    public R add(@RequestBody @Validated({GroupAdd.class}) SysUserInputParam param, Principal principal) {

        try {
            //组装配送员用户信息
            SysUser user = new SysUser();

            /*
            if(null!=param.getPhone() && param.getPhone().length()==11){
                if(ssDeliveryManService.existsPhone(param.getPhone())){
                    return R.err().setMessage("已存在相同手机号配送员");
                }
            }else{
                return R.err().setMessage("配送员手机号不能为空或位数不对");
            }*/
            if(null!=param.getPhone() && !param.getPhone().equals("")){
                if(ssDeliveryManService.existsUsername(param.getPhone())){//手机号设置为用户名时，判断用户名是否已存在
                    return R.err().setMessage("手机号同步设置为用户名，但已存在相同用户名的配送员");
                }
            }

            BeanUtils.copyProperties(param, user);
            user.setUsername(param.getPhone());//用户手机号做登录账户
            user.setCreateTime(LocalDateTime.now());

            if(null != principal){
                SysUser sysUser = (SysUser) ((JwtAuthenticationToken) principal).getPrincipal();
                if(null != sysUser.getStationId()) {
                    user.setStationId(sysUser.getStationId());
                }else{
                    return R.err().setMessage("水站获取失败");
                }
            }
            user.setTtype(3);//设置为水站配送员
            sysUserService.save(user);

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
    @ApiOperation("修改配送员信息")
    public R edit(@RequestBody @Validated({GroupEdit.class}) SysUserInputParam param) {
        try {
            SysUser user = new SysUser();

            if(null!=param.getPhone() && !param.getPhone().equals("")){
                if(ssDeliveryManService.existsUsername(param.getPhone())){//手机号设置为用户名时，判断用户名是否已存在
                    return R.err().setMessage("手机号同步设置为用户名，但已存在相同用户名的配送员");
                }
            }

            BeanUtils.copyProperties(param, user);
            user.setUsername(param.getPhone());//手机号做登录账户
            user.setUpdateTime(LocalDateTime.now());
            //ssDeliveryManService.updateDeliveryUser(param);

            sysUserService.updateById(user);

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
    @ApiOperation("通过用户ID删除配送员")
    public R delete(@RequestBody @Validated({GroupDelete.class}) SysUserInputParam param) {
        try {
            SysUser user = new SysUser();
            BeanUtils.copyProperties(param, user);
            //ssDeliveryManService.deleteDeliveryUser(param);

            sysUserService.removeById(user.getId());
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

            ssDeliveryManService.removeByIds(ids);

            return R.ok().setMessage("批量删除成功");
        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("批量删除失败");
        }
    }


}
