package com.cmtt.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cmtt.base.entity.SsCustomerInfo;
import com.cmtt.base.entity.SsCustomerInfoNew;
import com.cmtt.base.mapper.SqlMapper;
import com.cmtt.base.mapper.SsCustomerInfoMapper;
import com.cmtt.base.mapper.SsCustomerInfoNewMapper;
import com.cmtt.base.service.ISsCustomerInfoNewService;
import com.cmtt.base.service.ISsCustomerInfoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 客户信息 服务实现类
 * </p>
 *
 * @author lin
 * @since 2021-01-28
 */
@Service
public class SsCustomerInfoNewServiceImpl extends ServiceImpl<SsCustomerInfoNewMapper, SsCustomerInfoNew> implements ISsCustomerInfoNewService {

    @Autowired
    SqlMapper sqlMapper;

    @Autowired
    SsCustomerInfoNewMapper ssCustomerInfoNewMapper;

    @Override
    public List ListAllCustormers(String code){


        StringBuilder sqlstr = new StringBuilder();
                sqlstr.append(" select a.customer_id as customerId,a.customer_name as customerName,a.sex,a.status,a.customer_img as customerImg,a.phone,a.create_time as createTime,b.orderTotal,c.ticketTotal,e.moneyTotal from ")
                .append("(select customer_id,customer_img,customer_name,sex,status,phone,create_time from ss_customer_info a where status=\"100\")  a,")
                .append(" (select customer_id,count(customer_id) as orderTotal from ss_order_info b GROUP BY customer_id) b,")
                .append(" (select customer_id,count(customer_id) as ticketTotal from ss_customer_ticket where use_status=\"100\" GROUP BY customer_id) c,")
                .append("  (")
                .append("select customer_id,sum(total) as moneyTotal from ")
                .append("(select a.order_number,a.total from ")
                .append("(select order_number as order_number ,sum(amount) as total  from ss_order_detail GROUP BY order_number) a) d,")
                .append(" (select customer_id,order_number  from ss_order_info) b ")
                .append(" where d.order_number=b.order_number ")
                .append(" group by customer_id")
                .append(") e")
                .append(" where a.customer_id=b.customer_id and a.customer_id=c.customer_id and a.customer_id= e.customer_id ");

           //     .append("and customer_id =#{code}");

        Map<String, Object> params = new HashMap<>();
        params.put("sql",sqlstr);
       // params.put("customerId",code);
        if(code!=null&&!"".equals(code)){

        }else{
          //  params.put("sql","select customer_id as customerId from ss_customer_info");
        }
        List ans = sqlMapper.sqltest(params);

        return ans;
    }

    @Override
    public List<SsCustomerInfoNew> getAllCustormersNew(SsCustomerInfoNew ssCustomerInfoNew){
           StringBuilder sqlstr = new StringBuilder();
           sqlstr.append(" select id,customer_Id,customer_Name,sex,status,customerImg,phone,create_Time,orderTotal,ticketTotal,moneyTotal from ( ")
                   .append(" select a.id as id,a.customer_id as customerId,a.customer_name as customerName,a.sex as sex,a.status as status,a.customer_img as customerImg,a.phone as phone,a.create_time as createTime,b.orderTotal as orderTotal,c.ticketTotal as ticketTotal,e.moneyTotal as moneyTotal from ")
                   .append(" (select id,phone as customer_id,icon as customer_img,username as customer_name,sex,phone,status,create_time from sys_user a   ) a ")
                   .append(" left join ")
                   .append(" (select customer_id,count(customer_id) as orderTotal from ss_order_info b GROUP BY customer_id) b ")
                   .append(" on a.customer_id=b.customer_id ")
                   .append(" left join  ")
                   .append(" (select customer_id,count(customer_id) as ticketTotal from ss_customer_ticket where use_status=\"100\" GROUP BY customer_id) c ")
                   .append(" on a.customer_id=c.customer_id  ")
                   .append(" left JOIN ")
                   .append(" ( ")
                   .append(" select customer_id,sum(total) as moneyTotal from ")
                   .append(" (select a.order_number,a.total from ")
                   .append(" (select order_number as order_number ,sum(amount) as total  from ss_order_detail GROUP BY order_number) a) d,")
                   .append(" (select customer_id,order_number  from ss_order_info) b ")
                   .append(" where d.order_number=b.order_number ")
                   .append(" group by customer_id ")
                   .append(" ) e ")
                   .append(" on a.customer_id=e.customer_id ) a  where 1=1");

                   if(ssCustomerInfoNew!=null) {
                       if(!"".equals(ssCustomerInfoNew.getCustomerName())&&ssCustomerInfoNew.getCustomerName()!=null){
                           sqlstr.append( " and customer_Name like '%"+ssCustomerInfoNew.getCustomerName()+"%'");
                       }
                       if(!"".equals(ssCustomerInfoNew.getStatus())&&ssCustomerInfoNew.getStatus()!=null){
                           sqlstr.append(" and status="+ssCustomerInfoNew.getStatus()+"");
                       }
                       if(!"".equals(ssCustomerInfoNew.getPhone())&&ssCustomerInfoNew.getPhone()!=null){
                           sqlstr.append(" and phone="+ssCustomerInfoNew.getPhone()+"");
                       }
                       if(!"".equals(ssCustomerInfoNew.getOrderTotal())&&ssCustomerInfoNew.getOrderTotal()!=null){
                           sqlstr.append(" and orderTotal="+ssCustomerInfoNew.getOrderTotal()+"");
                       }
                       if(!"".equals(ssCustomerInfoNew.getTicketTotal())&&ssCustomerInfoNew.getTicketTotal()!=null){
                           sqlstr.append(" and ticketTotal="+ssCustomerInfoNew.getTicketTotal()+"");
                       }
                       if(!"".equals(ssCustomerInfoNew.getMoneyTotal())&&ssCustomerInfoNew.getMoneyTotal()!=null){
                           sqlstr.append(" and moneyTotal="+ssCustomerInfoNew.getMoneyTotal()+"");
                       }
                   }


                   Map<String, Object> params = new HashMap<>();
                   params.put("sql",sqlstr);
                   List ans = sqlMapper.sqltest(params);

                   return ans;
    }

    /**
     * 多级表查询分页
     * @param page
     * @param queryWrapper
     * @return
     */
    @Override
    public IPage<SsCustomerInfoNew> getAllSsCustomerInfoNewList(IPage<SsCustomerInfoNew> page, @Param(Constants.WRAPPER) Wrapper<SsCustomerInfoNew> queryWrapper){
                  return  ssCustomerInfoNewMapper.getAllSsCustomerInfoNewList(page, queryWrapper);
    }
}
