package com.cmtt.base.service;

import com.cmtt.base.entity.SsGoodsTag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品标签信息 服务类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-02-06
 */
public interface ISsGoodsTagService extends IService<SsGoodsTag> {

    Integer deleteByGoodsId(Integer goodsId);

    List<Integer> getTagIds(Integer goodsId);
}
