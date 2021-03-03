package com.cmtt.base.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cmtt.base.entity.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tencentcloudapi.tcb.v20180608.models.OrderInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单信息 Mapper 接口
 * </p>
 *
 * @author lin
 * @since 2021-01-30
 */
@Mapper
@Component(value = "ssOrderInfoMapper")
public interface SsOrderInfoMapper extends BaseMapper<SsOrderInfo> {
//    @Select(" \n" +
//            " select * from ( \n" +
//            " select d.customer_id,c.username,c.phone,c.station_id,d.order_number,d.order_date,d.receive_time,d.delevery_time,d.finish_time,d.status from \n" +
//            " (select a.customer_id as customer_id,a.order_number as order_number ,a.order_date as order_date, \n" +
//            " b.delivery_man_id as delivery_man_id,b.receive_time as receive_time,b.finish_time as finish_time, \n" +
//            " b.delevery_time as delevery_time,b.status as status from \n" +
//            "  (select customer_id,order_number,max(order_date) as order_date from ss_order_info GROUP BY customer_id) a \n" +
//            "  left JOIN \n" +
//            "  (select order_number,delivery_man_id,receive_time,finish_time,delevery_time,status from ss_order_delivery) b \n" +
//            "  on a.order_number=b.order_number) d \n" +
//            " left join \n" +
//            " (select * from sys_user where ttype='3') c \n" +
//            " on \n" +
//            " c.phone=d.delivery_man_id ) f where 1=1 \n" +
//            " and customer_id=#{openId}"
//    )
//@Select("  select  order_number as id,order_number,ss_customer_contacts_address,ss_customer_address_contacts,ss_customer_address_phone,ss_customer_address_status, \n" +
//        "  customer_name,customer_phone,customer_sex,delevery_username,delevery_phone, \n" +
//        "  max(order_date) as order_date,receive_time,delevery_time,finish_time,\n" +
//        "  ss_order_detail_num,ss_order_detail_price,pay_way,amount,goods_name,goods_code,goods_type_id,ticket_id,ticket_num, \n" +
//        "  unit,unit_price,cost_price,goods_img,ss_goods_info_status,  \n" +
//        "  customer_id,goods_id,address_id,station_id,order_status,station_name,station_leader_name,station_phone,station_address,station_status from  \n" +
//        "  ( \n" +
//        "  select order_number,customer_name,customer_phone,customer_sex,delevery_username,delevery_phone, \n" +
//        "  order_date,receive_time,delevery_time,finish_time, \n" +
//        "  order_status,num as ss_order_detail_num,price as ss_order_detail_price,pay_way,amount,goods_name,goods_code,goods_type_id,ticket_id,ticket_num,unit,unit_price,cost_price,goods_img,ss_goods_info_status, \n" +
//        "  customer_id,goods_id,address_id,station_id,station_name,station_leader_name,station_phone,station_address,station_status from  \n" +
//        "  ( \n" +
//        "  select customer_id,delevery_username,delevery_phone,station_id,order_number,order_date,receive_time,delevery_time,finish_time,order_status,\n" +
//        "  goods_id,num,price,pay_way,amount,goods_name,goods_code,goods_type_id,ticket_id,ticket_num,unit,unit_price,cost_price,goods_img,ss_goods_info_status,address_id \n" +
//        "  ,station_name,station_leader_name,station_phone,station_address,station_status from \n" +
//        "  ( \n" +
//        "  select customer_id,username as delevery_username,phone as delevery_phone,order_number,order_date,receive_time,delevery_time,finish_time,order_status, \n" +
//        "  goods_id,num,price,pay_way,amount,goods_name,goods_code,goods_type_id,ticket_id,ticket_num,unit,unit_price,cost_price,goods_img,ss_goods_info_status,address_id,station_id1 as station_id from  \n" +
//        "  ( \n" +
//        "  select customer_id,username,phone,station_id1,order_number,order_date,receive_time,delevery_time,finish_time,order_status, \n" +
//        "  goods_id,num,price,pay_way,amount,address_id \n" +
//        "  from \n" +
//        "  ( \n" +
//        "  select d.customer_id as customer_id ,c.username as username,c.phone as phone,station_id1 ,d.order_number as order_number1,d.order_date as order_date, \n" +
//        "  d.receive_time as receive_time,d.delevery_time as delevery_time,d.finish_time as finish_time,d.order_status as order_status  \n" +
//        "  from \n" +
//        "  (select a.customer_id as customer_id,a.order_number as order_number , \n" +
//        "  a.order_date as order_date, b.delivery_man_id as delivery_man_id,b.receive_time as receive_time,b.finish_time as finish_time,  \n" +
//        "  b.delevery_time as delevery_time ,a.order_status as order_status,station_id1 \n" +
//        "  from  \n" +
//        "  (select customer_id,order_number,order_date as order_date,order_status,station_id as station_id1  from  \n" +
//        "  ss_order_info ) a \n" +
//        "  left join  \n" +
//        "  (select order_number,delivery_man_id,receive_time,finish_time,delevery_time \n" +
//        "   from ss_order_delivery) b on a.order_number=b.order_number) d \n" +
//        "  left join (select * from sys_user) c  \n" +
//        "  on  \n" +
//        "  c.wx_openid=d.delivery_man_id \n" +
//        "  ) f \n" +
//        "  LEFT JOIN  \n" +
//        "  (select order_number,goods_id,address_id,num,price,pay_way,amount from ss_order_detail) g \n" +
//        "  on g.order_number = f.order_number1 \n" +
//        "  ) h \n" +
//        "  LEFT JOIN \n" +
//        "  (select id,goods_name,goods_code,goods_type_id,ticket_id,ticket_num,unit,unit_price,cost_price,goods_img,status as ss_goods_info_status from ss_goods_info) j \n" +
//        "  on h.goods_id=j.id \n" +
//        "  ) k \n" +
//        "  LEFT JOIN \n" +
//        "  (select id,station_name,leader_name as station_leader_name,phone as station_phone,address as station_address,status as station_status  from ss_water_station) l \n" +
//        "  on k.station_id=l.id  \n" +
//        "  ) m \n" +
//        "  LEFT JOIN \n" +
//        "  (select wx_openid,username as customer_name,phone as customer_phone,sex as customer_sex from sys_user) n \n" +
//        "  on n.wx_openid=m.customer_id \n" +
//        "  ) o \n" +
//        "  left JOIN \n" +
//        "  (select id,address as ss_customer_contacts_address,contacts as ss_customer_address_contacts,phone as ss_customer_address_phone,status as ss_customer_address_status from ss_customer_address) p \n" +
//        "  on p.id=o.address_id \n" +
//        "  where customer_id=#{openId} \n" +
//        " group by customer_id \n"
//)

