package com.cmtt.base.mapper;

import com.cmtt.base.entity.SsGoodsType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品分类 Mapper 接口
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-29
 */
public interface SsGoodsTypeMapper extends BaseMapper<SsGoodsType> {

    /**
     * 获取指定状态的商品分类信息
     * @param status
     * @return
     */
    @Select({"select id, type_name from ss_goods_type where status=#{status}"})
    List<Map<Integer, String>> getGoodsType(Integer status);
}
