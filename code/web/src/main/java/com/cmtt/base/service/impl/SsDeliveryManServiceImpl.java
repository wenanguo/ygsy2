package com.cmtt.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cmtt.base.controller.miniappparams.SysUserInputParam;
import com.cmtt.base.entity.SsDeliveryMan;
import com.cmtt.base.entity.SysUser;
import com.cmtt.base.mapper.SsDeliveryManMapper;
import com.cmtt.base.mapper.SysUserMapper;
import com.cmtt.base.service.ISsDeliveryManService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 配送员信息 服务实现类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-31
 */
@Service
public class SsDeliveryManServiceImpl extends ServiceImpl<SsDeliveryManMapper, SsDeliveryMan> implements ISsDeliveryManService {

    @Autowired
    private SsDeliveryManMapper ssDeliveryManMapper;

    @Override
    public boolean existsUsername(String username) {
        return ssDeliveryManMapper.queryUsername(username) > 0 ? true : false;
    }

    /**
     * 验证用户手机号是否唯一
     *
     * @param phone
     * @return
     */
    /*@Override
    public boolean existsPhone(String phone) {
        return ssDeliveryManMapper.queryUserNumByPhone(phone) > 0 ? true : false;
    }*/
}
