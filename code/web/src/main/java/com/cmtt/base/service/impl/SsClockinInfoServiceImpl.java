package com.cmtt.base.service.impl;

import com.cmtt.base.controller.miniappparams.ClockinInputParam;
import com.cmtt.base.entity.SsClockinInfo;
import com.cmtt.base.mapper.SsClockinInfoMapper;
import com.cmtt.base.service.ISsClockinInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuzm
 * @since 2021-02-20
 */
@Service
public class SsClockinInfoServiceImpl extends ServiceImpl<SsClockinInfoMapper, SsClockinInfo> implements ISsClockinInfoService {

    @Autowired
    private SsClockinInfoMapper ssClockinInfoMapper;

    @Override
    public List<SsClockinInfo> getClockinList(Integer userId, String month) {
        return ssClockinInfoMapper.getClockinList(userId, month);
    }

    @Override
    public Integer updateOutTime(LocalTime outTime, Integer userId, LocalDate clockinDate){
        return ssClockinInfoMapper.updateOutTime(outTime, userId, clockinDate);
    }
}
