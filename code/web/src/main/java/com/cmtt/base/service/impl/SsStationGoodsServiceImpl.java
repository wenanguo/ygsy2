package com.cmtt.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cmtt.base.entity.SsStationGoods;
import com.cmtt.base.mapper.SsStationGoodsMapper;
import com.cmtt.base.service.ISsStationGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 水站商品信息 服务实现类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-31
 */
@Service
public class SsStationGoodsServiceImpl extends ServiceImpl<SsStationGoodsMapper, SsStationGoods> implements ISsStationGoodsService {

    @Autowired
    private SsStationGoodsMapper ssStationGoodsMapper;

    @Override
    public IPage<SsStationGoods> getStationGoodsPage(IPage<SsStationGoods> page, Wrapper<SsStationGoods> queryWrapper) {
        return ssStationGoodsMapper.getStationGoodsPage(page, queryWrapper);
    }

    @Override
    public Integer updateDefaultSalePrice(Integer stationId, Integer goodsId) {
        return ssStationGoodsMapper.updateDefaultSalePrice(stationId, goodsId);
    }

    @Override
    public Integer updateSalePrice(SsStationGoods ssStationGoods) {
        return ssStationGoodsMapper.updateSalePrice(ssStationGoods.getStationSalePrice(), ssStationGoods.getStationId(), ssStationGoods.getGoodsId());
    }

    @Override
    public List<Map<Integer, String>> getGoodsDict(Integer status, Integer stationId) {
        return ssStationGoodsMapper.getGoodsDict(status, stationId);
    }
}
