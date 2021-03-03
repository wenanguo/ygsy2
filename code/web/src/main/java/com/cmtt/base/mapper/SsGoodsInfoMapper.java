package com.cmtt.base.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cmtt.base.entity.SsGoodsInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品信息 Mapper 接口
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-29
 */
public interface SsGoodsInfoMapper extends BaseMapper<SsGoodsInfo> {

    /**
     * 获取商品字典信息
     * @param status
     * @return
     */
    @Select({"select id, goods_name from ss_goods_info where status=#{status}"})
    List<Map<Integer, String>> getGoodsDict(Integer status);

    /**
     * 获取商品添加后的最新ID
     * @return
     */
    @Select({"select id from ss_goods_info order by id desc limit 1"})
    Integer getLastGoodsId();


    /**
     * 小程序：分页获取商品列表
     * @param queryWrapper
     * @return
     */
    @Select({"select a.id, a.goods_name as goodsName, a.goods_code as goodsCode, a.goods_type_id as goodsTypeId, a.ticket_id as ticketId \n" +
            ", a.ticket_num as ticketNum, a.unit, a.unit_price as unitPrice, a.cost_price as costPrice, a.sort, a.description \n" +
            ", a.goods_img as goodsImg, a.status, a.isrecommend, a.deleted, a.publish_time as publishTime, a.expired_time as expiredTime \n" +
            ", b.type_name as typeName, e.goodsTagStr, f.ticket_name as ticketName \n" +
            " from ss_goods_info a \n" +
            " left join ss_goods_type b on a.goods_type_id = b.id \n" +
            " left join ( \n" +
            " select c.goods_id, GROUP_CONCAT(d.tag_name separator '~|') as goodsTagStr from ss_goods_tag c inner join ss_tag_info d on c.tag_id = d.id \n" +
            " group by c.goods_id ) e on a.id = e.goods_id \n" +
            " left join ss_water_ticket f on a.ticket_id = f.id \n" +
            " ${ew.customSqlSegment} " })
    IPage<SsGoodsInfo> getGoodsPage(IPage<SsGoodsInfo> page, @Param(Constants.WRAPPER) Wrapper<SsGoodsInfo> queryWrapper);

    /**
     * 小程序：获取商品详情
     * @param goodsId
     * @return
     */
    @Select({"select a.id, a.goods_name as goodsName, a.goods_code as goodsCode, a.goods_type_id as goodsTypeId, a.ticket_id as ticketId \n" +
            ", a.ticket_num as ticketNum, a.unit, a.unit_price as unitPrice, a.cost_price as costPrice, a.sort, a.description \n" +
            ", a.goods_img as goodsImg, a.status, a.isrecommend, a.deleted, a.publish_time as publishTime, a.expired_time as expiredTime \n" +
            ", b.type_name as typeName, e.goodsTagStr, f.ticket_name as ticketName \n" +
            " from ss_goods_info a \n" +
            " left join ss_goods_type b on a.goods_type_id = b.id \n" +
            " left join ( \n" +
            " select c.goods_id, GROUP_CONCAT(d.tag_name separator '~|') as goodsTagStr from ss_goods_tag c inner join ss_tag_info d on c.tag_id = d.id \n" +
            " where c.goods_id = #{goodsId} group by c.goods_id ) e on a.id = e.goods_id \n" +
            " left join ss_water_ticket f on a.ticket_id = f.id \n" +
            " where a.id = #{goodsId}"})
    SsGoodsInfo getGoodsDetail(Integer goodsId);


    /**
     * 小程序：获取用户上一次购买的商品详情
     * @param customer_id 用户编号
     * @return
     */
    @Select({"select a.id, a.goods_name as goodsName, a.goods_code as goodsCode, a.goods_type_id as goodsTypeId, a.ticket_id as ticketId \n" +
            ", a.ticket_num as ticketNum, a.unit, a.unit_price as unitPrice, a.cost_price as costPrice, a.sort, a.description \n" +
            ", a.goods_img as goodsImg, a.status, a.isrecommend, a.deleted, a.publish_time as publishTime, a.expired_time as expiredTime \n" +
            ", b.num, c.order_number orderNumber, c.order_date orderDate, c.order_status orderStatus, d.type_name as typeName, e.goodsTagStr, f.ticket_name as ticketName \n" +
            " from ss_goods_info a \n" +
            " inner join ss_order_detail b on a.id = b.goods_id \n" +
            " inner join ss_order_info c on b.order_number = c.order_number \n" +
            " left join ss_goods_type d on a.goods_type_id = d.id \n" +
            " left join ( \n" +
            "   select c.goods_id, GROUP_CONCAT(d.tag_name separator '~|') as goodsTagStr from ss_goods_tag c inner join ss_tag_info d on c.tag_id = d.id group by c.goods_id  \n" +
            " ) e on a.id = e.goods_id \n" +
            " left join ss_water_ticket f on a.ticket_id = f.id \n" +
            " where c.customer_id = #{customer_id} order by c.order_date desc limit 1\n"})
    SsGoodsInfo getLastGoodsDetailWithAll(Integer customer_id);

