package com.cmtt.base.mapper;

import com.cmtt.base.entity.SsOrderLocation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单轨迹信息 Mapper 接口
 * </p>
 *
 * @author yuzm
 * @since 2021-02-18
 */
public interface SsOrderLocationMapper extends BaseMapper<SsOrderLocation> {

    /**
     * 根据订单获取配送员定位
     * @param orderNumber
     * @return
     */
    //@Select({"select longitude, latitude from ss_order_location where order_number=#{orderNumber} order by update_time"})
    @Select({"select b.longitude,b.latitude from ss_order_delivery a inner join ss_order_location b on a.delivery_man_id=b.delivery_man_id inner join ss_order_info c on a.order_number=c.order_number where c.order_status in (2,3) and a.order_number=#{orderNumber} limit 1"})
    Map<BigDecimal, BigDecimal> getLocationList(String orderNumber);
}
