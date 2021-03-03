package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.cmtt.base.config.ss.configuration.JwtAuthenticationToken;
import com.cmtt.base.controller.miniappparams.WaterstationOrdersInputParam;
import com.cmtt.base.entity.*;
import com.cmtt.base.entity.validated.GroupAdd;
import com.cmtt.base.entity.validated.GroupDelete;
import com.cmtt.base.entity.validated.GroupEdit;
import com.cmtt.base.utils.CommonUtils;
import com.cmtt.base.utils.RC;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.cmtt.base.service.ISsWaterstationOrderInfoService;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单信息 前端控制器
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-02-21
 */
@RestController
@RequestMapping("/api/base/ss-waterstation-order-info")
public class SsWaterstationOrderInfoController {

private final Logger logger = LoggerFactory.getLogger(SsWaterstationOrderInfoController.class);

@Autowired
public ISsWaterstationOrderInfoService ssWaterstationOrderInfoService;


/**
* 分页获取列表
*/
//@GetMapping("/list")
//@ResponseBody
public R list(SsWaterstationOrderInfo ssWaterstationOrderInfo) {

try {

// 构建分页类
IPage<SsWaterstationOrderInfo> ssWaterstationOrderInfoPage = new Page<>(ssWaterstationOrderInfo.getPageNo(), ssWaterstationOrderInfo.getPageSize());

    // 构造查询及排序方式
    QueryWrapper<SsWaterstationOrderInfo> queryWrapper = new QueryWrapper<>(ssWaterstationOrderInfo);
        queryWrapper.orderBy(true, ssWaterstationOrderInfo.getIsAsc(), ssWaterstationOrderInfo.getIsSortField());

        // 执行查询
        ssWaterstationOrderInfoPage = ssWaterstationOrderInfoService.page(ssWaterstationOrderInfoPage, queryWrapper);

        // 设置返回数据
        return R.ok().setPageResult(ssWaterstationOrderInfoPage);


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
        public R add(@RequestBody @Validated({GroupAdd.class})SsWaterstationOrderInfo ssWaterstationOrderInfo) {

        try {
        ssWaterstationOrderInfoService.save(ssWaterstationOrderInfo);

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
        public R edit(@RequestBody  @Validated({GroupEdit.class})SsWaterstationOrderInfo ssWaterstationOrderInfo) {


        try {

        ssWaterstationOrderInfoService.updateById(ssWaterstationOrderInfo);

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
        public R delete(@RequestBody @Validated({GroupDelete.class})SsWaterstationOrderInfo ssWaterstationOrderInfo) {

        try {

        ssWaterstationOrderInfoService.removeById(ssWaterstationOrderInfo.getId());

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

            ssWaterstationOrderInfoService.removeByIds(ids);

            return R.ok().setMessage("批量删除成功");
            } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("批量删除失败");
            }
        }



    @PostMapping("/addOrder")
    @ResponseBody
    @ApiOperation("水站端订单批量预下单")
    public R addWaterstationOrderInfo(@RequestBody @Validated({GroupAdd.class})WaterstationOrdersInputParam param, Principal principal) {

        try {

            SysUser sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal(); //用户相关

            if(sysUser==null){
                return R.err();
            }

          //  String orderNum = String.valueOf(System.currentTimeMillis()/ 1000000); //订单编号
            String orderNum = CommonUtils.getDateNo();
            String  openID = sysUser.getWxOpenid();
            String  phone = sysUser.getPhone();
            Integer stationId = sysUser.getStationId();

            String payWay = param.getPayWay();

            LocalDateTime createTime = LocalDateTime.now(); //创建时间

            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


            SsWaterstationOrderInfo ssWaterstationOrderInfo =new SsWaterstationOrderInfo();
            ssWaterstationOrderInfo.setPayWay(payWay);
            ssWaterstationOrderInfo.setOrderNumber(orderNum);
        //    if("2".equals(payWay)){//如果为信息支付，那么就是未支付状态，返回订单号
                ssWaterstationOrderInfo.setOrderStatus(1);
                ssWaterstationOrderInfo.setPayStatus(RC.PAY_NO.code()); //未支付状态
        //    }

//            if("1".equals(payWay)){//如果为月结支付
//                ssWaterstationOrderInfo.setOrderStatus(1);
//                ssWaterstationOrderInfo.setPayStatus(RC.PAY_NO.code()); //未支付状态
//            }

            ssWaterstationOrderInfo.setCustomerId(phone);
            ssWaterstationOrderInfo.setOrderDate(createTime);
            ssWaterstationOrderInfo.setDescription(param.getDescription());
            ssWaterstationOrderInfo.setStationId(stationId);

            List<SsWaterstationOrderDetail> listSsWaterstationOrderDetail = new ArrayList<>();

            if(param.getAppointTime()!=null&&!"".equals(param.getAppointTime())){
                ssWaterstationOrderInfo.setAppointTime(LocalDateTime.parse(param.getAppointTime(), df));
            }


           for(int i=0;i<param.getSsWaterstationOrderDetail().size();i++){

               SsWaterstationOrderDetail ssWaterstationOrderInfoDetail =new SsWaterstationOrderDetail();

               BeanUtils.copyProperties(param,ssWaterstationOrderInfoDetail);

               ssWaterstationOrderInfoDetail.setGoodsId(param.getSsWaterstationOrderDetail().get(i).getGoodsId());
               ssWaterstationOrderInfoDetail.setNum(param.getSsWaterstationOrderDetail().get(i).getNum());
               ssWaterstationOrderInfoDetail.setOrderNumber(orderNum);

               listSsWaterstationOrderDetail.add(ssWaterstationOrderInfoDetail);
           }

            ssWaterstationOrderInfo.setSsWaterstationOrderDetail(listSsWaterstationOrderDetail);

            SsWaterstationOrderInfo ssWaterstationOrderInfoNew = ssWaterstationOrderInfoService.saveAllWaterstationOrderInfo(ssWaterstationOrderInfo);

            if(ssWaterstationOrderInfoNew==null){
                return R.err().setMessage("新增失败");
            }

//         double totalPrice =    ssWaterstationOrderInfoNew.getTotalPrice();  //总额
//         List list =    ssWaterstationOrderInfoNew.getSsWaterstationOrderDetail();//支付商品信息
//           String orderNum =  ssWaterstationOrderInfoNew.getOrderNumber();

         //   return R.ok().setResult(ssWaterstationOrderInfoNew);
            return R.ok();

        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("新增失败");
        }
    }

    @GetMapping("/listAllDetail")
    @ResponseBody
    @ApiOperation("水站端订单明细查询")
    public R listAll(WaterstationOrdersInputParam param, Principal principal, HttpServletRequest httpServletRequest,String customerId) {

        try {
            SsWaterstationOrderInfo ssWaterstationOrderInfo =new SsWaterstationOrderInfo();

//            List<SsOrderDetail> ssOrderDetails = new ArrayList<SsOrderDetail>();

            BeanUtils.copyProperties(param,ssWaterstationOrderInfo);

            String phoneType = httpServletRequest.getHeader("platform"); //判断是后端代码还是小程序
            SysUser sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal();

            if(sysUser==null){
                return R.err();
            }

            // 构建分页类
            IPage<SsWaterstationOrderInfo> ssWaterstationOrderInfoPage = new Page<>(ssWaterstationOrderInfo.getPageNo(), ssWaterstationOrderInfo.getPageSize());

            // 构造查询及排序方式
            QueryWrapper<SsWaterstationOrderInfo> queryWrapper = new QueryWrapper<>(ssWaterstationOrderInfo);

           // String openID = sysUser.getWxOpenid();
            Integer id = sysUser.getId();
            String orderNumber = ssWaterstationOrderInfo.getOrderNumber();
            Integer payStatus = ssWaterstationOrderInfo.getPayStatus();
            Integer orderStatus = ssWaterstationOrderInfo.getOrderStatus();

            if("miniprogram".equals(phoneType)) {//小程序

                queryWrapper.eq("customer_id", id);
            }else{
                queryWrapper.eq("customer_id", customerId);
            }

            if(!"".equals(orderNumber)&&orderNumber!=null){
                queryWrapper.eq("order_number1", orderNumber);
            }

            if(payStatus!=null&&!"".equals(payStatus)){
                queryWrapper.eq("pay_status", payStatus);
            }

            if(orderStatus!=null&&!"".equals(orderStatus)){
                queryWrapper.eq("order_status", orderStatus);
            }

            queryWrapper.orderBy(true, ssWaterstationOrderInfo.getIsAsc(), ssWaterstationOrderInfo.getIsSortField());

            // 执行查询
            ssWaterstationOrderInfoPage = ssWaterstationOrderInfoService.getAllSsWaterstationOrderInfoList(ssWaterstationOrderInfoPage, queryWrapper);

            // 设置返回数据
            return R.ok().setPageResult(ssWaterstationOrderInfoPage);
 //           System.out.println("User:" + data.getGoodsId() + "," + data.getNum()+","+data.getTotalCount());
        } catch (Exception e) {

            logger.warn(e.getMessage());

            return R.err().setMessage("系统错误");
        }
    }

    @GetMapping("/list")
    @ResponseBody
    @ApiOperation("水站端订单基本信息查询")
    public R getSsWaterstationOrderInfoList(WaterstationOrdersInputParam param, Principal principal, HttpServletRequest httpServletRequest) {

        try {
            SsWaterstationOrderInfo ssWaterstationOrderInfo =new SsWaterstationOrderInfo();

//            List<SsOrderDetail> ssOrderDetails = new ArrayList<SsOrderDetail>();

            BeanUtils.copyProperties(param,ssWaterstationOrderInfo);

            String phoneType = httpServletRequest.getHeader("platform"); //判断是后端代码还是小程序
            SysUser sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal();

            if(sysUser==null){
                return R.err();
            }

            // 构建分页类
            IPage<SsWaterstationOrderInfo> ssWaterstationOrderInfoPage = new Page<>(ssWaterstationOrderInfo.getPageNo(), ssWaterstationOrderInfo.getPageSize());

            // 构造查询及排序方式
            QueryWrapper<SsWaterstationOrderInfo> queryWrapper = new QueryWrapper<>(ssWaterstationOrderInfo);

           // String openID = sysUser.getWxOpenid();
            Integer id = sysUser.getId();
            Integer stationId = sysUser.getStationId();
            String orderNum = ssWaterstationOrderInfo.getOrderNumber();
            Integer payStatus = ssWaterstationOrderInfo.getPayStatus();
            Integer orderStatus = ssWaterstationOrderInfo.getOrderStatus();

            if("miniprogram".equals(phoneType)) {//小程序

                queryWrapper.eq("customer_id", id);
            }

            if(!"".equals(orderNum)&&orderNum!=null){
                queryWrapper.eq("order_number1", orderNum);
            }

            if(payStatus!=null&&!"".equals(payStatus)){
                queryWrapper.eq("pay_status", payStatus);
            }

            if(orderStatus!=null&&!"".equals(orderStatus)){
                queryWrapper.eq("order_status", orderStatus);
            }

            if(stationId!=null&&!"".equals(stationId)){
                queryWrapper.eq("station_id", stationId);
            }


            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            if(ssWaterstationOrderInfo.getStartTime()!=null&&!"".equals(ssWaterstationOrderInfo.getStartTime())){
                queryWrapper.gt("order_date", LocalDateTime.parse(ssWaterstationOrderInfo.getStartTime(), df));//搜索开始时间
            }

            if(ssWaterstationOrderInfo.getEndTime()!=null&&!"".equals(ssWaterstationOrderInfo.getEndTime())){
                queryWrapper.lt("order_date", LocalDateTime.parse(ssWaterstationOrderInfo.getEndTime(), df));//搜索结束时间
            }

            queryWrapper.orderBy(true, ssWaterstationOrderInfo.getIsAsc(), ssWaterstationOrderInfo.getIsSortField());

            // 执行查询
            ssWaterstationOrderInfoPage = ssWaterstationOrderInfoService.getSsWaterstationOrderInfoList(ssWaterstationOrderInfoPage, queryWrapper);

            // 设置返回数据
            return R.ok().setPageResult(ssWaterstationOrderInfoPage);
            //           System.out.println("User:" + data.getGoodsId() + "," + data.getNum()+","+data.getTotalCount());
        } catch (Exception e) {

            logger.warn(e.getMessage());

            return R.err().setMessage("系统错误");
        }
    }
}
