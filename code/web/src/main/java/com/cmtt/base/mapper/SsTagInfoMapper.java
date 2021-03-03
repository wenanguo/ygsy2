package com.cmtt.base.mapper;

import com.cmtt.base.entity.SsTagInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品标签信息 Mapper 接口
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-29
 */
public interface SsTagInfoMapper extends BaseMapper<SsTagInfo> {

    /**
     * 获取tag信息字典
     * @param status
     * @return
     */
    @Select({"select id, tag_name from ss_tag_info where tag_status=#{status}"})
    List<Map<Integer, String>> getTagDict(Integer status);
}
