package com.cmtt.base.service.impl;

import com.cmtt.base.entity.SsWaterTicket;
import com.cmtt.base.mapper.SsWaterTicketMapper;
import com.cmtt.base.service.ISsWaterTicketService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 水票信息 服务实现类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-31
 */
@Service
public class SsWaterTicketServiceImpl extends ServiceImpl<SsWaterTicketMapper, SsWaterTicket> implements ISsWaterTicketService {

    @Autowired
    private SsWaterTicketMapper ssWaterTicketMapper;
    @Override
    public List<Map<Integer, String>> getTicketDict(Integer status) {
        return ssWaterTicketMapper.getTicketDict(status);
    }
}
