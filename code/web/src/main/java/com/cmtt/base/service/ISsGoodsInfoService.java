package com.cmtt.base.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cmtt.base.entity.SsGoodsInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品信息 服务类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-29
 */
public interface ISsGoodsInfoService extends IService<SsGoodsInfo> {

    List<Map<Integer, String>> getGoodsDict(Integer status);

    Integer getLastGoodsId();

    SsGoodsInfo getGoodsDetail(Integer goodsId);


    SsGoodsInfo getLastGoodsDetailWithAll(Integer customer_id);

    SsGoodsInfo getLastGoodsDetail(Integer customer_id);

    SsGoodsInfo getLastTicketDetail(Integer customer_id);

    IPage<SsGoodsInfo> getGoodsPage(IPage<SsGoodsInfo> page, @Param(Constants.WRAPPER) Wrapper<SsGoodsInfo> queryWrapper);

    SsGoodsInfo getAllWithBuyMost(Integer status);

    SsGoodsInfo getGoodsWithBuyMost(Integer status);

    SsGoodsInfo getTicketWithBuyMost(Integer status);

    Integer getTicketNum(Integer customerId, Double goodsValue);

    IPage<SsGoodsInfo> getStationGoodsPage(IPage<SsGoodsInfo> page, @Param(Constants.WRAPPER) Wrapper<SsGoodsInfo> queryWrapper);
}
