package com.cmtt.base.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cmtt.base.entity.SsStationGoods;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 水站商品信息 服务类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-31
 */
public interface ISsStationGoodsService extends IService<SsStationGoods> {

    public IPage<SsStationGoods> getStationGoodsPage(IPage<SsStationGoods> page, @Param(Constants.WRAPPER) Wrapper<SsStationGoods> queryWrapper);

    Integer updateDefaultSalePrice(Integer stationId, Integer goodsId);

    Integer updateSalePrice(SsStationGoods ssStationGoods);

    List<Map<Integer, String>> getGoodsDict(Integer status, Integer stationId);
}
