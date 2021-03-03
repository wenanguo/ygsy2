package com.cmtt.base.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cmtt.base.entity.SsCustomerTicket;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 用户购买水票信息 Mapper 接口
 * </p>
 *
 * @author lin
 * @since 2021-01-29
 */
@Mapper
@Component(value = "ssCustomerTicketMapper")
public interface SsCustomerTicketMapper extends BaseMapper<SsCustomerTicket> {

    @Select("\n"+
    " select a.id as id,b.ticket_img,a.customer_id as customer_id ,a.use_status as use_status ,a.ticket_code as ticket_code ,b.ticket_name as ticket_name,b.ticket_type as ticket_type,a.use_time,a.end_time from  \n" +
            " (select * from ss_customer_ticket)  a  \n" +
            " JOIN \n" +
            " (select * from ss_water_ticket)  b  \n" +
            " on a.ticket_id=b.id \n" +
            " ${ew.customSqlSegment}  "
    )
    IPage<SsCustomerTicket> getAllSsCustomerTicketList(IPage<SsCustomerTicket> page, @Param(Constants.WRAPPER) Wrapper<SsCustomerTicket> queryWrapper);

}
