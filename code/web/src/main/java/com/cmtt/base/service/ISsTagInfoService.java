package com.cmtt.base.service;

import com.cmtt.base.entity.SsTagInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品标签信息 服务类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-29
 */
public interface ISsTagInfoService extends IService<SsTagInfo> {
    List<Map<Integer, String>> getTagDict(Integer status);
}
