package com.cmtt.base.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmtt.base.entity.SsCustomerAddress;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 * 客户地址信息 服务类
 * </p>
 *
 * @author Andrew.Wen1
 * @since 2021-01-29
 */
public interface ISsCustomerAddressService extends IService<SsCustomerAddress> {
    Page<Map<String,Object>> selectListPage(int current, int number, String id);

    Map<String, Object> getNearestStation(BigDecimal longitude, BigDecimal latitude, Integer status);

    Integer updateStation(Integer stationId, Integer addressId);
}