    //获取订单关联全部详情-将order_info里的水站station_id放到
    @Select("  select  order_number as id,order_number,ss_customer_contacts_address,ss_customer_address_contacts,ss_customer_address_phone,ss_customer_address_status, \n" +
            "  customer_name,customer_phone,customer_sex,delevery_username,delevery_phone, \n" +
            "  max(order_date) as order_date,receive_time,delevery_time,finish_time, \n" +
            "  ss_order_detail_num,ss_order_detail_price,amount,goods_name,goods_code,goods_type_id,ticket_id,ticket_num, \n" +
            "  unit,unit_price,cost_price,goods_img,ss_goods_info_status,  \n" +
            "  customer_id,goods_id,address_id,station_id,order_status,station_name,station_leader_name,station_phone,station_address,station_status,pay_way,pay_status,totalprice from  \n" +
            "  ( \n" +
            "  select order_number,customer_name,customer_phone,customer_sex,delevery_username,delevery_phone, \n" +
            "  order_date,receive_time,delevery_time,finish_time, \n" +
            "  order_status,num as ss_order_detail_num,price as ss_order_detail_price,amount,goods_name,goods_code,goods_type_id,ticket_id,ticket_num,unit,unit_price,cost_price,goods_img,ss_goods_info_status, \n" +
            "  customer_id,goods_id,address_id,station_id,station_name,station_leader_name,station_phone,station_address,station_status,pay_way,pay_status,totalprice from  \n" +
            "  ( \n" +
            "  select customer_id,delevery_username,delevery_phone,station_id,order_number,order_date,receive_time,delevery_time,finish_time,order_status,\n" +
            "  goods_id,num,price,pay_way,amount,goods_name,goods_code,goods_type_id,ticket_id,ticket_num,unit,unit_price,cost_price,goods_img,ss_goods_info_status,address_id \n" +
            "  ,station_name,station_leader_name,station_phone,station_address,station_status,pay_status,totalprice from \n" +
            "  ( \n" +
            "  select station_id,customer_id,username as delevery_username,phone as delevery_phone,order_number,order_date,receive_time,delevery_time,finish_time,order_status, \n" +
            "  goods_id,num,price,amount,goods_name,goods_code,goods_type_id,ticket_id,ticket_num,unit,unit_price,cost_price,goods_img,ss_goods_info_status,address_id,pay_way,pay_status,totalprice from  \n" +
            "  ( \n" +
            "  select customer_id,username,phone,station_id,order_number,order_date,receive_time,delevery_time,finish_time,order_status, \n" +
            "  goods_id,num,price,amount,address_id,pay_way,pay_status,totalprice \n" +
            "  from \n" +
            "  ( \n" +
            "  select d.customer_id as customer_id ,c.username as username,c.phone as phone,d.order_number as order_number1,d.order_date as order_date, \n" +
            "  d.receive_time as receive_time,d.delevery_time as delevery_time,d.finish_time as finish_time,d.order_status as order_status,pay_way,pay_status,totalprice  \n" +
            "  from \n" +
            "  (select a.customer_id as customer_id,a.order_number as order_number , \n" +
            "  a.order_date as order_date, b.delivery_man_id as delivery_man_id,b.receive_time as receive_time,b.finish_time as finish_time,  \n" +
            "  b.delevery_time as delevery_time,a.order_status as order_status,pay_way,pay_status,totalprice \n" +
            "  from  \n" +
            "  (select customer_id,order_number,order_date as order_date,order_status,pay_way,pay_status,total_price as totalprice  from  \n" +
            "  ss_order_info ) a \n" +
            "  left join  \n" +
            "  (select order_number,delivery_man_id,receive_time,finish_time,delevery_time \n" +
            "   from ss_order_delivery) b on a.order_number=b.order_number) d \n" +
            "  left join (select * from sys_user) c  \n" +
            "  on  \n" +
            "  c.phone=d.delivery_man_id \n" +
            "  ) f \n" +
            "  LEFT JOIN  \n" +
            "  (select order_number,goods_id,address_id,num,price,amount,station_id from ss_order_detail) g \n" +
            "  on g.order_number = f.order_number1 \n" +
            "  ) h \n" +
            "  LEFT JOIN \n" +
            "  (select id,goods_name,goods_code,goods_type_id,ticket_id,ticket_num,unit,unit_price,cost_price,goods_img,status as ss_goods_info_status from ss_goods_info) j \n" +
            "  on h.goods_id=j.id \n" +
            "  ) k \n" +
            "  LEFT JOIN \n" +
            "  (select id,station_name,leader_name as station_leader_name,phone as station_phone,address as station_address,status as station_status  from ss_water_station) l \n" +
            "  on k.station_id=l.id  \n" +
            "  ) m \n" +
            "  LEFT JOIN \n" +
            "  (select wx_openid,username as customer_name,phone as customer_phone,sex as customer_sex from sys_user) n \n" +
            "  on n.customer_phone=m.customer_id \n" +
            "  ) o \n" +
            "  left JOIN \n" +
            "  (select id,address as ss_customer_contacts_address,contacts as ss_customer_address_contacts,phone as ss_customer_address_phone,status as ss_customer_address_status from ss_customer_address) p \n" +
            "  on p.id=o.address_id \n" +
            "  where customer_id=#{openId} \n" +
            " group by customer_id \n"
    )
    List<Map<Integer, String>> getLastOrder(Integer status, String openId);

