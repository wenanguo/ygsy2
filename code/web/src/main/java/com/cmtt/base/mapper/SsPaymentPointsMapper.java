package com.cmtt.base.mapper;

import com.cmtt.base.entity.SsPaymentPoints;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 用户消费积分信息 Mapper 接口
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-02-11
 */
@Mapper
@Component(value = "ssPaymentPointsMapper")
public interface SsPaymentPointsMapper extends BaseMapper<SsPaymentPoints> {

    @Insert(" \n" +
            "insert into ss_payment_points(customer_id,order_number,goods_id,goods_num,total_points) \n" +
            "values (#{customerId},#{orderNumber},#{goodsId},#{goodsNum},#{totalPoints})"
    )
    boolean insertPaymentPoints(SsPaymentPoints ssPaymentPoints);

}
