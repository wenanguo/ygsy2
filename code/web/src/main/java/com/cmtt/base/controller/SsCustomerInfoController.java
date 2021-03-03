package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.cmtt.base.config.ss.configuration.JwtAuthenticationToken;
import com.cmtt.base.controller.miniappparams.AddressInputParam;
import com.cmtt.base.controller.miniappparams.PageInputParam;
import com.cmtt.base.entity.*;
import com.cmtt.base.entity.validated.GroupAdd;
import com.cmtt.base.entity.validated.GroupDelete;
import com.cmtt.base.entity.validated.GroupEdit;
import com.cmtt.base.mapper.SqlMapper;
import com.cmtt.base.service.ISsCustomerAddressService;
import com.cmtt.base.service.ISsCustomerInfoNewService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.cmtt.base.service.ISsCustomerInfoService;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>
 * 客户信息 前端控制器
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-28
 */
@RestController
@RequestMapping("/api/base/ss-customer-info")
@Api(tags = "用户相关")
public class SsCustomerInfoController {

    private final Logger logger = LoggerFactory.getLogger(SsCustomerInfoController.class);

    @Autowired
    public ISsCustomerInfoService ssCustomerInfoService;
    @Autowired
    public ISsCustomerAddressService ssCustomerAddressService;
    @Autowired
    public ISsCustomerInfoNewService ssCustomerInfoNewService;