    //获取订单关联全部详情
//    @Select("  select  order_number as id,order_number,ss_customer_contacts_address,ss_customer_address_contacts,ss_customer_address_phone,ss_customer_address_status, \n" +
//            "  customer_name,customer_phone,customer_sex,delevery_username,delevery_phone, \n" +
//            "  order_date,receive_time,delevery_time,finish_time,ss_order_delivery_status, \n" +
//            "  ss_order_detail_num,ss_order_detail_price,pay_way,amount,goods_name,goods_code,goods_type_id,ticket_id,ticket_num, \n" +
//            "  unit,unit_price,cost_price,goods_img,ss_goods_info_status,  \n" +
//            "  customer_id,goods_id,address_id,station_id,order_status,station_name,station_leader_name,station_phone,station_address,station_status,totalprice from  \n" +
//            "  ( \n" +
//            "  select order_number,customer_name,customer_phone,customer_sex,delevery_username,delevery_phone, \n" +
//            "  order_date,receive_time,delevery_time,finish_time,ss_order_delivery_status, \n" +
//            "  order_status,num as ss_order_detail_num,price as ss_order_detail_price,pay_way,amount,goods_name,goods_code,goods_type_id,ticket_id,ticket_num,unit,unit_price,cost_price,goods_img,ss_goods_info_status, \n" +
//            "  customer_id,goods_id,address_id,station_id,station_name,station_leader_name,station_phone,station_address,station_status,totalprice from  \n" +
//            "  ( \n" +
//            "  select customer_id,delevery_username,delevery_phone,station_id,order_number,order_date,receive_time,delevery_time,finish_time,ss_order_delivery_status,order_status,\n" +
//            "  goods_id,num,price,pay_way,amount,goods_name,goods_code,goods_type_id,ticket_id,ticket_num,unit,unit_price,cost_price,goods_img,ss_goods_info_status,address_id \n" +
//            "  ,station_name,station_leader_name,station_phone,station_address,station_status,totalprice from \n" +
//            "  ( \n" +
//            "  select customer_id,username as delevery_username,phone as delevery_phone,order_number,order_date,receive_time,delevery_time,finish_time,ss_order_delivery_status,order_status, \n" +
//            "  goods_id,num,price,pay_way,amount,goods_name,goods_code,goods_type_id,ticket_id,ticket_num,unit,unit_price,cost_price,goods_img,ss_goods_info_status,address_id,station_id1 as station_id ,totalprice from  \n" +
//            "  ( \n" +
//            "  select customer_id,username,phone,station_id1,order_number,order_date,receive_time,delevery_time,finish_time,ss_order_delivery_status,order_status, \n" +
//            "  goods_id,num,price,pay_way,amount,address_id,totalprice \n" +
//            "  from \n" +
//            "  ( \n" +
//            "  select d.customer_id as customer_id ,c.username as username,c.phone as phone,station_id1 ,d.order_number as order_number1,d.order_date as order_date, \n" +
//            "  d.receive_time as receive_time,d.delevery_time as delevery_time,d.finish_time as finish_time,d.status as ss_order_delivery_status,d.order_status as order_status,totalprice  \n" +
//            "  from \n" +
//            "  (select a.customer_id as customer_id,a.order_number as order_number , \n" +
//            "  a.order_date as order_date, b.delivery_man_id as delivery_man_id,b.receive_time as receive_time,b.finish_time as finish_time,  \n" +
//            "  b.delevery_time as delevery_time,b.status as status ,a.order_status as order_status,station_id1,totalprice \n" +
//            "  from  \n" +
//            "  (select customer_id,order_number,order_date as order_date,order_status,station_id as station_id1,total_price as totalprice  from  \n" +
//            "  ss_order_info ) a \n" +
//            "  left join  \n" +
//            "  (select order_number,delivery_man_id,receive_time,finish_time,delevery_time, \n" +
//            "  status from ss_order_delivery) b on a.order_number=b.order_number) d \n" +
//            "  left join (select * from sys_user) c  \n" +
//            "  on  \n" +
//            "  c.wx_openid=d.delivery_man_id \n" +
//            "  ) f \n" +
//            "  LEFT JOIN  \n" +
//            "  (select order_number,goods_id,address_id,num,price,pay_way,amount from ss_order_detail) g \n" +
//            "  on g.order_number = f.order_number1 \n" +
//            "  ) h \n" +
//            "  LEFT JOIN \n" +
//            "  (select id,goods_name,goods_code,goods_type_id,ticket_id,ticket_num,unit,unit_price,cost_price,goods_img,status as ss_goods_info_status from ss_goods_info) j \n" +
//            "  on h.goods_id=j.id \n" +
//            "  ) k \n" +
//            "  LEFT JOIN \n" +
//            "  (select id,station_name,leader_name as station_leader_name,phone as station_phone,address as station_address,status as station_status  from ss_water_station) l \n" +
//            "  on k.station_id=l.id  \n" +
//            "  ) m \n" +
//            "  LEFT JOIN \n" +
//            "  (select wx_openid,username as customer_name,phone as customer_phone,sex as customer_sex from sys_user) n \n" +
//            "  on n.wx_openid=m.customer_id \n" +
//            "  ) o \n" +
//            "  left JOIN \n" +
//            "  (select id,address as ss_customer_contacts_address,contacts as ss_customer_address_contacts,phone as ss_customer_address_phone,status as ss_customer_address_status from ss_customer_address) p \n" +
//            "  on p.id=o.address_id \n" +
//            "  ${ew.customSqlSegment} \n"
//    ) -- 老数据库查询方式，水站id在order_info表里
    //获取订单关联全部详情-将order_info里的水站station_id放到
    @Select("  select  order_number as id,order_number,ss_customer_contacts_address,ss_customer_address_contacts,ss_customer_address_phone,ss_customer_address_status, \n" +
            "  customer_name,customer_phone,customer_sex,delevery_username,delevery_phone, \n" +
            "  order_date,receive_time,delivery_man_id,delevery_time,finish_time, \n" +
            "  ss_order_detail_num,ss_order_detail_price,amount,goods_name,goods_code,goods_type_id,ticket_id,ticket_num, \n" +
            "  unit,unit_price,cost_price,goods_img,ss_goods_info_status,  \n" +
            "  customer_id,goods_id,address_id,station_id,order_status,station_name,station_leader_name,station_phone,station_address,station_status,pay_way,pay_status,totalprice from  \n" +
            "  ( \n" +
            "  select order_number,customer_name,customer_phone,customer_sex,delevery_username,delevery_phone, \n" +
            "  order_date,receive_time,delevery_time,delivery_man_id,finish_time, \n" +
            "  order_status,num as ss_order_detail_num,price as ss_order_detail_price,amount,goods_name,goods_code,goods_type_id,ticket_id,ticket_num,unit,unit_price,cost_price,goods_img,ss_goods_info_status, \n" +
            "  customer_id,goods_id,address_id,station_id,station_name,station_leader_name,station_phone,station_address,station_status,pay_way,pay_status,totalprice from  \n" +
            "  ( \n" +
            "  select customer_id,delevery_username,delevery_phone,station_id,order_number,order_date,receive_time,delivery_man_id,delevery_time,finish_time,order_status,\n" +
            "  goods_id,num,price,pay_way,amount,goods_name,goods_code,goods_type_id,ticket_id,ticket_num,unit,unit_price,cost_price,goods_img,ss_goods_info_status,address_id \n" +
            "  ,station_name,station_leader_name,station_phone,station_address,station_status,pay_status,totalprice from \n" +
            "  ( \n" +
            "  select station_id,customer_id,username as delevery_username,phone as delevery_phone,order_number,order_date,receive_time,delivery_man_id,delevery_time,finish_time,order_status, \n" +
            "  goods_id,num,price,amount,goods_name,goods_code,goods_type_id,ticket_id,ticket_num,unit,unit_price,cost_price,goods_img,ss_goods_info_status,address_id,pay_way,pay_status,totalprice from  \n" +
            "  ( \n" +
            "  select customer_id,username,phone,station_id,order_number,order_date,receive_time,delivery_man_id,delevery_time,finish_time,order_status, \n" +
            "  goods_id,num,price,amount,address_id,pay_way,pay_status,totalprice \n" +
            "  from \n" +
            "  ( \n" +
            "  select d.customer_id as customer_id ,c.username as username,c.phone as phone,d.order_number as order_number1,d.order_date as order_date, \n" +
            "  d.receive_time as receive_time,delivery_man_id,delevery_time as delevery_time,d.finish_time as finish_time,d.order_status as order_status,pay_way,pay_status,totalprice  \n" +
            "  from \n" +
            "  (select a.customer_id as customer_id,a.order_number as order_number , \n" +
            "  a.order_date as order_date, b.delivery_man_id as delivery_man_id,b.receive_time as receive_time,b.finish_time as finish_time,  \n" +
            "  b.delevery_time as delevery_time,a.order_status as order_status,pay_way,pay_status,totalprice \n" +
            "  from  \n" +
            "  (select customer_id,order_number,order_date as order_date,order_status,pay_way,pay_status,total_price as totalprice  from  \n" +
            "  ss_order_info ) a \n" +
            "  left join  \n" +
            "  (select order_number,delivery_man_id,receive_time,finish_time,delevery_time \n" +
            "   from ss_order_delivery) b on a.order_number=b.order_number) d \n" +
            "  left join (select * from sys_user) c  \n" +
            "  on  \n" +
            "  c.id=d.delivery_man_id \n" +
            "  ) f \n" +
            "  LEFT JOIN  \n" +
            "  (select order_number,goods_id,address_id,num,price,amount,station_id from ss_order_detail) g \n" +
            "  on g.order_number = f.order_number1 \n" +
            "  ) h \n" +
            "  LEFT JOIN \n" +
            "  (select id,goods_name,goods_code,goods_type_id,ticket_id,ticket_num,unit,unit_price,cost_price,goods_img,status as ss_goods_info_status from ss_goods_info) j \n" +
            "  on h.goods_id=j.id \n" +
            "  ) k \n" +
            "  LEFT JOIN \n" +
            "  (select id,station_name,leader_name as station_leader_name,phone as station_phone,address as station_address,status as station_status  from ss_water_station) l \n" +
            "  on k.station_id=l.id  \n" +
            "  ) m \n" +
            "  LEFT JOIN \n" +
            "  (select id,wx_openid,username as customer_name,phone as customer_phone,sex as customer_sex from sys_user) n \n" +
            "  on n.id=m.customer_id \n" +
            "  ) o \n" +
            "  left JOIN \n" +
            "  (select id,address as ss_customer_contacts_address,contacts as ss_customer_address_contacts,phone as ss_customer_address_phone,status as ss_customer_address_status from ss_customer_address) p \n" +
            "  on p.id=o.address_id \n" +
            "  ${ew.customSqlSegment} \n"
    )
    IPage<SsOrderInfo> getAllOrderDetailList(IPage<SsOrderInfo> page, @Param(Constants.WRAPPER) Wrapper<SsOrderInfo> queryWrapper);


