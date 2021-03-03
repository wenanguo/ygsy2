package com.cmtt.base.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmtt.base.entity.SsCustomerAddress;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 客户地址信息 Mapper 接口
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-29
 */
public interface SsCustomerAddressMapper extends BaseMapper<SsCustomerAddress> {
    //查询用户关联地址表
    @Select("select t1.*,t2.customer_name from ss_customer_address t1 LEFT JOIN  ss_customer_info t2  ON t1.customer_id =t2.customer_id and t1.customer_id= #{id}")
    List<Map<String,Object>> CustomerAddressList(Page<Map<String,Object>> page, String id);

    /**
     * 获取商品添加后的最新ID
     * @return
     */
    @Select({"select a.id, ROUND(Spherical_Distance(a.longitude, a.latitude, #{longitude}, #{latitude}), 2) dist from ss_water_station a where a.`status`=#{status} order by dist limit 1 \n"})
    Map<String, Object> getNearestStation(BigDecimal longitude, BigDecimal latitude, Integer status);

    /**
     * 修改用户地址的归属水站
     * @param stationId 水站ID
     * @param addressId 地址ID
     * @return
     */
    @Update({"update ss_customer_address set station_id=#{stationId} where id=#{addressId}"})
    Integer updateStation(Integer stationId, Integer addressId);
}
