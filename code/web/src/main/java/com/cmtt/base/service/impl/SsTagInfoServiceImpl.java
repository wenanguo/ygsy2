package com.cmtt.base.service.impl;

import com.cmtt.base.entity.SsTagInfo;
import com.cmtt.base.mapper.SsTagInfoMapper;
import com.cmtt.base.service.ISsTagInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品标签信息 服务实现类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-29
 */
@Service
public class SsTagInfoServiceImpl extends ServiceImpl<SsTagInfoMapper, SsTagInfo> implements ISsTagInfoService {

    @Autowired
    private SsTagInfoMapper ssTagInfoMapper;

    @Override
    public List<Map<Integer, String>> getTagDict(Integer status) {
        return ssTagInfoMapper.getTagDict(status);
    }
}
