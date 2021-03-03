package com.cmtt.base.service;

import com.cmtt.base.entity.SsGoodsType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品分类 服务类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-29
 */
public interface ISsGoodsTypeService extends IService<SsGoodsType> {
    List<Map<Integer, String>> getGoodsType(Integer status);
}