    @Select(" \n" +
            "select goods_type_id,ticket_num,goods_code,ticket_id from ss_goods_info where \n" +
            " id=#{goodsId} \n"
    )
    SsGoodsInfo getTicketOrNot(Integer goodsId);

    @Insert(" \n" +
            "insert into ss_order_info(order_number,order_status,customer_id,total_price,description,order_date,appoint_time,pay_way,pay_status) \n" +
            " values \n" +
            "(#{orderNumber},#{orderStatus},#{customerId},#{totalPrice},#{description},#{orderDate},#{appointTime},#{payWay},#{payStatus})"
    )
    boolean saveOrderInfo(SsOrderInfo ssOrderInfo);


    @Insert(" \n" +
            " insert into ss_order_detail(order_number,goods_id,num,price,memo,amount,address_id,station_id) \n" +
            " values \n" +
            "(#{orderNumber},#{goodsId},#{num},#{price},#{memo},#{amount},#{addressId},#{stationId})"
    )
    boolean saveOrderDetail(SsOrderDetail ssOrderDetail);

    @Insert(" \n" +
            " insert into ss_customer_ticket(customer_id,ticket_id,ticket_code,use_status,use_time,create_time)  \n" +
            " values \n" +
            "(#{customerId},#{ticketId},#{ticketCode},#{useStatus},#{useTime},#{createTime})"
    )
    boolean saveCustomerTicket(SsCustomerTicket ssCustomerTicket);


