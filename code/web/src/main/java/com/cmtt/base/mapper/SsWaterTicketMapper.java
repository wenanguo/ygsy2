package com.cmtt.base.mapper;

import com.cmtt.base.entity.SsWaterTicket;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 水票信息 Mapper 接口
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-31
 */
@Mapper
@Component(value = "ssWaterTicketMapper")
public interface SsWaterTicketMapper extends BaseMapper<SsWaterTicket> {

    /**
     * 获取水站字典信息
     * @param status
     * @return
     */
    @Select({"select id, ticket_name from ss_water_ticket where status=#{status}"})
    List<Map<Integer, String>> getTicketDict(Integer status);

}
