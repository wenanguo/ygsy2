package com.cmtt.base.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cmtt.base.entity.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 订单信息 Mapper 接口
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-02-21
 */
@Mapper
@Component(value = "ssWaterstationOrderInfoMapper")
public interface SsWaterstationOrderInfoMapper extends BaseMapper<SsWaterstationOrderInfo> {

    //订单明细信息查询
    @Select(" select order_number1 as id,order_number1 as order_number,delevery_username,delevery_phone,                                                                                              \n"+
            " order_date as orderDate,receive_time as receiveTime,delevery_time as deleveryTime,delivery_man_id,finish_time as finishTime,                                                                                             \n"+
            " ss_waterstation_order_detail as ssOrderDetailNum,ss_waterstation_order_detail_price as ssOrderDetailPrice,amount,goods_name,goods_code,                                                                           \n"+
            " goods_type_id,ticket_id,ticket_num, unit,unit_price,cost_price,goods_img,                                                                                               \n"+
            " ss_goods_info_status, customer_id,goods_id,order_status,                                                                                                                \n"+
            " station_name,station_leader_name,station_phone,station_address,station_status,pay_way,                                                                                  \n"+
            " pay_status,totalprice,stationId                                                                                                                                         \n"+
            " from                                                                                                                                                                    \n"+
            "  ( select order_number1,customer_name,customer_phone,customer_sex,                                                                                                      \n"+
            " delevery_username,delevery_phone, order_date,receive_time,delevery_time,                                                                                                \n"+
            " delivery_man_id,finish_time, order_status,                                                                                                                     \n"+
            " num as ss_waterstation_order_detail,price as ss_waterstation_order_detail_price,                                                                                        \n"+
            " amount,goods_name,goods_code,goods_type_id,ticket_id,ticket_num,                                                                                                        \n"+
            " unit,unit_price,cost_price,goods_img,ss_goods_info_status,                                                                                                              \n"+
            " customer_id,goods_id,stationId,station_name,                                                                                                                            \n"+
            " station_leader_name,station_phone,station_address,                                                                                                                      \n"+
            " station_status,pay_way,pay_status,totalprice                                                                                                                            \n"+
            " from                                                                                                                                                                    \n"+
            " ( select customer_id,delevery_username,delevery_phone,stationId,                                                                                                        \n"+
            " order_number1,order_date,receive_time,delevery_time,delivery_man_id,finish_time,                                                                                                        \n"+
            " order_status, goods_id,num,price,pay_way,                                                                                                      \n"+
            " amount,goods_name,goods_code,goods_type_id,ticket_id,ticket_num,                                                                                                        \n"+
            " unit,unit_price,cost_price,goods_img,ss_goods_info_status,                                                                                                              \n"+
            " station_name,station_leader_name,station_phone,                                                                                                                         \n"+
            " station_address,station_status,pay_status,totalprice                                                                                                                    \n"+
            " from                                                                                                                                                                    \n"+
            " ( select stationId,customer_id,username as delevery_username,                                                                                                           \n"+
            " phone as delevery_phone,order_number1,order_date,receive_time,delevery_time,                                                                                            \n"+
            " delivery_man_id,finish_time,order_status, goods_id,num,price,amount,                                                                                           \n"+
            " goods_name,goods_code,goods_type_id,ticket_id,ticket_num,unit,unit_price,                                                                                               \n"+
            " cost_price,goods_img,ss_goods_info_status,pay_way,pay_status,totalprice                                                                                                 \n"+
            "  from                                                                                                                                                                   \n"+
            " ( select customer_id,username,phone,order_number1,order_date,                                                                                                           \n"+
            " receive_time,delevery_time,delivery_man_id,finish_time,order_status,                                                                                           \n"+
            " goods_id,num,price,amount,pay_way,pay_status,totalprice,stationId                                                                                                       \n"+
            " from                                                                                                                                                                    \n"+
            " ( select d.customer_id as customer_id ,                                                                                                                                 \n"+
            " c.username as username,c.phone as phone,d.order_number as order_number1,                                                                                                \n"+
            " d.order_date as order_date, d.receive_time as receive_time,                                                                                                             \n"+
            " d.delevery_time as delevery_time,d.delivery_man_id as delivery_man_id,d.finish_time as finish_time,                                                                                                          \n"+
            " d.order_status as order_status,pay_way,                                                                                            \n"+
            " pay_status,totalprice,stationId                                                                                                                                         \n"+
            " from                                                                                                                                                                    \n"+
            " (select a.customer_id as customer_id,a.order_number as order_number ,                                                                                                   \n"+
            " a.order_date as order_date, b.delivery_man_id as delivery_man_id,b.receive_time as receive_time,                                                                        \n"+
            " b.finish_time as finish_time, b.delevery_time as delevery_time,a.order_status as order_status,                                                      \n"+
            " pay_way,pay_status,totalprice,station_id as stationId                                                                                                                   \n"+
            "  from                                                                                                                                                                   \n"+
            "  (select customer_id,order_number,order_date as order_date,order_status,                                                                                                \n"+
            " pay_way,pay_status,total_price as totalprice,station_id from ss_waterstation_order_info ) a                                                                             \n"+
            " left join                                                                                                                                                               \n"+
            "  (select order_number,delivery_man_id,receive_time,finish_time,delevery_time from ss_order_delivery) b on a.order_number=b.order_number) d                      \n"+
            " left join (select * from sys_user) c on c.id=d.delivery_man_id ) f                                                                                               \n"+
            " LEFT JOIN (select order_number,goods_id,num,price,amount from ss_waterstation_order_detail) g on g.order_number = f.order_number1 ) h                                   \n"+
            " LEFT JOIN (select id,goods_name,goods_code,goods_type_id,ticket_id,ticket_num,unit,unit_price,cost_price,goods_img,status as ss_goods_info_status from ss_goods_info) j \n"+
            " on h.goods_id=j.id ) k                                                                                                                                                  \n"+
            " LEFT JOIN                                                                                                                                                               \n"+
            " (select id,station_name,leader_name as station_leader_name,phone as station_phone,address as station_address,status as station_status from ss_water_station) l          \n"+
            " on k.stationId=l.id ) m                                                                                                                                                 \n"+
            " LEFT JOIN                                                                                                                                                               \n"+
            " (select id,wx_openid,username as customer_name,phone as customer_phone,sex as customer_sex from sys_user) n                                                                \n"+
            " on n.id=m.customer_id ) o                                                                                                                                        \n"+
            "  ${ew.customSqlSegment} \n "
    )
    IPage<SsWaterstationOrderInfo> getAllSsWaterstationOrderInfoList(IPage<SsWaterstationOrderInfo> page, @Param(Constants.WRAPPER) Wrapper<SsWaterstationOrderInfo> queryWrapper);