    @Insert(" \n" +
            " insert into ss_order_detail(order_number,goods_id,num,price,pay_way,memo,amount,address_id) \n" +
            " values \n" +
            "(#{orderNumber},#{goodsId},#{num},#{price},#{payWay},#{memo},#{amount},#{addressId})"
    )
    boolean saveOpinionById(SsOpinions ssOpinions);


    //根据用户订单获取用户地址，根据用户地址找到绑定的水站
    @Select(" \n" +
              "select * from ss_customer_address where id =" +
              "(select address_id from ss_order_detail where order_number = #{orderNumber})"
    )
    SsCustomerAddress getWaterStationByOrderNumber(Integer orderNumber);

    //根据订单号，找对应订单基本信息
    @Select(
            " select * from ss_order_info where order_number = #{orderNumber})"
    )
    SsOrderInfo  getOrderInfoByOrderNumber(Integer orderNumber);

    //根据订单号，找对应订单详细信息
    @Select(
            " select * from ss_order_detail where order_number= #{orderNumber})"
    )
    List<SsOrderDetail>  getOrderDetailByOrderNumber(Integer orderNumber);

    //根据订单号，找对应商品信息
    @Select(
            "select * from ss_goods_info where id in (\n" +
                    "select goods_id from ss_order_detail where order_number=#{orderNumber}) "
    )
    SsGoodsInfo  getSsGoodsInfoByOrderNumber(Integer orderNumber);

