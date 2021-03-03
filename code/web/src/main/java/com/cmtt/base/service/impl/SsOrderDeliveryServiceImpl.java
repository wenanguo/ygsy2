package com.cmtt.base.service.impl;

import com.cmtt.base.entity.SsOrderDelivery;
import com.cmtt.base.mapper.SsOrderDeliveryMapper;
import com.cmtt.base.service.ISsOrderDeliveryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 订单配送信息 服务实现类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-02-14
 */
@Service
public class SsOrderDeliveryServiceImpl extends ServiceImpl<SsOrderDeliveryMapper, SsOrderDelivery> implements ISsOrderDeliveryService {

    @Autowired
    private SsOrderDeliveryMapper ssOrderDeliveryMapper;

    @Override
    public Integer delivery(LocalDateTime deleveryTime, String orderNumber, Integer deliveryManId) {
        return ssOrderDeliveryMapper.delivery(deleveryTime, orderNumber, deliveryManId);
    }

    @Override
    public Integer finish(LocalDateTime finishTime, String orderNumber, Integer deliveryManId) {
        return ssOrderDeliveryMapper.finish(finishTime, orderNumber, deliveryManId);
    }

    @Override
    public Integer updateOrderStatus(Integer status, String orderNumber) {
        return ssOrderDeliveryMapper.updateOrderStatus(status, orderNumber);
    }

    @Override
    public Integer updateOrderStatusOfStation(Integer status, String orderNumber) {
        return ssOrderDeliveryMapper.updateOrderStatusOfStation(status, orderNumber);
    }
}
