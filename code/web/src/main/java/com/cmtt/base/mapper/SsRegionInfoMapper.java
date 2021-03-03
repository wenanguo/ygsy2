package com.cmtt.base.mapper;

import com.cmtt.base.entity.SsRegionInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 区域信息表 Mapper 接口
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-02-19
 */
public interface SsRegionInfoMapper extends BaseMapper<SsRegionInfo> {

    /**
     * 获取区域信息
     * @return
     */
    @Select({"select b.areacode, concat(a.areadesc,b.areadesc) areadesc from ss_region_info a inner join ss_region_info b on a.areacode=b.parentarea where b.areatype=2"})
    List<Map<String, String>> getRegionList();

    @Update({"update ss_region_info set region_code=#{regionCode} where areacode=#{areacode}"})
    Integer updateRegion(String regionCode, String areacode);
}
