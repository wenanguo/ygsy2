package com.cmtt.base.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cmtt.base.entity.*;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tencentcloudapi.tcb.v20180608.models.OrderInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单信息 服务类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-30
 */
public interface ISsOrderInfoService extends IService<SsOrderInfo> {

    //获取用户最后一条订单信息
    List<Map<Integer, String>> getLastOrder(Integer status,String openId);


    //获取用户水票表全部信息
    public IPage<SsOrderInfo> getAllOrderDetailList(IPage<SsOrderInfo> page, @Param(Constants.WRAPPER) Wrapper<SsOrderInfo> queryWrapper);

    //添加订单相关插入
    @Transactional(rollbackFor = Exception.class)
    public boolean saveAllOrderInfo(SsOrderInfo ssOrderInfo,List<SsOrderDetail> ssOrderDetails);

    //添加订单相关修改
    @Transactional(rollbackFor = Exception.class)
    public boolean UpdateAllOrderInfo(String orderNum,String status);

    @Transactional(rollbackFor = Exception.class)
    SsOrderInfo saveAllOrderInfoReturnOrderNum(SsOrderInfo ssOrderInfo,List<SsOrderDetail> ssOrderDetails);

    /**
     * 水票支付
     * @param ssOrderInfo
     * @param ssOrderDetails
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    SsOrderInfo PayTicketReturnOrderNum(SsOrderInfo ssOrderInfo,List<SsOrderDetail> ssOrderDetails);


     //判断购买商品是否为水票
     SsGoodsInfo getTicketOrNot(Integer goodsId);

     //根据水站id获取已支付未派送的订单信息
     List<SsOrderInfo> getOrderInfoByStationId(Integer stationId);

     //水站派单员灵丹
    @Transactional(rollbackFor = Exception.class)
    boolean  updateByOrderNum(SsOrderInfo ssOrderInfo, SsOrderDelivery ssOrderDelivery);

    //水站派送表更新
    @Transactional(rollbackFor = Exception.class)
    boolean  updateByOrderNumForDeliver(SsOrderDelivery ssOrderDelivery);

    IPage<SsOrderInfo> getOrderInfoByStationIdPage(IPage<SsOrderInfo> page, @Param(Constants.WRAPPER) Wrapper<SsOrderInfo> queryWrapper);

}
