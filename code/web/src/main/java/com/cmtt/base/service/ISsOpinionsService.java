package com.cmtt.base.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cmtt.base.entity.SsOpinions;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-02-16
 */
public interface ISsOpinionsService extends IService<SsOpinions> {

    //添加订单相关插入
    @Transactional(rollbackFor = Exception.class)
     boolean saveOpinionById(SsOpinions ssOpinions);

    IPage<SsOpinions> getAllOpinions(IPage<SsOpinions> page, @Param(Constants.WRAPPER) Wrapper<SsOpinions> queryWrapper);

}
