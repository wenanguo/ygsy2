package com.cmtt.base.service;

import com.cmtt.base.controller.miniappparams.ClockinInputParam;
import com.cmtt.base.entity.SsClockinInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuzm
 * @since 2021-02-20
 */
public interface ISsClockinInfoService extends IService<SsClockinInfo> {

    List<SsClockinInfo> getClockinList(Integer userId, String month);

    Integer updateOutTime(LocalTime outTime, Integer userId, LocalDate clockinDate);
}
