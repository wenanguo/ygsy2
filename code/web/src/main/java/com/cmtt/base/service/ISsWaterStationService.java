package com.cmtt.base.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cmtt.base.entity.SsWaterStation;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 水站信息表 服务类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-31
 */
public interface ISsWaterStationService extends IService<SsWaterStation> {

    List<Map<Integer, String>> getStationDict(Integer status);

    List<Map<String, String>> getRetionInfoDict(Integer status, String parentarea);

    public IPage<SsWaterStation> getStationPage(IPage<SsWaterStation> page, @Param(Constants.WRAPPER) Wrapper<SsWaterStation> queryWrapper);
}