    //订单基本信息查询
    @Select("  \n" +
            " select * from ( \n" +
            " select id,order_number,order_status,customer_id,total_price,description,order_date, \n" +
            " appoint_time,pay_status,pay_way,station_id,station_name,stationAddress from  \n" +
            " (select order_number,order_status,customer_id,total_price,description,order_date, \n" +
            " appoint_time,pay_status,pay_way,station_id  \n" +
            " from ss_waterstation_order_info) a \n" +
            " left join  \n" +
            "  (select id,station_name,address as stationAddress from ss_water_station) b \n" +
            "  on a.station_id =b.id) a \n" +
            "  ${ew.customSqlSegment} "
    )
    IPage<SsWaterstationOrderInfo> getSsWaterstationOrderInfoList(IPage<SsWaterstationOrderInfo> page, @Param(Constants.WRAPPER) Wrapper<SsWaterstationOrderInfo> queryWrapper);

    @Insert(" \n" +
            "insert into ss_waterstation_order_info(order_number,order_status,customer_id,total_price,description,order_date,appoint_time,pay_way,pay_status,station_id) \n" +
            " values \n" +
            "(#{orderNumber},#{orderStatus},#{customerId},#{totalPrice},#{description},#{orderDate},#{appointTime},#{payWay},#{payStatus},#{stationId})"
    )
    boolean saveOrderInfo(SsWaterstationOrderInfo ssOrderInfo);


    @Insert(" \n" +
            " insert into ss_waterstation_order_detail(order_number,goods_id,num,price,memo,amount) \n" +
            " values \n" +
            "(#{orderNumber},#{goodsId},#{num},#{price},#{memo},#{amount})"
    )
    boolean saveOrderDetail(SsWaterstationOrderDetail ssOrderDetail);

    @Select("  \n" +
            "  select * from ss_waterstation_price where goods_id=#{goodsId} and station_id=#{stationId} \n "

    )
    SsWaterstationPrice getSsWaterstationPrice(SsWaterstationPrice SsWaterstationPrice);

    @Select("  \n" +
            "  select * from ss_station_goods where goods_Id=#{goodsId} and station_Id=#{stationId} \n "

    )
    SsStationGoods getSsStationGoods(Integer goodsId,Integer stationId);

    //根据订单号，找对应订单基本信息
    @Select(
            " select * from ss_waterstation_order_info where order_number = #{orderNumber})"
    )
    SsWaterstationOrderInfo  getOrderInfoByOrderNumber(Integer orderNumber);

    //根据订单号，找对应订单详细信息
    @Select(
            " select * from ss_waterstation_order_detail where order_number= #{orderNumber})"
    )
    List<SsWaterstationOrderDetail> getOrderDetailByOrderNumber(Integer orderNumber);

    @Update(" update ss_waterstation_order_info set pay_status=#{payStatus},order_status=4  where order_number= #{orderNumber} and customer_Id=#{customerId}  \n ")
    boolean updateSsWaterstationOrderInfo(Integer orderNumber,String payStatus);

}