    /**
     * 小程序：获取用户上一次购买的商品(水)详情
     * @param customer_id 用户编号
     * @return
     */
    @Select({"select a.id, a.goods_name as goodsName, a.goods_code as goodsCode, a.goods_type_id as goodsTypeId, a.ticket_id as ticketId \n" +
            ", a.ticket_num as ticketNum, a.unit, a.unit_price as unitPrice, a.cost_price as costPrice, a.sort, a.description \n" +
            ", a.goods_img as goodsImg, a.status, a.isrecommend, a.deleted, a.publish_time as publishTime, a.expired_time as expiredTime \n" +
            ", b.num, c.order_number orderNumber, c.order_date orderDate, c.order_status orderStatus, d.type_name as typeName, e.goodsTagStr, f.ticket_name as ticketName \n" +
            " from ss_goods_info a \n" +
            " inner join ss_order_detail b on a.id = b.goods_id \n" +
            " inner join ss_order_info c on b.order_number = c.order_number \n" +
            " left join ss_goods_type d on a.goods_type_id = d.id \n" +
            " left join ( \n" +
            "   select c.goods_id, GROUP_CONCAT(d.tag_name separator '~|') as goodsTagStr from ss_goods_tag c inner join ss_tag_info d on c.tag_id = d.id group by c.goods_id  \n" +
            " ) e on a.id = e.goods_id \n" +
            " left join ss_water_ticket f on a.ticket_id = f.id \n" +
            " where c.customer_id = #{customer_id} and a.ticket_id is null order by c.order_date desc limit 1\n"})
    SsGoodsInfo getLastGoodsDetail(Integer customer_id);


    /**
     * 小程序：获取用户上一次购买的商品（水票）详情
     * @param customer_id 用户编号
     * @return
     */
    @Select({"select a.id, a.goods_name as goodsName, a.goods_code as goodsCode, a.goods_type_id as goodsTypeId, a.ticket_id as ticketId \n" +
            ", a.ticket_num as ticketNum, a.unit, a.unit_price as unitPrice, a.cost_price as costPrice, a.sort, a.description \n" +
            ", a.goods_img as goodsImg, a.status, a.isrecommend, a.deleted, a.publish_time as publishTime, a.expired_time as expiredTime \n" +
            ", b.num, c.order_number orderNumber, c.order_date orderDate, c.order_status orderStatus, d.type_name as typeName, e.goodsTagStr, f.ticket_name as ticketName \n" +
            " from ss_goods_info a \n" +
            " inner join ss_order_detail b on a.id = b.goods_id \n" +
            " inner join ss_order_info c on b.order_number = c.order_number \n" +
            " left join ss_goods_type d on a.goods_type_id = d.id \n" +
            " left join ( \n" +
            "   select c.goods_id, GROUP_CONCAT(d.tag_name separator '~|') as goodsTagStr from ss_goods_tag c inner join ss_tag_info d on c.tag_id = d.id group by c.goods_id  \n" +
            " ) e on a.id = e.goods_id \n" +
            " left join ss_water_ticket f on a.ticket_id = f.id \n" +
            " where c.customer_id = #{customer_id} and a.ticket_id is not null order by c.order_date desc limit 1\n"})
    SsGoodsInfo getLastTicketDetail(Integer customer_id);


    /**
     * 小程序：获取所有商品中购买最多的商品详情
     * @return
     */
    @Select({"select a.id, a.goods_name as goodsName, a.goods_code as goodsCode, a.goods_type_id as goodsTypeId, a.ticket_id as ticketId \n" +
            ", a.ticket_num as ticketNum, a.unit, a.unit_price as unitPrice, a.cost_price as costPrice, a.sort, a.description \n" +
            ", a.goods_img as goodsImg, a.status, a.isrecommend, a.deleted, a.publish_time as publishTime, a.expired_time as expiredTime \n" +
            ", d.type_name as typeName, e.goodsTagStr \n" +
            " from ss_goods_info a \n" +
            " left join (select b.goods_id, sum(b.num) total from ss_order_detail b group by b.goods_id order by total desc) c on a.id=c.goods_id \n" +
            " left join ss_goods_type d on a.goods_type_id = d.id \n" +
            " left join ( \n" +
            "   select c.goods_id, GROUP_CONCAT(d.tag_name separator '~|') as goodsTagStr from ss_goods_tag c inner join ss_tag_info d on c.tag_id = d.id group by c.goods_id  \n" +
            " ) e on a.id = e.goods_id \n" +
            " where a.`status`= #{status} order by c.total desc limit 1\n" })
    SsGoodsInfo getAllWithBuyMost(Integer status);