    //根据水站配送员登录获取已支付完成待派送的订单信息
    @Select(" \n" +
            "select * from ss_order_detail where station_id=#{stationId} and order_number in \n" +
            " ( \n" +
            " select order_number from ss_order_info where order_status=1 and pay_status=203 " +
            ") "
    )
    List<SsOrderInfo> getOrderInfoByStationId(Integer stationId);

    //根据水站配送员登录获取已支付完成待派送的订单信息
    @Select("  select  order_number as id,order_number,ss_customer_contacts_address,ss_customer_address_contacts,ss_customer_address_phone,ss_customer_address_status, \n" +
            "  customer_name,customer_phone,customer_sex,delevery_username,delevery_phone, \n" +
            "  order_date,receive_time,delevery_time,finish_time, \n" +
            "  ss_order_detail_num,ss_order_detail_price,amount,goods_name,goods_code,goods_type_id,ticket_id,ticket_num, \n" +
            "  unit,unit_price,cost_price,goods_img,ss_goods_info_status,  \n" +
            "  customer_id,goods_id,address_id,station_id,order_status,station_name,station_leader_name,station_phone,station_address,station_status,pay_way,pay_status,totalprice,house_number from  \n" +
            "  ( \n" +
            "  select order_number,customer_name,customer_phone,customer_sex,delevery_username,delevery_phone, \n" +
            "  order_date,receive_time,delevery_time,finish_time, \n" +
            "  order_status,num as ss_order_detail_num,price as ss_order_detail_price,amount,goods_name,goods_code,goods_type_id,ticket_id,ticket_num,unit,unit_price,cost_price,goods_img,ss_goods_info_status, \n" +
            "  customer_id,goods_id,address_id,station_id,station_name,station_leader_name,station_phone,station_address,station_status,pay_way,pay_status,totalprice from  \n" +
            "  ( \n" +
            "  select customer_id,delevery_username,delevery_phone,station_id,order_number,order_date,receive_time,delevery_time,finish_time,order_status,\n" +
            "  goods_id,num,price,pay_way,amount,goods_name,goods_code,goods_type_id,ticket_id,ticket_num,unit,unit_price,cost_price,goods_img,ss_goods_info_status,address_id \n" +
            "  ,station_name,station_leader_name,station_phone,station_address,station_status,pay_status,totalprice from \n" +
            "  ( \n" +
            "  select station_id,customer_id,username as delevery_username,phone as delevery_phone,order_number,order_date,receive_time,delevery_time,finish_time,order_status, \n" +
            "  goods_id,num,price,amount,goods_name,goods_code,goods_type_id,ticket_id,ticket_num,unit,unit_price,cost_price,goods_img,ss_goods_info_status,address_id,pay_way,pay_status,totalprice from  \n" +
            "  ( \n" +
            "  select customer_id,username,phone,station_id,order_number,order_date,receive_time,delevery_time,finish_time,order_status, \n" +
            "  goods_id,num,price,amount,address_id,pay_way,pay_status,totalprice \n" +
            "  from \n" +
            "  ( \n" +
            "  select d.customer_id as customer_id ,c.username as username,c.phone as phone,d.order_number as order_number1,d.order_date as order_date, \n" +
            "  d.receive_time as receive_time,d.delevery_time as delevery_time,d.finish_time as finish_time,d.order_status as order_status,pay_way,pay_status,totalprice  \n" +
            "  from \n" +
            "  (select a.customer_id as customer_id,a.order_number as order_number , \n" +
            "  a.order_date as order_date, b.delivery_man_id as delivery_man_id,b.receive_time as receive_time,b.finish_time as finish_time,  \n" +
            "  b.delevery_time as delevery_time ,a.order_status as order_status,pay_way,pay_status,totalprice \n" +
            "  from  \n" +
            "  (select customer_id,order_number,order_date as order_date,order_status,pay_way,pay_status,total_price as totalprice  from  \n" +
            "  ss_order_info ) a \n" +
            "  left join  \n" +
            "  (select order_number,delivery_man_id,receive_time,finish_time,delevery_time \n" +
            "   from ss_order_delivery) b on a.order_number=b.order_number) d \n" +
            "  left join (select * from sys_user) c  \n" +
            "  on  \n" +
            "  c.id=d.delivery_man_id \n" +
            "  ) f \n" +
            "  LEFT JOIN  \n" +
            "  (select order_number,goods_id,address_id,num,price,amount,station_id from ss_order_detail) g \n" +
            "  on g.order_number = f.order_number1 \n" +
            "  ) h \n" +
            "  LEFT JOIN \n" +
            "  (select id,goods_name,goods_code,goods_type_id,ticket_id,ticket_num,unit,unit_price,cost_price,goods_img,status as ss_goods_info_status from ss_goods_info) j \n" +
            "  on h.goods_id=j.id \n" +
            "  ) k \n" +
            "  LEFT JOIN \n" +
            "  (select id,station_name,leader_name as station_leader_name,phone as station_phone,address as station_address,status as station_status  from ss_water_station) l \n" +
            "  on k.station_id=l.id  \n" +
            "  ) m \n" +
            "  LEFT JOIN \n" +
            "  (select id,wx_openid,username as customer_name,phone as customer_phone,sex as customer_sex from sys_user) n \n" +
            "  on n.id=m.customer_id \n" +
            "  ) o \n" +
            "  left JOIN \n" +
            "  (select id,address as ss_customer_contacts_address,contacts as ss_customer_address_contacts,phone as ss_customer_address_phone,status as ss_customer_address_status,house_number from ss_customer_address) p \n" +
            "  on p.id=o.address_id \n" +
            "  ${ew.customSqlSegment} \n"
    )
    IPage<SsOrderInfo> getOrderInfoByStationIdPage(IPage<SsOrderInfo> page, @Param(Constants.WRAPPER) Wrapper<SsOrderInfo> queryWrapper);

