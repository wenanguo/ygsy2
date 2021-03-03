package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.cmtt.base.config.ss.configuration.JwtAuthenticationToken;
import com.cmtt.base.controller.miniappparams.OrderDetailParam;
import com.cmtt.base.controller.miniappparams.OrdersInputParam;
import com.cmtt.base.controller.miniappparams.PageInputParam;
import com.cmtt.base.entity.*;
import com.cmtt.base.entity.validated.GroupAdd;
import com.cmtt.base.entity.validated.GroupDelete;
import com.cmtt.base.entity.validated.GroupEdit;
import com.cmtt.base.utils.CommonUtils;
import com.cmtt.base.utils.OrderNumUtil;
import com.cmtt.base.utils.RC;
import com.tencentcloudapi.tcb.v20180608.models.OrderInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cmtt.base.service.ISsOrderInfoService;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>
 * 订单信息 前端控制器
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-30
 */
@RestController
@RequestMapping("/api/base/ss-order-info")
@Api(tags = "用户订单")
public class SsOrderInfoController {

private final Logger logger = LoggerFactory.getLogger(SsOrderInfoController.class);

@Autowired
public ISsOrderInfoService ssOrderInfoService;


    /**
    * 分页获取列表
    */
    @GetMapping("/list")
    @ResponseBody
    @ApiOperation("订单列表查询")
    public R list(SsOrderInfo ssOrderInfo, Principal principal, String orderStatus,HttpServletRequest httpServletRequest) {

        try {

            String phoneType = httpServletRequest.getHeader("platform"); //判断是后端代码还是小程序

            // 构建分页类
            IPage<SsOrderInfo> ssOrderInfoPage = new Page<>(ssOrderInfo.getPageNo(), ssOrderInfo.getPageSize());
            SysUser sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal();

            if(sysUser==null){
                return R.err();
            }

           // String openID = sysUser.getWxOpenid();
            // 构造查询及排序方式
            QueryWrapper<SsOrderInfo> queryWrapper = new QueryWrapper<>(ssOrderInfo);

//            if("miniprogram".equals(phoneType)){//小程序
//
//                queryWrapper.eq("customer_id",openID);
//
//            }else{//后台
//                if(customerId==null|| "".equals(customerId)){
//                    queryWrapper.eq("customer_id",null);
//                }
//                if(customerId!=null){
//                    queryWrapper.eq("customer_id",customerId);
//                }
//            }
            if(sysUser!=null){
                if (ssOrderInfo.getStationName()!=null&&!"".equals(ssOrderInfo.getStationName())){
                    queryWrapper.like("station_name",ssOrderInfo.getStationName());
                }

                if(orderStatus!=null&&!"".equals(orderStatus)) {
                    queryWrapper.eq("order_status", orderStatus);
                }

                queryWrapper.orderBy(true, ssOrderInfo.getIsAsc(), ssOrderInfo.getIsSortField());

                // 执行查询
                ssOrderInfoPage = ssOrderInfoService.getAllOrderDetailList(ssOrderInfoPage, queryWrapper);

                // 设置返回数据
                return R.ok().setPageResult(ssOrderInfoPage);
            }else{
                return R.err();
            }



        } catch (Exception e) {

            logger.warn(e.getMessage());

            return R.err().setMessage("系统错误");
        }

    }

