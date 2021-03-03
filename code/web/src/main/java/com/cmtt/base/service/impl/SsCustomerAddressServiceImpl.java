package com.cmtt.base.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmtt.base.entity.SsCustomerAddress;
import com.cmtt.base.mapper.SsCustomerAddressMapper;
import com.cmtt.base.service.ISsCustomerAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 * 客户地址信息 服务实现类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-29
 */
@Service
public class SsCustomerAddressServiceImpl extends ServiceImpl<SsCustomerAddressMapper, SsCustomerAddress> implements ISsCustomerAddressService {
    @Override
    public Page<Map<String, Object>> selectListPage(int current, int number, String id) {
        //新建分页
        Page<Map<String,Object>> page =new Page<Map<String,Object>>(current,number);
        //返回结果
        return  page.setRecords(this.baseMapper.CustomerAddressList(page,id));
    }

    @Override
    public Map<String, Object> getNearestStation(BigDecimal longitude, BigDecimal latitude, Integer status) {
        return this.baseMapper.getNearestStation(longitude, latitude, status);
    }

    @Override
    public Integer updateStation(Integer stationId, Integer addressId) {
        return this.baseMapper.updateStation(stationId, addressId);
    }
}
