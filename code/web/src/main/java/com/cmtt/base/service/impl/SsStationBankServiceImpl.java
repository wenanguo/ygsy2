package com.cmtt.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cmtt.base.entity.SsStationBank;
import com.cmtt.base.mapper.SsStationBankMapper;
import com.cmtt.base.service.ISsStationBankService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 水站绑定的银行卡信息 服务实现类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-31
 */
@Service
public class SsStationBankServiceImpl extends ServiceImpl<SsStationBankMapper, SsStationBank> implements ISsStationBankService {

    @Override
    public List<Map<Integer, String>> getBankList() {
        return this.baseMapper.getBankList();
    }

    @Override
    public IPage<SsStationBank> getStationBankPage(IPage<SsStationBank> page, @Param(Constants.WRAPPER) Wrapper<SsStationBank> queryWrapper){
        return this.baseMapper.getStationBankPage(page, queryWrapper);
    }
}
