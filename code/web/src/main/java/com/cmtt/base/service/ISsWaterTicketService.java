package com.cmtt.base.service;

import com.cmtt.base.entity.SsWaterTicket;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 水票信息 服务类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-31
 */
public interface ISsWaterTicketService extends IService<SsWaterTicket> {

    List<Map<Integer, String>> getTicketDict(Integer status);

}
