package com.cmtt.base.service.impl;

import com.cmtt.base.entity.SsGoodsType;
import com.cmtt.base.mapper.SsGoodsTypeMapper;
import com.cmtt.base.service.ISsGoodsTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品分类 服务实现类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-29
 */
@Service
public class SsGoodsTypeServiceImpl extends ServiceImpl<SsGoodsTypeMapper, SsGoodsType> implements ISsGoodsTypeService {

    @Autowired
    private SsGoodsTypeMapper ssGoodsTypeMapper;

    @Override
    public List<Map<Integer, String>> getGoodsType(Integer status){
        return ssGoodsTypeMapper.getGoodsType(status);
    }

}
