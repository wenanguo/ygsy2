package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmtt.base.config.ss.configuration.JwtAuthenticationToken;
import com.cmtt.base.controller.miniappparams.AddressInputParam;
import com.cmtt.base.controller.miniappparams.PageInputParam;
import com.cmtt.base.entity.*;
import com.cmtt.base.entity.validated.GroupAdd;
import com.cmtt.base.entity.validated.GroupDelete;
import com.cmtt.base.entity.validated.GroupEdit;
import com.cmtt.base.service.ISsCustomerAddressService;
import com.cmtt.base.service.ISsCustomerInfoService;
import com.cmtt.base.utils.BaiduMapUtil;
import com.cmtt.base.utils.RC;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 客户地址信息 前端控制器
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-29
 */
@RestController
@RequestMapping("/api/base/ss-customer-address")
@Api(tags = "用户相关")
public class SsCustomerAddressController {

    private final Logger logger = LoggerFactory.getLogger(SsCustomerAddressController.class);

    @Autowired
    public ISsCustomerAddressService ssCustomerAddressService;
    @Autowired
    public ISsCustomerInfoService ssCustomerInfoService;

    //获取配置文件中的用户地址可以匹配的最远水站距离
    private String farthestDistance;
    @Value("${address.farthest.distance}")
    public void setFarthestDistance(String farthestDistance) {
        this.farthestDistance = farthestDistance;
    }


    /**
     * 获取用户地址关联查询
     * @param id
     * @return
     */
    @RequestMapping("selectListPage")
    @ResponseBody
    public  List<Map<String,Object>>   selectListPage(String id) {
        Page<Map<String, Object>> page = ssCustomerAddressService.selectListPage(1, 2,id);



        //  this.setResult(map);


        return page.getRecords();
    }



    /**
     * 分页获取列表
     */
    @GetMapping("/list")
    @ResponseBody
    @ApiOperation("用户地址表基本信息")
    public R list(SsCustomerAddress ssCustomerAddress, String customerId) {

        try {
            //获取查询条件，然后置空，防止框架因为有值而做等值查询——而这里需要做like查询
            String contacts = ssCustomerAddress.getContacts();
            ssCustomerAddress.setContacts(null);

// 构建分页类
            IPage<SsCustomerAddress> ssCustomerAddressPage = new Page<>(ssCustomerAddress.getPageNo(), ssCustomerAddress.getPageSize());



            // 构造查询及排序方式
            QueryWrapper<SsCustomerAddress> queryWrapper = new QueryWrapper<>(ssCustomerAddress);
            if(customerId==null || "".equals(customerId)){
                queryWrapper.eq("customer_id",null);

            }
            if(customerId!=null&&!"".equals(customerId)){
                queryWrapper.apply("status={0}",RC.B_NORMAL.code())
                        .inSql("customer_id","select customer_id from ss_customer_info where customer_id ="+customerId);

                if(contacts!=null && !contacts.equals("")) {
                    queryWrapper.like("contacts", contacts);
                }
                queryWrapper.orderBy(true, ssCustomerAddress.getIsAsc(), ssCustomerAddress.getIsSortField());

                // 执行查询
                ssCustomerAddressPage = ssCustomerAddressService.page(ssCustomerAddressPage, queryWrapper);
            }
            // 设置返回数据
            return R.ok().setPageResult(ssCustomerAddressPage);


        } catch (Exception e) {

            logger.warn(e.getMessage());

            return R.err().setMessage("系统错误");
        }
    }


    /**
     * 新增地址和用户关联
     */
    @PostMapping("/addCustomerAndAddress")
    @ResponseBody
    @ApiOperation("用户水票表关联新增")
    public R addCustomerAndAddress(@RequestBody @Validated({GroupAdd.class})SsCustomerAddress ssCustomerAddress,@Validated({GroupAdd.class})SsCustomerInfo ssCustomerInfo) {

        try {
            String customerId = ssCustomerInfo.getCustomerId(); //获取前端传入过来的CustomerId
            ssCustomerAddress.setCustomerId(customerId);//将CustomerId传入用户新增地址中
            ssCustomerAddressService.save(ssCustomerAddress);

            return R.ok().setMessage("新增成功");

        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("新增失败");
        }


    }

