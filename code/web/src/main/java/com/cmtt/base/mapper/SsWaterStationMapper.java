package com.cmtt.base.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cmtt.base.entity.SsWaterStation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 水站信息表 Mapper 接口
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-31
 */
public interface SsWaterStationMapper extends BaseMapper<SsWaterStation> {

    /**
     * 获取水站字典信息
     * @param status
     * @return
     */
    @Select({"select id, station_name from ss_water_station where status=#{status}"})
    List<Map<Integer, String>> getStationDict(Integer status);

    /**
     * 获取区域字典信息
     * @param status
     * @param parentarea 上级编码
     * @return
     */
    @Select({"select areacode, areadesc from ss_region_info where status=#{status} and parentarea = #{parentarea} order by areacode"})
    List<Map<String, String>> getRetionInfoDict(Integer status, String parentarea);

    /**
     * 重写水站分页查询
     * 增加了区域中文名称的查询
     * @return
     */
    @Select("\n" +
            " select a.id, a.station_name as stationName, a.leader_name as leaderName, a.phone, a.open_id as openId, a.landline \n" +
            " , a.province, a.city, a.county, a.town, a.address, a.latitude, a.longitude, a.status, a.available_balance as availableBalance \n" +
            " , a.frozen_balance as frozenBalance, a.create_by as createBy, a.create_time as createTime \n" +
            " , a.last_upadte_by as lastUpdateBy, a.last_update_time as lastUpdateTime \n" +
            " , b.areadesc as provinceName, c.areadesc as cityName, d.areadesc as countyName, e.areadesc as townName \n" +
            " from ss_water_station a \n" +
            " left join ss_region_info b on a.province=b.areacode and b.areatype=1 \n" +
            " left join ss_region_info c on a.city=c.areacode and c.areatype=1 \n" +
            " left join ss_region_info d on a.county=d.areacode and d.areatype=2 \n" +
            " left join ss_region_info e on a.town=e.areacode and e.areatype=3 \n" +
            " ${ew.customSqlSegment} " )
    IPage<SsWaterStation> getStationPage(IPage<SsWaterStation> page, @Param(Constants.WRAPPER) Wrapper<SsWaterStation> queryWrapper);


}
