package com.cmtt.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cmtt.base.entity.SsGoodsInfo;
import com.cmtt.base.entity.SsWaterStation;
import com.cmtt.base.mapper.SsGoodsInfoMapper;
import com.cmtt.base.service.ISsGoodsInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-29
 */
@Service
public class SsGoodsInfoServiceImpl extends ServiceImpl<SsGoodsInfoMapper, SsGoodsInfo> implements ISsGoodsInfoService {

    @Autowired
    private SsGoodsInfoMapper ssGoodsInfoMapper;
    @Override
    public List<Map<Integer, String>> getGoodsDict(Integer status) {
        return ssGoodsInfoMapper.getGoodsDict(status);
    }

    @Override
    public Integer getLastGoodsId() {
        return ssGoodsInfoMapper.getLastGoodsId();
    }

    @Override
    public SsGoodsInfo getGoodsDetail(Integer goodsId) {
        return ssGoodsInfoMapper.getGoodsDetail(goodsId);
    }

    @Override
    public SsGoodsInfo getLastGoodsDetailWithAll(Integer customer_id) {
        return ssGoodsInfoMapper.getLastGoodsDetailWithAll(customer_id);
    }

    @Override
    public SsGoodsInfo getLastGoodsDetail(Integer customer_id) {
        return ssGoodsInfoMapper.getLastGoodsDetail(customer_id);
    }

    @Override
    public SsGoodsInfo getLastTicketDetail(Integer customer_id) {
        return ssGoodsInfoMapper.getLastTicketDetail(customer_id);
    }

    @Override
    public IPage<SsGoodsInfo> getGoodsPage(IPage<SsGoodsInfo> page, Wrapper<SsGoodsInfo> queryWrapper) {
        return ssGoodsInfoMapper.getGoodsPage(page, queryWrapper);
    }

    @Override
    public SsGoodsInfo getAllWithBuyMost(Integer status) {
        return ssGoodsInfoMapper.getAllWithBuyMost(status);
    }

    @Override
    public SsGoodsInfo getGoodsWithBuyMost(Integer status) {
        return ssGoodsInfoMapper.getGoodsWithBuyMost(status);
    }

    @Override
    public SsGoodsInfo getTicketWithBuyMost(Integer status) {
        return ssGoodsInfoMapper.getTicketWithBuyMost(status);
    }

    @Override
    public Integer getTicketNum(Integer customerId, Double goodsValue) {
        return ssGoodsInfoMapper.getTicketNum(customerId, goodsValue);
    }

    @Override
    public IPage<SsGoodsInfo> getStationGoodsPage(IPage<SsGoodsInfo> page, Wrapper<SsGoodsInfo> queryWrapper) {
        return ssGoodsInfoMapper.getStationGoodsPage(page,queryWrapper);
    }

}
