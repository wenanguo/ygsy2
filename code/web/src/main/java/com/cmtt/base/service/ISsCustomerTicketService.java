package com.cmtt.base.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cmtt.base.entity.SsCustomerInfoNew;
import com.cmtt.base.entity.SsCustomerTicket;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户购买水票信息 服务类
 * </p>
 *
 * @author lin
 * @since 2021-01-29
 */
public interface ISsCustomerTicketService extends IService<SsCustomerTicket> {
    //获取用户水票表全部信息
    public IPage<SsCustomerTicket> getAllSsCustomerTicketList(IPage<SsCustomerTicket> page, @Param(Constants.WRAPPER) Wrapper<SsCustomerTicket> queryWrapper);

}