    /**
     * 分页获取列表
     */
    @GetMapping("/list")
    @ResponseBody
    public R list(SsCustomerInfo ssCustomerInfo) {

        try {

            //获取查询条件，然后置空，防止框架因为有值而做等值查询——而这里需要做like查询
            String customerName = ssCustomerInfo.getCustomerName();
            ssCustomerInfo.setCustomerName(null);

            String sortField = ssCustomerInfo.getIsSortField();

// 构建分页类
            IPage<SsCustomerInfo> ssCustomerInfoPage = new Page<>(ssCustomerInfo.getPageNo(), ssCustomerInfo.getPageSize());



            // 构造查询及排序方式
            QueryWrapper<SsCustomerInfo> queryWrapper = new QueryWrapper<>(ssCustomerInfo);
            if(customerName!=null && !customerName.equals("")) {
                queryWrapper.like("customer_name", customerName);
            }
            if(sortField!="ADDRESS"||!sortField.equals("TICKET")) {
                queryWrapper.orderBy(true, ssCustomerInfo.getIsAsc());
            }else{
                queryWrapper.orderBy(true, ssCustomerInfo.getIsAsc(), ssCustomerInfo.getIsSortField());
            }

            // 执行查询
            ssCustomerInfoPage = ssCustomerInfoService.page(ssCustomerInfoPage, Wrappers.<SsCustomerInfo>lambdaQuery(ssCustomerInfo)

            );

            // 设置返回数据
            return R.ok().setPageResult(ssCustomerInfoPage);


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
    @ApiOperation("新增用户")
    public R add(@RequestBody @Validated({GroupAdd.class})SsCustomerInfo ssCustomerInfo) {

        try {
            ssCustomerInfoService.save(ssCustomerInfo);
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
    @ApiOperation("修改用户")
    public R edit(@RequestBody  @Validated({GroupEdit.class})SsCustomerInfo ssCustomerInfo) {


        try {

            ssCustomerInfoService.updateById(ssCustomerInfo);

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
    @ApiOperation("删除用户")
    public R delete(@RequestBody @Validated({GroupDelete.class})SsCustomerInfo ssCustomerInfo) {

        try {
            String a = "12345";
            System.out.print("test");
            ssCustomerInfoService.removeById(ssCustomerInfo.getCustomerId());

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
    @ApiOperation("批量删除用户")
    public R batchDelete(@RequestBody List<Integer> ids) {
        try {

            ssCustomerInfoService.removeByIds(ids);

            return R.ok().setMessage("批量删除成功");
        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("批量删除失败");
        }
    }


    /**
     * 获取联合大查询（用户，订单，水票，总订单金额）,新方法
     */
    @GetMapping("/listAll")
    @ResponseBody
    @ApiOperation("根据id获取对应用户信息")
    public R listAll(SsCustomerInfoNew ssCustomerInfoNew, Principal principal, HttpServletRequest httpServletRequest) {

        try {
            //获取查询条件，然后置空，防止框架因为有值而做等值查询——而这里需要做like查询
            String customerName = ssCustomerInfoNew.getCustomerName();
            ssCustomerInfoNew.setCustomerName(null);

            String phoneType = httpServletRequest.getHeader("platform"); //判断是后端代码还是小程序
            SysUser sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal();

            // 构建分页类
            IPage<SsCustomerInfoNew> ssCustomerInfoPage = new Page<>(ssCustomerInfoNew.getPageNo(), ssCustomerInfoNew.getPageSize());

            // 构造查询及排序方式
            QueryWrapper<SsCustomerInfoNew> queryWrapper = new QueryWrapper<>(ssCustomerInfoNew);

            if("miniprogram".equals(phoneType)){//小程序
                queryWrapper.eq("customer_Id", sysUser.getWxOpenid());
            }

            if(customerName!=null && !customerName.equals("")) {
                queryWrapper.like("customerName", customerName);
            }
            if(ssCustomerInfoNew.getStatus()!=null){
                queryWrapper.eq("status",ssCustomerInfoNew.getStatus());
            }

            //   queryWrapper.eq("status",status);
            queryWrapper.orderBy(true, ssCustomerInfoNew.getIsAsc(), ssCustomerInfoNew.getIsSortField());


            // 执行查询
            // ssCustomerInfoPage = ssCustomerInfoNewService.getAllSsCustomerInfoNewList(ssCustomerInfoPage, Wrappers.<SsCustomerInfoNew>lambdaQuery(ssCustomerInfoNew));
            ssCustomerInfoPage = ssCustomerInfoNewService.getAllSsCustomerInfoNewList(ssCustomerInfoPage, queryWrapper);


            // 设置返回数据
            return R.ok().setPageResult(ssCustomerInfoPage);

        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("系统错误");
        }
    }

    /**
     * 小程序
     * 根据用户鉴权id，获取联合大查询（用户，订单，水票，总订单金额）
     */
    @GetMapping("/getSsCustomerInfoListAll.app")
    @ResponseBody
    public R getSsCustomerInfoListAll(PageInputParam params, Principal principal) {
        try {
            IPage<SsCustomerInfoNew> ssCustomerInfoNewPage = new Page<>(params.getPageNo(), params.getPageSize());
            SysUser sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal();
            // 构造查询及排序方式
            LambdaQueryWrapper<SsCustomerInfoNew> queryWrapper = Wrappers.<SsCustomerInfoNew>lambdaQuery()
                    .eq(SsCustomerInfoNew::getCustomerId,sysUser.getPhone());//当前用户

            ssCustomerInfoNewPage = ssCustomerInfoNewService.getAllSsCustomerInfoNewList(ssCustomerInfoNewPage, queryWrapper);

            return R.ok().setPageResult(ssCustomerInfoNewPage);


        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("系统错误");
        }
    }

    /**
     * 小程序
     * 新增用户
     */
    @PostMapping("/addSsCustomerInfo.app")
    @ResponseBody
    public R addSsCustomerInfo(@RequestBody @Valid SsCustomerInfo params, Principal principal) {

        try {
            SsCustomerInfo ssCustomerInfo =new SsCustomerInfo();
            BeanUtils.copyProperties(params,ssCustomerInfo);
            SysUser sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal();
            ssCustomerInfo.setCustomerId(sysUser.getId()+"");
            ssCustomerInfo.setOpenid(sysUser.getId()+"");
            ssCustomerInfo.setCustomerName(sysUser.getUsername());
            ssCustomerInfo.setPhone(sysUser.getPhone());
            ssCustomerInfo.setSex(sysUser.getSex().toString());
            ssCustomerInfo.setCreateTime(LocalDateTime.now());
            ssCustomerInfo.setUpdateTime(LocalDateTime.now());
            ssCustomerInfo.setStatus(100);
            ssCustomerInfoService.save(ssCustomerInfo);
            return R.ok().setMessage("新增成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return R.err().setMessage("新增失败");
        }
    }

    /**
     * 小程序
     * 修改用户
     */
    @PostMapping("/updateSsCustomerInfo.app")
    @ResponseBody
    public R updateSsCustomerInfo(@RequestBody @Valid SsCustomerInfo params, Principal principal) {

        try {
            if(params.getId()==0){
                return R.err().setMessage("修改失败，地址ID为0");
            }
            SysUser sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal();

            SsCustomerInfo ssCustomerInfo =new SsCustomerInfo();
            BeanUtils.copyProperties(params,ssCustomerInfo);

            ssCustomerInfo.setCustomerId(sysUser.getId()+"");
            ssCustomerInfo.setOpenid(sysUser.getId()+"");
            ssCustomerInfo.setCustomerName(sysUser.getUsername());
            ssCustomerInfo.setPhone(sysUser.getPhone());
            ssCustomerInfo.setSex(sysUser.getSex().toString());
            ssCustomerInfo.setCreateTime(LocalDateTime.now());
            ssCustomerInfo.setUpdateTime(LocalDateTime.now());
            ssCustomerInfo.setStatus(100);

            ssCustomerInfoService.updateById(ssCustomerInfo);
            return R.ok().setMessage("更新成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return R.err().setMessage("更新失败");
        }
    }
}
