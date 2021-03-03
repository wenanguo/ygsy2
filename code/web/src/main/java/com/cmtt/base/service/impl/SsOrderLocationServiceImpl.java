package com.cmtt.base.service.impl;

import com.cmtt.base.entity.SsOrderLocation;
import com.cmtt.base.mapper.SsOrderLocationMapper;
import com.cmtt.base.service.ISsOrderLocationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单轨迹信息 服务实现类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-02-18
 */
@Service
public class SsOrderLocationServiceImpl extends ServiceImpl<SsOrderLocationMapper, SsOrderLocation> implements ISsOrderLocationService {

    @Autowired
    private SsOrderLocationMapper ssOrderLocationMapper;

    @Override
    public Map<BigDecimal, BigDecimal> getLocationList(String orderNumber) {
        return ssOrderLocationMapper.getLocationList(orderNumber);
    }
}
