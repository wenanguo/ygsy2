package com.cmtt.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cmtt.base.entity.SsWaterStation;
import com.cmtt.base.mapper.SsWaterStationMapper;
import com.cmtt.base.service.ISsWaterStationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 水站信息表 服务实现类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-31
 */
@Service
public class SsWaterStationServiceImpl extends ServiceImpl<SsWaterStationMapper, SsWaterStation> implements ISsWaterStationService {

    @Autowired
    private SsWaterStationMapper ssWaterStationMapper;

    @Override
    public List<Map<Integer, String>> getStationDict(Integer status) {
        return ssWaterStationMapper.getStationDict(status);
    }

    @Override
    public List<Map<String, String>> getRetionInfoDict(Integer status, String parentarea){
        return ssWaterStationMapper.getRetionInfoDict(status, parentarea);
    }

    @Override
    public IPage<SsWaterStation> getStationPage(IPage<SsWaterStation> page, Wrapper<SsWaterStation> queryWrapper) {
        return ssWaterStationMapper.getStationPage(page, queryWrapper);
    }
}
