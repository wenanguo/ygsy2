package com.cmtt.base.service.impl;

import com.cmtt.base.entity.SsRegionInfo;
import com.cmtt.base.mapper.SsRegionInfoMapper;
import com.cmtt.base.service.ISsRegionInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 区域信息表 服务实现类
 * </p>
 *
 * @author yuzm
 * @since 2021-02-19
 */
@Service
public class SsRegionInfoServiceImpl extends ServiceImpl<SsRegionInfoMapper, SsRegionInfo> implements ISsRegionInfoService {

    @Autowired
    private SsRegionInfoMapper ssRegionInfoMapper;

    @Override
    public List<Map<String, String>> getRegionList() {
        return ssRegionInfoMapper.getRegionList();
    }

    @Override
    public Integer updateRegion(String regionCode, String areacode) {
        return ssRegionInfoMapper.updateRegion(regionCode,areacode);
    }
}
