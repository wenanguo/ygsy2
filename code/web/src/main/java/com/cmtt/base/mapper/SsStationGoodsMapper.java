package com.cmtt.base.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cmtt.base.entity.SsStationGoods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cmtt.base.entity.SsWaterStation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 水站商品信息 Mapper 接口
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-31
 */
public interface SsStationGoodsMapper extends BaseMapper<SsStationGoods> {

    /**
     * 水站商品分页查询
     * 增加了商品名称和水站名称的查询
     * @return
     */
    @Select("\n" +
            " select a.id, a.station_id as stationId, a.goods_id as goodsId, a.status, a.station_sale_price as stationSalePrice \n" +
            " , b.station_name as stationName, c.goods_name as goodsName, c.unit_price as unitPrice, c.cost_price as costPrice \n" +
            " from ss_station_goods a \n" +
            " inner join ss_water_station b on a.station_id=b.id  \n" +
            " inner join  ss_goods_info c on a.goods_id=c.id \n" +
            " ${ew.customSqlSegment} " )
    IPage<SsStationGoods> getStationGoodsPage(IPage<SsStationGoods> page, @Param(Constants.WRAPPER) Wrapper<SsStationGoods> queryWrapper);

    /**
     * 水站添加商品时，默认设置归属水站的商品价为原商品价
     * @param stationId
     * @param goodsId
     * @return
     */
    @Update({"update ss_station_goods a, ss_goods_info b set a.station_sale_price = b.unit_price where a.goods_id=b.id and a.station_id =#{stationId} and a.goods_id = #{goodsId} and a.station_sale_price is null"})
    Integer updateDefaultSalePrice(Integer stationId, Integer goodsId);

    /**
     * 修改水站商品的价格
     * @param stationSalePrice
     * @param stationId
     * @param goodsId
     * @return
     */
    @Update({"update ss_station_goods set station_sale_price=#{stationSalePrice} where station_id=#{stationId} and goods_id = #{goodsId}"})
    Integer updateSalePrice(Double stationSalePrice, Integer stationId, Integer goodsId);

    /**
     * 获取未归属水站的商品字典信息
     * @param status
     * @return
     */
    @Select({"select id, goods_name from ss_goods_info a where status=#{status} and a.ticket_id is null and a.id not in (select goods_id from ss_station_goods where station_id=#{stationId})"})
    List<Map<Integer, String>> getGoodsDict(Integer status, Integer stationId);
}