    @Update(" \n" +
    " update ss_order_info set order_status=#{orderStatus}  where order_number=#{orderNum}"
    )
    boolean updateOrderInfoByOrderNum(String orderNum,String status);

    @Update(" \n" +
            " update ss_order_delivery set status=#{status}  where order_number=#{orderNum}"
    )
    boolean updateByOrderNumForDeliver(Integer orderNum);

    @Insert(" \n" +
            " insert into ss_order_delivery (order_number,delivery_man_id,receive_time,status) \n" +
            "  values \n" +
            "(#{orderNum},#{delivery_man_id},#{receive_time},#{status})"
    )
    boolean insertByOrderNumForDeliver(SsOrderDelivery ssOrderDelivery);


    @Select(" select * from ss_goods_info where status=\"100\"  and id = #{goodsId} \n")
    SsGoodsInfo  getSsGoodsInfoByGoodsId(Integer goodsId);

    @Select(" select * from ss_water_ticket where id=#{ticketId} and status=\"100\" ")
    SsWaterTicket getWaterTicketByTicketId(Integer ticketId);

    @Select(" select * from ss_water_ticket where ticket_value=#{ticketValue} and status=\"100\" ")
    List<SsWaterTicket> getWaterTicketByTicketValue(double ticketValue);

    @Select(" select * from ss_customer_ticket where ticket_id =#{ticketId}  and use_status='1' and customer_Id=#{customerId}\n"
            )
    List<SsCustomerTicket> getWaterTicketTotalByTicketId(Integer ticketId,Integer customerId);

    @Select(" \n" +
            " select ticket_id,customerId,ticket_code,use_status,use_time,end_time,ticket_value from (  \n" +
            " select a.ticket_id as ticket_id,a.customer_Id as customerId,a.ticket_code as ticket_code,a.use_status as use_status,a.use_time as use_time,a.end_time as end_time,b.ticket_value as ticket_value from  \n" +
            "  (select ticket_id,customer_Id,ticket_code,use_status,use_time,end_time from ss_customer_ticket where use_status='1' and customer_Id=#{customerId} ) a  \n" +
            "  left join (select id,ticket_value from ss_water_ticket where ticket_value=#{ticketValue}) b on b.id=a.ticket_id ) c  \n" +
            " where ticket_value=#{ticketValue} "
    )
    List<SsCustomerTicket> getWaterTicketTotalByTicketValue(double ticketValue,Integer customerId);

    @Update(" update ss_customer_ticket set use_status=2  where ticket_id=#{ticketId} and customer_Id=#{customerId} and ticket_code= #{ticketCode} \n ")
    boolean updateCustomerTicketByTicket(SsCustomerTicket ssCustomerTicket);
}
