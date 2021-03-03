package com.cmtt.base.service;

import com.cmtt.base.entity.SsOrderLocation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单轨迹信息 服务类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-02-18
 */
public interface ISsOrderLocationService extends IService<SsOrderLocation> {

    Map<BigDecimal, BigDecimal> getLocationList(String orderNumber);

}
