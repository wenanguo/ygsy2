package com.cmtt.base.service;

import com.cmtt.base.entity.SsRegionInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 区域信息表 服务类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-02-19
 */
public interface ISsRegionInfoService extends IService<SsRegionInfo> {

    List<Map<String, String>> getRegionList();

    Integer updateRegion(String regionCode, String areacode);

}
