package com.cmtt.base.service;

import com.cmtt.base.controller.miniappparams.SysUserInputParam;
import com.cmtt.base.entity.SsDeliveryMan;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 配送员信息 服务类
 * </p>
 *
 * @author yuzm
 * @since 2021-01-31
 */
public interface ISsDeliveryManService extends IService<SsDeliveryMan> {

    //boolean existsPhone(String phone);


    boolean existsUsername(String username);
}