    /**
     * 获取用户最后一条订单
     *
     * @return
     */
    @PostMapping("/getLastOrder")
    @ResponseBody
    @ApiOperation("查询当前用户最新一条订单信息")
    public R getLastOrder(HttpServletRequest httpServletRequest,Principal principal){
        try {
            String phoneType = httpServletRequest.getHeader("platform"); //判断是后端代码还是小程序
            SysUser sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal();

            if(sysUser==null){
                return R.err();
            }

          //  String openId = sysUser.getWxOpenid();
            String phone  = sysUser.getPhone();
            if("miniprogram".equals(phoneType)){
                List<Map<Integer, String>> list = ssOrderInfoService.getLastOrder(RC.B_NORMAL.code(),phone);
                // 设置返回数据
                return R.ok().setResult(list);
            }else{
                return R.err();
            }
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
        public R add(@RequestBody @Validated({GroupAdd.class})SsOrderInfo ssOrderInfo) {

        try {
        ssOrderInfoService.save(ssOrderInfo);

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
        public R edit(@RequestBody  @Validated({GroupEdit.class})SsOrderInfo ssOrderInfo) {


        try {

        ssOrderInfoService.updateById(ssOrderInfo);

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
        public R delete(@RequestBody @Validated({GroupDelete.class})SsOrderInfo ssOrderInfo) {

        try {

        ssOrderInfoService.removeById(ssOrderInfo.getId());

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

            ssOrderInfoService.removeByIds(ids);

            return R.ok().setMessage("批量删除成功");
            } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("批量删除失败");
            }
        }





    /**
     * 分页获取列表
     */
    @GetMapping("/listAll")
    @ResponseBody
    @ApiOperation("用户订单拓展大查询关联，用户，订单，商品，水站，派送员")
    public R listAll(SsOrderInfo ssOrderInfo, String orderStatus, String customerId, Principal principal, HttpServletRequest httpServletRequest) {

        try {

            String phoneType = httpServletRequest.getHeader("platform"); //判断是后端代码还是小程序

            // 构建分页类
            IPage<SsOrderInfo> ssOrderInfoPage = new Page<>(ssOrderInfo.getPageNo(), ssOrderInfo.getPageSize());
            SysUser sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal();

            if(sysUser==null){
                return R.err();
            }

         //   String openID = sysUser.getWxOpenid();
         //   String phone = sysUser.getPhone();
            Integer id = sysUser.getId();
            // 构造查询及排序方式
            QueryWrapper<SsOrderInfo> queryWrapper = new QueryWrapper<>(ssOrderInfo);

            if("miniprogram".equals(phoneType)){//小程序

                queryWrapper.eq("customer_id",id);

                if(ssOrderInfo.getOrderNumber()!=null&&!"".equals(ssOrderInfo.getOrderNumber())){
                    queryWrapper.eq("order_number",ssOrderInfo.getOrderNumber());
                }

                if(ssOrderInfo.getPayStatus()!=null&&!"".equals(ssOrderInfo.getPayStatus())){
                    queryWrapper.eq("pay_status",ssOrderInfo.getPayStatus());
                }
            }else{//后台
                if(customerId==null|| "".equals(customerId)){
                    queryWrapper.eq("customer_id",null);
                }
                if(customerId!=null&&!"".equals(customerId)){
                    queryWrapper.eq("customer_id",customerId);
                }
                if (ssOrderInfo.getStationName()!=null&&!"".equals(ssOrderInfo.getStationName())){
                    queryWrapper.like("station_name",ssOrderInfo.getStationName());
                }
           }

            if(orderStatus!=null&&!"".equals(orderStatus)) {
                queryWrapper.eq("order_status", orderStatus);
            }

            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


            if(ssOrderInfo.getStartTime()!=null&&!"".equals(ssOrderInfo.getStartTime())){
                queryWrapper.gt("order_date", LocalDateTime.parse(ssOrderInfo.getStartTime(), df));//搜索开始时间
            }

            if(ssOrderInfo.getEndTime()!=null&&!"".equals(ssOrderInfo.getEndTime())){
                queryWrapper.lt("order_date", LocalDateTime.parse(ssOrderInfo.getEndTime(), df));//搜索结束时间
            }


            queryWrapper.orderBy(true, ssOrderInfo.getIsAsc(), ssOrderInfo.getIsSortField());

            // 执行查询
            ssOrderInfoPage = ssOrderInfoService.getAllOrderDetailList(ssOrderInfoPage, queryWrapper);

            // 设置返回数据
            return R.ok().setPageResult(ssOrderInfoPage);


        } catch (Exception e) {

            logger.warn(e.getMessage());

            return R.err().setMessage("系统错误");
        }
    }


    /**
     * 用户购买商品后
     * 新增订单相关信息
     * lin
     */
    @PostMapping("/addAllOrderInfo")
    @ResponseBody
    public R addAllOrderInfo(@RequestBody @Valid OrdersInputParam ordersInfos, @RequestBody @Valid OrderDetailParam orderDetails, Principal principal) {

        SsOrderInfo ssOrderInfo =new SsOrderInfo();
        SsOrderDetail ssOrderDetail =new SsOrderDetail();
        List<SsOrderDetail> ssOrderDetails = new ArrayList<SsOrderDetail>();

        BeanUtils.copyProperties(ordersInfos,ssOrderInfo);
        BeanUtils.copyProperties(orderDetails,ssOrderDetail);

       // String orderNum = String.valueOf(System.currentTimeMillis()/ 10000); //订单编号
        String orderNum = CommonUtils.getDateNo();
        SysUser sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal(); //用户相关
        //String  openID = sysUser.getWxOpenid();
        //String  phone = sysUser.getPhone();
        Integer id = sysUser.getId();
        LocalDateTime createTime = LocalDateTime.now(); //创建时间

        try {
            if(sysUser==null){
                return R.err();
            }

            ssOrderInfo.setOrderStatus(1); //1-待分配，2-配送中，3-已完成，4-已取消
            ssOrderInfo.setPayStatus(ordersInfos.getPayStatus()); //支付状态：201-未支付,202-支付中,203-支付成功,204-支付失败
            ssOrderInfo.setOrderDate(createTime);

            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");



            ssOrderInfo.setAppointTime(LocalDateTime.parse(ordersInfos.getAppointTime(), df));
            ssOrderInfo.setOrderNumber(orderNum);
            ssOrderInfo.setCustomerId(id+"");
            ssOrderInfo.setPayWay(ordersInfos.getPayWay());
            ssOrderInfo.setDescription(ordersInfos.getDescription());
            ssOrderInfo.setTotalPrice(ordersInfos.getTotalPrice());

            ssOrderDetail.setNum(orderDetails.getNum());
            ssOrderDetail.setGoodsId(orderDetails.getGoodsId());
            ssOrderDetail.setOrderNumber(orderNum);
            ssOrderDetail.setAddressId(orderDetails.getAddressId());
            ssOrderDetail.setAmount(ordersInfos.getTotalPrice());
            ssOrderDetail.setPrice(ordersInfos.getTotalPrice());

            ssOrderDetails.add(ssOrderDetail);

            ssOrderInfoService.saveAllOrderInfo(ssOrderInfo,ssOrderDetails);

            return R.ok().setMessage("新增成功");

        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("新增失败");
        }
    }






    /**
     * 用户购买商品后
     * 新增订单相关信息
     * 生成预支付订单
     * lin
     */
    @PostMapping("/PayTicketReturnOrderNum.app")
    @ResponseBody
    @ApiOperation("用户端水票支付")
    public R PayTicketReturnOrderNum(@RequestBody @Valid OrdersInputParam params,  Principal principal) {

        SsOrderInfo ssOrderInfo =new SsOrderInfo();
        SsOrderDetail ssOrderDetail =new SsOrderDetail();
        List<SsOrderDetail> ssOrderDetails = new ArrayList<SsOrderDetail>();

        BeanUtils.copyProperties(params,ssOrderInfo);
        // BeanUtils.copyProperties(orderDetails,ssOrderDetail);

       // String orderNum = String.valueOf(System.currentTimeMillis()/ 1000000); //订单编号
        String orderNum = CommonUtils.getDateNo();
        SysUser sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal(); //用户相关
       // String  openID = sysUser.getWxOpenid();
       //   String phone  = sysUser.getPhone();
        Integer id = sysUser.getId();
        LocalDateTime createTime = LocalDateTime.now(); //创建时间

        try {
            if(sysUser==null){
                return R.err();
            }

            if(!"1".equals(params.getPayWay())){//微信支付方式不能用该种方式
                return R.err().setMessage("微信支付方式不能使用该种方式");
            }

            ssOrderInfo.setOrderStatus(1); //1-待分配，2-配送中，3-已完成，4-已取消
            ssOrderInfo.setPayStatus(201); //支付状态：201-未支付,202-支付中,203-支付成功,204-支付失败
            ssOrderInfo.setOrderDate(createTime);

            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            if(params.getAppointTime()!=null&&!"".equals(params.getAppointTime())){
                ssOrderInfo.setAppointTime(LocalDateTime.parse(params.getAppointTime(), df));
            }


            // ssOrderInfo.setAppointTime(params.getAppointTime());
            ssOrderInfo.setOrderNumber(orderNum);
            ssOrderInfo.setCustomerId(id+"");
            ssOrderInfo.setPayWay(params.getPayWay());
            ssOrderInfo.setDescription(params.getDescription());
            //  ssOrderInfo.setTotalPrice(params.getTotalPrice());

            ssOrderDetail.setNum(params.getNum());
            ssOrderDetail.setGoodsId(params.getGoodsId());
            ssOrderDetail.setOrderNumber(orderNum);
            ssOrderDetail.setAddressId(params.getAddressId());
            ssOrderDetail.setStationId(sysUser.getStationId());
            //    ssOrderDetail.setAmount(params.getTotalPrice());
            //    ssOrderDetail.setPrice(params.getTotalPrice());

            ssOrderDetails.add(ssOrderDetail);

            return  R.ok().setResult(ssOrderInfoService.PayTicketReturnOrderNum(ssOrderInfo,ssOrderDetails));

            //   return R.ok().setMessage("新增成功");

        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("新增失败");
        }
    }



    /**
     * 用户购买商品后
     * 新增订单相关信息
     * 生成预支付订单
     * lin
     */
    @PostMapping("/saveAllOrderInfoReturnOrderNum.app")
    @ResponseBody
    @ApiOperation("用户端预生成订单,订单未待分配，未支付")
    public R saveAllOrderInfoReturnOrderNum(@RequestBody @Valid OrdersInputParam params,  Principal principal) {

        SsOrderInfo ssOrderInfo =new SsOrderInfo();
        SsOrderDetail ssOrderDetail =new SsOrderDetail();
        List<SsOrderDetail> ssOrderDetails = new ArrayList<SsOrderDetail>();

        BeanUtils.copyProperties(params,ssOrderInfo);
       // BeanUtils.copyProperties(orderDetails,ssOrderDetail);

    //    String orderNum = String.valueOf(System.currentTimeMillis()/ 1000000); //订单编号
        String orderNum = CommonUtils.getDateNo();

        SysUser sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal(); //用户相关
       // String  openID = sysUser.getWxOpenid();
       // String  phone = sysUser.getPhone();
        Integer id = sysUser.getId();
        LocalDateTime createTime = LocalDateTime.now(); //创建时间

        try {
            if(sysUser==null){
                return R.err();
            }

            if("1".equals(params.getPayWay())){//水票支付方式不能用该种方式
                return R.err().setMessage("水票支付方式不能使用该种方式");
            }

            ssOrderInfo.setOrderStatus(1); //1-待分配，2-配送中，3-已完成，4-已取消
            ssOrderInfo.setPayStatus(201); //支付状态：201-未支付,202-支付中,203-支付成功,204-支付失败
            ssOrderInfo.setOrderDate(createTime);

            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            if(params.getAppointTime()!=null&&!"".equals(params.getAppointTime())){
                ssOrderInfo.setAppointTime(LocalDateTime.parse(params.getAppointTime(), df));
            }


           // ssOrderInfo.setAppointTime(params.getAppointTime());
            ssOrderInfo.setOrderNumber(orderNum);
            ssOrderInfo.setCustomerId(id+"");
            ssOrderInfo.setPayWay(params.getPayWay());
            ssOrderInfo.setDescription(params.getDescription());
          //  ssOrderInfo.setTotalPrice(params.getTotalPrice());

            ssOrderDetail.setNum(params.getNum());
            ssOrderDetail.setGoodsId(params.getGoodsId());
            ssOrderDetail.setOrderNumber(orderNum);
            ssOrderDetail.setAddressId(params.getAddressId());
        //    ssOrderDetail.setAmount(params.getTotalPrice());
        //    ssOrderDetail.setPrice(params.getTotalPrice());

            ssOrderDetails.add(ssOrderDetail);

            SsOrderInfo ssOrderInfoNew = ssOrderInfoService.saveAllOrderInfoReturnOrderNum(ssOrderInfo,ssOrderDetails);

            if(ssOrderInfoNew==null){
                return R.err().setMessage("新增失败");
            }


            // String goodsName = ssOrderInfoNew.getGoodsName(); //商品名称
//            String orderNo = ssOrderInfoNew.getOrderNumber(); //订单编号
//            double totalPrice = ssOrderInfoNew.getTotalPrice(); //订单总价


            // 微信支付发起预订单

            // 获得返回参数

            return  R.ok();

         //   return R.ok().setMessage("新增成功");

        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("新增失败");
        }
    }




    /**
     * 根据水站id查询水站带分派订单
     */
    @GetMapping("/listOrderInfoByStationId")
    @ResponseBody
    @ApiOperation("根据登录人的头信息里的水站id，查询已支付，待分配状态的订单")
    public R  listOrderInfo(SsOrderInfo ssOrderInfo, String orderstatus,Principal principal, HttpServletRequest httpServletRequest){

        try {

            String phoneType = httpServletRequest.getHeader("platform"); //判断是后端代码还是小程序

            // 构建分页类
            IPage<SsOrderInfo> ssOrderInfoPage = new Page<>(ssOrderInfo.getPageNo(), ssOrderInfo.getPageSize());
            SysUser sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal();

            if(sysUser==null){
                return R.err();
            }

            //String openID = sysUser.getWxOpenid();
            // 构造查询及排序方式
            QueryWrapper<SsOrderInfo> queryWrapper = new QueryWrapper<>(ssOrderInfo);

            if("miniprogram".equals(phoneType)){//小程序

                queryWrapper.eq("station_id",sysUser.getStationId())
                .eq("order_status",ssOrderInfo.getOrderStatus())
                .eq("pay_status",203);

            }else{//后台
                return R.err().setMessage("查询失败");
            }

            queryWrapper.orderBy(true, ssOrderInfo.getIsAsc(), ssOrderInfo.getIsSortField());

            // 执行查询
            ssOrderInfoPage = ssOrderInfoService.getOrderInfoByStationIdPage(ssOrderInfoPage, queryWrapper);

            // 设置返回数据
            return R.ok().setPageResult(ssOrderInfoPage);

        } catch (Exception e) {
            logger.error(e.getMessage());
            return R.err().setMessage("查询失败");

        }
    }

    /**
     * 派送员刚开始派送相关修改
     */
    @PutMapping("/editOrderByStation")
    @ResponseBody
    @ApiOperation("派送员派送")
    public R editOrderByStation(@RequestBody  @Validated({GroupEdit.class})OrdersInputParam param,Principal principal) {

        try {

            SsOrderInfo ssOrderInfo = new SsOrderInfo();
            SsOrderDelivery ssOrderDelivery = new SsOrderDelivery();

            BeanUtils.copyProperties(param,ssOrderInfo);


            SysUser sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal();

            if(sysUser==null){
                return R.err();
            }

            LocalDateTime createTime = LocalDateTime.now(); //创建时间

            ssOrderInfo.setOrderNumber(param.getOrderNumber());
            ssOrderInfo.setOrderStatus(2); //修改为派送中状态

            ssOrderDelivery.setDeliveryManId(Integer.parseInt(sysUser.getPhone()));
            //ssOrderDelivery.setStatus(1);//派送表派送中状态
            ssOrderDelivery.setReceiveTime(createTime);
            ssOrderDelivery.setOrderNumber(param.getOrderNumber());

            ssOrderInfoService.updateByOrderNum(ssOrderInfo,ssOrderDelivery);

            return R.ok().setMessage("修改成功");

        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("修改失败");
        }
    }

}
