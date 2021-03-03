package com.cmtt.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;

import com.cmtt.base.entity.SsCustomerTicket;
import com.cmtt.base.mapper.SsCustomerTicketMapper;
import com.cmtt.base.service.ISsCustomerTicketService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户购买水票信息 服务实现类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-29
 */
@Service
public class SsCustomerTicketServiceImpl extends ServiceImpl<SsCustomerTicketMapper, SsCustomerTicket> implements ISsCustomerTicketService {


    @Autowired
    SsCustomerTicketMapper ssCustomerTicketMapper;

    @Override
    public IPage<SsCustomerTicket> getAllSsCustomerTicketList(IPage<SsCustomerTicket> page, @Param(Constants.WRAPPER) Wrapper<SsCustomerTicket> queryWrapper){
         return  ssCustomerTicketMapper.getAllSsCustomerTicketList(page, queryWrapper);
    }

}
