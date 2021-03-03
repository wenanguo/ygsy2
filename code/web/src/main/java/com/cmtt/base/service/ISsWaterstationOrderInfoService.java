package com.cmtt.base.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cmtt.base.entity.SsWaterstationOrderInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 订单信息 服务类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-02-21
 */
public interface ISsWaterstationOrderInfoService extends IService<SsWaterstationOrderInfo> {

    IPage<SsWaterstationOrderInfo> getAllSsWaterstationOrderInfoList(IPage<SsWaterstationOrderInfo> page, @Param(Constants.WRAPPER) Wrapper<SsWaterstationOrderInfo> queryWrapper);

    IPage<SsWaterstationOrderInfo> getSsWaterstationOrderInfoList(IPage<SsWaterstationOrderInfo> page, @Param(Constants.WRAPPER) Wrapper<SsWaterstationOrderInfo> queryWrapper);

    //添加订单预下单
    @Transactional(rollbackFor = Exception.class)
    public SsWaterstationOrderInfo saveAllWaterstationOrderInfo(SsWaterstationOrderInfo ssWaterstationOrderInfo);

    //支付完成后，修改订单状态/
    @Transactional(rollbackFor = Exception.class)
    public boolean UpdateAllOrderInfo(String orderNum,String payStatus);

}
