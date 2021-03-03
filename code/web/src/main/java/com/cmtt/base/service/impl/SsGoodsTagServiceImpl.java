package com.cmtt.base.service.impl;

import com.cmtt.base.entity.SsGoodsTag;
import com.cmtt.base.mapper.SsGoodsTagMapper;
import com.cmtt.base.service.ISsGoodsTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品标签信息 服务实现类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-02-06
 */
@Service
public class SsGoodsTagServiceImpl extends ServiceImpl<SsGoodsTagMapper, SsGoodsTag> implements ISsGoodsTagService {

    @Autowired
    private SsGoodsTagMapper ssGoodsTagMapper;

    @Override
    public Integer deleteByGoodsId(Integer goodsId) {
        return ssGoodsTagMapper.deleteByGoodsId(goodsId);
    }

    @Override
    public List<Integer> getTagIds(Integer goodsId) {
        return ssGoodsTagMapper.getTagIds(goodsId);
    }
}
