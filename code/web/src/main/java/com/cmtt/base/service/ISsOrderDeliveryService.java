package com.cmtt.base.service;

import com.cmtt.base.entity.SsOrderDelivery;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDateTime;

/**
 * <p>
 * 订单配送信息 服务类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-02-14
 */
public interface ISsOrderDeliveryService extends IService<SsOrderDelivery> {

    Integer delivery(LocalDateTime deleveryTime, String orderNumber, Integer deliveryManId);

    Integer finish(LocalDateTime finishTime, String orderNumber, Integer deliveryManId);

    Integer updateOrderStatus(Integer status, String orderNumber);

    Integer updateOrderStatusOfStation(Integer status, String orderNumber);

}