    /**
     * 小程序：获取购买最多的水详情
     * @return
     */
    @Select({"select a.id, a.goods_name as goodsName, a.goods_code as goodsCode, a.goods_type_id as goodsTypeId, a.ticket_id as ticketId \n" +
            ", a.ticket_num as ticketNum, a.unit, a.unit_price as unitPrice, a.cost_price as costPrice, a.sort, a.description \n" +
            ", a.goods_img as goodsImg, a.status, a.isrecommend, a.deleted, a.publish_time as publishTime, a.expired_time as expiredTime \n" +
            ", d.type_name as typeName, e.goodsTagStr \n" +
            " from ss_goods_info a \n" +
            " left join (select b.goods_id, sum(b.num) total from ss_order_detail b group by b.goods_id order by total desc) c on a.id=c.goods_id \n" +
            " left join ss_goods_type d on a.goods_type_id = d.id \n" +
            " left join ( \n" +
            "   select c.goods_id, GROUP_CONCAT(d.tag_name separator '~|') as goodsTagStr from ss_goods_tag c inner join ss_tag_info d on c.tag_id = d.id group by c.goods_id  \n" +
            " ) e on a.id = e.goods_id \n" +
            " where a.`status`= #{status} and a.ticket_id is null order by c.total desc limit 1\n" })
    SsGoodsInfo getGoodsWithBuyMost(Integer status);

    /**
     * 小程序：获取购买最多的水票详情
     * @return
     */
    @Select({"select a.id, a.goods_name as goodsName, a.goods_code as goodsCode, a.goods_type_id as goodsTypeId, a.ticket_id as ticketId \n" +
            ", a.ticket_num as ticketNum, a.unit, a.unit_price as unitPrice, a.cost_price as costPrice, a.sort, a.description \n" +
            ", a.goods_img as goodsImg, a.status, a.isrecommend, a.deleted, a.publish_time as publishTime, a.expired_time as expiredTime \n" +
            ", d.type_name as typeName, e.goodsTagStr \n" +
            " from ss_goods_info a \n" +
            " left join (select b.goods_id, sum(b.num) total from ss_order_detail b group by b.goods_id order by total desc) c on a.id=c.goods_id \n" +
            " left join ss_goods_type d on a.goods_type_id = d.id \n" +
            " left join ( \n" +
            "   select c.goods_id, GROUP_CONCAT(d.tag_name separator '~|') as goodsTagStr from ss_goods_tag c inner join ss_tag_info d on c.tag_id = d.id group by c.goods_id  \n" +
            " ) e on a.id = e.goods_id \n" +
            " where a.`status`= #{status} and a.ticket_id is not null order by c.total desc limit 1\n" })
    SsGoodsInfo getTicketWithBuyMost(Integer status);

    /**
     * 小程序：获取与某商品价值相等的用户水票数量
     * @param customerId 用户Id
     * @param goodsValue 商品售价
     * @return
     */
    @Select({"select count(a.ticket_id) customerTicketnum from ss_customer_ticket a \n" +
            " inner join ss_water_ticket b on a.ticket_id = b.id \n" +
            " where a.use_status = 1 and a.customer_id=#{customerId} and b.ticket_value = #{goodsValue} group by a.customer_id"})
    Integer getTicketNum(Integer customerId, Double goodsValue);

    /**
     * 小程序：分页获取水站商品列表
     * @param queryWrapper
     * @return
     */
    @Select({"select a.id, a.goods_name as goodsName, a.goods_code as goodsCode, a.goods_type_id as goodsTypeId, a.ticket_id as ticketId \n" +
            ", a.ticket_num as ticketNum, a.unit, g.station_sale_price as unitPrice, a.sort, a.description \n" +
            ", a.goods_img as goodsImg, a.status, a.isrecommend, a.deleted, a.publish_time as publishTime, a.expired_time as expiredTime \n" +
            ", b.type_name as typeName, e.goodsTagStr, f.ticket_name as ticketName \n" +
            " from ss_goods_info a \n" +
            " left join ss_goods_type b on a.goods_type_id = b.id \n" +
            " left join ( \n" +
            " select c.goods_id, GROUP_CONCAT(d.tag_name separator '~|') as goodsTagStr from ss_goods_tag c inner join ss_tag_info d on c.tag_id = d.id \n" +
            " group by c.goods_id ) e on a.id = e.goods_id \n" +
            " left join ss_water_ticket f on a.ticket_id = f.id \n" +
            " inner join ss_station_goods g on a.id = g.goods_id" +
            " ${ew.customSqlSegment} " })
    IPage<SsGoodsInfo> getStationGoodsPage(IPage<SsGoodsInfo> page, @Param(Constants.WRAPPER) Wrapper<SsGoodsInfo> queryWrapper);

}
