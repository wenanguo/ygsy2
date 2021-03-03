package com.cmtt.base.mapper;

import com.cmtt.base.entity.SsOrderDelivery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * <p>
 * 订单配送信息 Mapper 接口
 * </p>
 *
 * @author yuzm
 * @since 2021-02-14
 */
public interface SsOrderDeliveryMapper extends BaseMapper<SsOrderDelivery> {

    //更新配送中状态和时间
    @Update({"update ss_order_delivery set delevery_time = #{deleveryTime} where order_number=#{orderNumber} and delivery_man_id=#{deliveryManId} "})
    Integer delivery(LocalDateTime deleveryTime, String orderNumber, Integer deliveryManId);

    //配送完成
    @Update({"update ss_order_delivery set finish_time = #{finishTime} where order_number=#{orderNumber} and delivery_man_id=#{deliveryManId} "})
    Integer finish(LocalDateTime finishTime, String orderNumber, Integer deliveryManId);

    //更新个人订单的接单、配送和完成状态
    @Update({"update ss_order_info set order_status=#{status} where order_number=#{orderNumber} "})
    Integer updateOrderStatus(Integer status, String orderNumber);

    //更新水厂订单的接单、配送和完成状态
    @Update({"update ss_waterstation_order_info set order_status=#{status} where order_number=#{orderNumber} "})
    Integer updateOrderStatusOfStation(Integer status, String orderNumber);

}
