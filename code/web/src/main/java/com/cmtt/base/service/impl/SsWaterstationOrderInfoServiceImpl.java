package com.cmtt.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cmtt.base.entity.*;
import com.cmtt.base.mapper.SsOrderInfoMapper;
import com.cmtt.base.mapper.SsWaterstationOrderInfoMapper;
import com.cmtt.base.service.ISsWaterstationOrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cmtt.base.utils.RC;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 水站订单信息 服务实现类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-02-21
 */
@Service
public class SsWaterstationOrderInfoServiceImpl extends ServiceImpl<SsWaterstationOrderInfoMapper, SsWaterstationOrderInfo> implements ISsWaterstationOrderInfoService {

    @Autowired
    private SsOrderInfoMapper  ssOrderInfoMapper;

    @Autowired
    private SsWaterstationOrderInfoMapper ssWaterstationOrderInfoMapper;

    @Override
    public IPage<SsWaterstationOrderInfo> getAllSsWaterstationOrderInfoList(IPage<SsWaterstationOrderInfo> page, @Param(Constants.WRAPPER) Wrapper<SsWaterstationOrderInfo> queryWrapper){

        return ssWaterstationOrderInfoMapper.getAllSsWaterstationOrderInfoList(page,queryWrapper);

    }

    @Override
    public IPage<SsWaterstationOrderInfo> getSsWaterstationOrderInfoList(IPage<SsWaterstationOrderInfo> page, Wrapper<SsWaterstationOrderInfo> queryWrapper) {
        return ssWaterstationOrderInfoMapper.getSsWaterstationOrderInfoList(page,queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SsWaterstationOrderInfo saveAllWaterstationOrderInfo(SsWaterstationOrderInfo ssWaterstationOrderInfo) {
        try{
            SsWaterstationOrderInfo ssWaterstationOrderInfoNew = new SsWaterstationOrderInfo();
            List<SsWaterstationOrderDetail> listSsWaterstationOrderDetail = ssWaterstationOrderInfo
                                                                              .getSsWaterstationOrderDetail();
            double totalPrice = 0;

            List<SsWaterstationOrderDetail> SsWaterstationOrderDetailNews = new ArrayList<>();

            if(listSsWaterstationOrderDetail!=null){
                for(int i=0;i<listSsWaterstationOrderDetail.size();i++){
                    SsWaterstationOrderDetail ssWaterstationOrderDetail = new SsWaterstationOrderDetail();
                    SsStationGoods ssStationGoods1 = new SsStationGoods();

                    String orderNum = listSsWaterstationOrderDetail.get(i).getOrderNumber();
                    Integer goodsId = listSsWaterstationOrderDetail.get(i).getGoodsId();//商品id
                    Integer num = listSsWaterstationOrderDetail.get(i).getNum();//购买商品数量

               //    ssStationGoods1.setGoodsId(goodsId);
               //     ssStationGoods1.setStationId(Integer.parseInt(ssWaterstationOrderInfo.getStationId()));
                   Integer stationId =  ssWaterstationOrderInfo.getStationId();
                    SsStationGoods ssStationGoods  = ssWaterstationOrderInfoMapper.getSsStationGoods(goodsId,stationId);
                    //SsGoodsInfo ssGoodsInfo = ssOrderInfoMapper.getSsGoodsInfoByGoodsId(goodsId); //根据商品id获取商品
                    if(ssStationGoods==null){
                        return null;
                    }
                    double unitPrice = ssStationGoods.getStationSalePrice(); //获得商品单价
                    double amount = unitPrice*num; //获得该商品购买价的小计

                    ssWaterstationOrderDetail.setOrderNumber(orderNum);
                    ssWaterstationOrderDetail.setNum(num);
                    ssWaterstationOrderDetail.setGoodsId(goodsId);
                    ssWaterstationOrderDetail.setAmount(amount);
                    ssWaterstationOrderDetail.setPrice(unitPrice);

                    totalPrice += amount;
                    SsWaterstationOrderDetailNews.add(ssWaterstationOrderDetail);
                    ssWaterstationOrderInfoMapper.saveOrderDetail(ssWaterstationOrderDetail);
                }
            }

            ssWaterstationOrderInfo.setTotalPrice(totalPrice);

            ssWaterstationOrderInfo.setSsWaterstationOrderDetail(null);

            ssWaterstationOrderInfoMapper.saveOrderInfo(ssWaterstationOrderInfo);

            ssWaterstationOrderInfoNew.setOrderNumber(ssWaterstationOrderInfo.getOrderNumber());
            ssWaterstationOrderInfoNew.setTotalPrice(totalPrice);
            ssWaterstationOrderInfoNew.setSsWaterstationOrderDetail(SsWaterstationOrderDetailNews);
            return ssWaterstationOrderInfoNew;
        }catch(Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return null;
        }

    }

    /**
     * 支付完成后，回写订单状态
     * @param orderNum
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean UpdateAllOrderInfo(String orderNum,String payStatus) {
        try{

            if(RC.PAY_NO.equals(payStatus)){
                return false;
            }
           ssWaterstationOrderInfoMapper.updateSsWaterstationOrderInfo(Integer.parseInt(orderNum),payStatus);


            return true;
        }catch(Exception e){
            return false;
        }
    }
}
