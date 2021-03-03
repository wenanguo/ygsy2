package com.cmtt.base.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cmtt.base.entity.SsCustomerInfo;
import com.cmtt.base.entity.SsCustomerInfoNew;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 客户信息 服务类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-28
 */
public interface ISsCustomerInfoNewService extends IService<SsCustomerInfoNew> {

    public  List ListAllCustormers(String code);

    public List<SsCustomerInfoNew> getAllCustormersNew(SsCustomerInfoNew ssCustomerInfoNew);

    public IPage<SsCustomerInfoNew> getAllSsCustomerInfoNewList(IPage<SsCustomerInfoNew> page, @Param(Constants.WRAPPER) Wrapper<SsCustomerInfoNew> queryWrapper);


}
