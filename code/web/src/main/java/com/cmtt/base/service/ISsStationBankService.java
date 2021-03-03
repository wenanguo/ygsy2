package com.cmtt.base.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cmtt.base.entity.SsStationBank;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 水站绑定的银行卡信息 服务类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-31
 */
public interface ISsStationBankService extends IService<SsStationBank> {

    List<Map<Integer, String>> getBankList();

    IPage<SsStationBank> getStationBankPage(IPage<SsStationBank> page, @Param(Constants.WRAPPER) Wrapper<SsStationBank> queryWrapper);
}
