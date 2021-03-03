package com.cmtt.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cmtt.base.entity.R;
import com.cmtt.base.entity.SsOpinions;
import com.cmtt.base.entity.SsOrderInfo;
import com.cmtt.base.mapper.SsOpinionsMapper;
import com.cmtt.base.mapper.SsOrderInfoMapper;
import com.cmtt.base.service.ISsOpinionsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-02-16
 */
@Service
public class SsOpinionsServiceImpl extends ServiceImpl<SsOpinionsMapper, SsOpinions> implements ISsOpinionsService {

    @Autowired
    private SsOpinionsMapper  ssOpinionsMapper;

    @Override
    //添加订单相关插入
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOpinionById(SsOpinions ssOpinions) {
        try{
            ssOpinionsMapper.saveOpinionById(ssOpinions);
            ssOpinionsMapper.updateOpinionById(ssOpinions.getParentId());
            return true;
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //获取意见列表
    @Override
    public IPage<SsOpinions> getAllOpinions(IPage<SsOpinions> page, @Param(Constants.WRAPPER) Wrapper<SsOpinions> queryWrapper){
           try{
               return ssOpinionsMapper.getAllOpinions(page,queryWrapper);
           }catch(Exception e){
               e.printStackTrace();
               return null;
           }
    }

}
