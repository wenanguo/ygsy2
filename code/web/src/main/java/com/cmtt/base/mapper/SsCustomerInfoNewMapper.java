package com.cmtt.base.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cmtt.base.entity.SsCustomerInfo;
import com.cmtt.base.entity.SsCustomerInfoNew;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 客户信息拓展 Mapper 接口
 * </p>
 *
 * @author lin
 * @since 2021-01-28
 */
@Mapper
@Component(value = "ssCustomerInfoNewMapper")
public interface SsCustomerInfoNewMapper extends BaseMapper<SsCustomerInfoNew> {

    /**
     * 客户信息查询拓展
     * @return
     */
//    @Select("\n" +
////            " select id as id,customer_Id,customerName,sex,status,customerImg,phone,createTime,orderTotal,ticketTotal,moneyTotal,totalPoints from ( \n" +
////            " select a.id as id,a.customer_id as customer_Id,a.customer_name as customerName,a.sex as sex,a.status as status,a.customer_img as customerImg,a.phone as phone,a.create_time as createTime,b.orderTotal as orderTotal,c.ticketTotal as ticketTotal,e.moneyTotal as moneyTotal,f.totalPoints as totalPoints from \n" +
////            " (select id,phone as customer_id,icon as customer_img,username as customer_name,sex,phone,status,create_time from sys_user a  where ttype='1') a \n" +
////            " left join \n" +
////            " (select customer_id,sum(total_points)  as totalPoints from ss_payment_points GROUP BY customer_id) f \n" +
////            " on a.customer_id=f.customer_id \n" +
////            " left join \n" +
////            " (select customer_id,count(customer_id) as orderTotal from ss_order_info b GROUP BY customer_id) b \n" +
////            " on a.customer_id=b.customer_id \n" +
////            " left join \n" +
////            " (select customer_id,count(customer_id) as ticketTotal from ss_customer_ticket  GROUP BY customer_id) c \n" +
////            " on a.customer_id=c.customer_id \n" +
////            " left JOIN  \n" +
////            " ( \n" +
////            " select customer_id,sum(total) as moneyTotal from \n" +
////            "  (select a.order_number,a.total from \n" +
////            " (select order_number as order_number ,sum(amount) as total  from ss_order_detail GROUP BY order_number) a) d, \n" +
////            " (select customer_id,order_number  from ss_order_info) b  \n" +
////            " where d.order_number=b.order_number  \n" +
////            "  group by customer_id \n" +
////            "   ) e  \n" +
////            "  on a.customer_id=e.customer_id ) a \n" +
////            " ${ew.customSqlSegment} ")
    @Select(" \n"+
            " select * from ( \n" +
           " select id as id,customer_Id,customerName,sex,status,customerImg,phone, \n" +
            " createTime,orderTotal,ticketTotal,moneyTotal,totalPoints,addressId,address,stationId,station_name as stationName,waterStationAddress,haveTicket \n" +
            " from \n" +
            " ( select a.id as id,a.customer_id as customer_Id,a.customer_name as customerName,a.sex as sex,a.status as status,a.customer_img as customerImg, \n" +
            " a.phone as phone,a.create_time as createTime,b.orderTotal as orderTotal,c.ticketTotal as ticketTotal,b.moneyTotal as moneyTotal,f.totalPoints as totalPoints,d.id as addressId, d.address as address,d.station_id as stationId,haveTicket  \n" +
            " from  \n" +
            " (select id,id as customer_id,icon as customer_img,username as customer_name,sex,phone,status,create_time from sys_user a) a  \n" +
            "  left join  \n" +
            "  (select customer_id,sum(total_points) as totalPoints from ss_payment_points GROUP BY customer_id) f on a.customer_id=f.customer_id  \n" +
            "  left join \n" +
            "  (select customer_id,count(customer_id) as orderTotal,sum(total_price) as moneyTotal from ss_order_info b GROUP BY customer_id) b on a.customer_id=b.customer_id  \n" +
            " left join \n" +
            " (select customer_id,count(customer_id) as ticketTotal from ss_customer_ticket GROUP BY customer_id) c on a.customer_id=c.customer_id  \n" +
            "  LEFT JOIN \n" +
            "  (select id, customer_id,address,station_id from ss_customer_address) d on d.customer_id = a.customer_id" +
            " Left JOIN \n" +
            "  (select customer_id,count(customer_id) as haveTicket from ss_customer_ticket where use_status='1' GROUP BY customer_id) e on a.customer_id=e.customer_id \n" +
            ") a \n" +
            " left join \n" +
            " (select id as waterStationId,station_name,address as waterStationAddress from ss_water_station) B  \n" +
            " on a.stationId = B.waterStationId ) d \n" +
            "   ${ew.customSqlSegment}  "
    )
    IPage<SsCustomerInfoNew> getAllSsCustomerInfoNewList(IPage<SsCustomerInfoNew> page, @Param(Constants.WRAPPER) Wrapper<SsCustomerInfoNew> queryWrapper);



}