    /**
     * 修改地址和用户关联
     */
    @PutMapping("/editCustomerAndAddress")
    @ResponseBody
    @ApiOperation("用户水票表关联修改")
    public R editCustomerAndAddress(@RequestBody  @Validated({GroupEdit.class})SsCustomerAddress ssCustomerAddress,@Validated({GroupAdd.class})SsCustomerInfo ssCustomerInfo) {


        try {
            String customerId = ssCustomerInfo.getCustomerId(); //获取前端传入过来的CustomerId
            ssCustomerAddress.setCustomerId(customerId+"");//将CustomerId传入用户修改地址中
            ssCustomerAddressService.updateById(ssCustomerAddress);

            return R.ok().setMessage("修改成功");

        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("修改失败");
        }
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    @ResponseBody
    @ApiOperation("用户关联地址表新增")
    public R add(@RequestBody @Validated({GroupAdd.class})SsCustomerAddress ssCustomerAddress,@Validated({GroupAdd.class})SsCustomerInfo ssCustomerInfo) {

        try {
            String customerId = ssCustomerInfo.getCustomerId(); //获取前端传入过来的CustomerId
            ssCustomerAddress.setCustomerId(customerId);//将CustomerId传入用户修改地址中

            ssCustomerAddressService.save(ssCustomerAddress);

            return R.ok().setMessage("新增成功");

        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("新增失败");
        }
    }
    /**
     * 小程序获取当前用户地址列表
     */
    @PostMapping("/getAddressList.app")
    @ResponseBody
    @ApiOperation("获取用户地址列表")
    public R getAddressList(@RequestBody @Valid PageInputParam params,Principal principal) {

        try {
            IPage<SsCustomerAddress> ssCustomerAddressPage = new Page<>(params.getPageNo(), params.getPageSize());
            SysUser sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal();
            if(sysUser==null){
                return R.err();
            }
            // 构造查询及排序方式
            LambdaQueryWrapper<SsCustomerAddress> queryWrapper = Wrappers.<SsCustomerAddress>lambdaQuery()
                    .eq(SsCustomerAddress::getStatus, RC.B_NORMAL.code())//状态是启用
                    .eq(SsCustomerAddress::getCustomerId,sysUser.getId());//当前用户
            return R.ok().setResult(ssCustomerAddressService.page(ssCustomerAddressPage,queryWrapper));

        } catch (Exception e) {
            logger.error(e.getMessage());
            return R.err().setMessage("查询失败");
        }
    }
    /**
     * 小程序新增地址
     */
    @PostMapping("/addAddress.app")
    @ResponseBody
    @ApiOperation("新增地址")
    public R addAddress(@RequestBody @Valid AddressInputParam params, Principal principal) {

        try {
            SsCustomerAddress address =new SsCustomerAddress();
            BeanUtils.copyProperties(params,address);
            SysUser sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal();
            if(sysUser==null){
                return R.err();
            }
            address.setCustomerId(sysUser.getId()+"");
            address.setCreateBy(sysUser.getId()+"");
            address.setCreateTime(LocalDateTime.now());
            address.setStatus(RC.B_NORMAL.code());

            //把微信经纬度转成百度经纬度
            BaiduMapUtil mapUtil = new BaiduMapUtil();
            BigDecimal[] lngAndLat = mapUtil.change2BaiduLngAndLat(address.getLongitude(), address.getLatitude());
            address.setLongitude(lngAndLat[0]); //重置转换后的经度
            address.setLatitude(lngAndLat[1]);  //重置转换后的维度

            //根据用户地址的经纬度获取最近的水站
            Map<String, Object> station = ssCustomerAddressService.getNearestStation(
                    address.getLongitude(),  //经度
                    address.getLatitude(),   //维度
                    RC.B_NORMAL.code());     //水站状态为正常
            Double distance = Double.valueOf(station.get("dist").toString());
            Double furDistance = Double.valueOf(farthestDistance); //公里
            boolean distanceFlag = false;
            //最近水站的距离和设定的最远距离对比
            if((distance/1000) < furDistance){
                //最近的水站在最远距离之内
                Integer stationId = Integer.parseInt(station.get("id").toString());
                address.setStationId(stationId); //为用户地址分配水站
                distanceFlag = true;
            }else{
                address.setStatus(RC.B_INVALID.code());//超出距离，设置超出状态
            }

            ssCustomerAddressService.save(address);
            if(distanceFlag){
                return R.ok().setMessage("新增成功");
            }else{
                //最近的水站超出最远距离
                return R.ok().setMessage("地址超出最近水站"+String.format("%.1f", (distance/1000))+"公里，将无法配送");
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            return R.err().setMessage("新增失败");
        }
    }
    /**
     * 小程序修改地址
     */
    @PostMapping("/editAddress.app")
    @ResponseBody
    @ApiOperation("修改地址")
    public R editAddress(@RequestBody @Valid AddressInputParam params, Principal principal) {

        try {
            if(params.getId()==0){
                return R.err().setMessage("修改失败，地址ID为0");
            }
            SysUser sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal();

            if(sysUser==null){
                return R.err();
            }

            if(params.getIsdefault().equals("2")){//如果需要将当前地址设置为默认地址,则需要将之前的地址给设置成非默认
                LambdaQueryWrapper<SsCustomerAddress> queryWrapper = Wrappers.<SsCustomerAddress>lambdaQuery()
                        .eq(SsCustomerAddress::getStatus, RC.B_NORMAL.code())//状态是启用
                        .eq(SsCustomerAddress::getCustomerId,sysUser.getId())//当前用户
                        .eq(SsCustomerAddress::getIsdefault, 2);//状态是默认
                SsCustomerAddress addressOldDefault = ssCustomerAddressService.getOne(queryWrapper);
                if(addressOldDefault!=null)
                {
                    addressOldDefault.setIsdefault("1");//设置非默认
                    ssCustomerAddressService.updateById(addressOldDefault);
                }
            }
            SsCustomerAddress address =new SsCustomerAddress();
            BeanUtils.copyProperties(params,address);
            address.setCustomerId(sysUser.getId()+"");
            address.setLastUpdateBy(sysUser.getId()+"");
            address.setLastUpdateTime(LocalDateTime.now());
            ssCustomerAddressService.updateById(address);
            return R.ok().setMessage("更新成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return R.err().setMessage("更新失败");
        }
    }
    /**
     * 小程序获取当前用户默认地址
     */
    @PostMapping("/getDefaultAdd.app")
    @ResponseBody
    @ApiOperation("获取默认地址")
    public R getDefaultAdd(Principal principal) {

        try {
            SysUser sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal();
            if(sysUser==null){
                return R.err();
            }
            // 构造查询及排序方式
            LambdaQueryWrapper<SsCustomerAddress> queryWrapper = Wrappers.<SsCustomerAddress>lambdaQuery()
                    .eq(SsCustomerAddress::getStatus, RC.B_NORMAL.code())//状态是启用
                    .eq(SsCustomerAddress::getCustomerId,sysUser.getId())//当前用户
                    .eq(SsCustomerAddress::getIsdefault, 2);//状态是默认
            return R.ok().setResult(ssCustomerAddressService.getOne(queryWrapper));

        } catch (Exception e) {
            logger.error(e.getMessage());
            return R.err().setMessage("查询失败");
        }
    }
    /**
     * 修改
     */
    @PutMapping("/edit")
    @ResponseBody
    @ApiOperation("用户地址表修改")
    public R edit(@RequestBody  @Validated({GroupEdit.class})SsCustomerAddress ssCustomerAddress) {


        try {

            ssCustomerAddressService.updateById(ssCustomerAddress);

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
    @ApiOperation("用户地址表删除")
    public R delete(@RequestBody @Validated({GroupDelete.class})SsCustomerAddress ssCustomerAddress) {

        try {

            ssCustomerAddressService.removeById(ssCustomerAddress.getId());

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
    @ApiOperation("用户地址表批量")
    public R batchDelete(@RequestBody List<Integer> ids) {
        try {

            ssCustomerAddressService.removeByIds(ids);

            return R.ok().setMessage("批量删除成功");
        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("批量删除失败");
        }
    }

    /**
     * 修改用户地址归属水站
     */
    @PutMapping("/updateStation")
    @ResponseBody
    @ApiOperation("修改用户地址归属水站")
    public R udpateStation(@RequestBody  @Validated({GroupEdit.class})SsCustomerInfoNew ssCustomerInfoNew) {
        try {
            ssCustomerAddressService.updateStation(ssCustomerInfoNew.getStationId(), ssCustomerInfoNew.getAddressId());
            return R.ok().setMessage("修改成功");
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return R.err().setMessage("修改失败");
        }
    }
}
