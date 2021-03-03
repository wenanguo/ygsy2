package com.cmtt.base.mapper;

import com.cmtt.base.entity.SsGoodsTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品标签信息 Mapper 接口
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-02-06
 */
public interface SsGoodsTagMapper extends BaseMapper<SsGoodsTag> {

    /**
     * 通过商品ID删除
     * @param goodsId
     * @return
     */
    @Delete({"delete from ss_goods_tag where goods_id=#{goodsId}"})
    Integer deleteByGoodsId(Integer goodsId);

    /**
     * 获取商品的标签ID
     * @param goodsId
     * @return
     */
    @Select({"select tag_id from ss_goods_tag where goods_id=#{goodsId}"})
    List<Integer> getTagIds(Integer goodsId);

}
