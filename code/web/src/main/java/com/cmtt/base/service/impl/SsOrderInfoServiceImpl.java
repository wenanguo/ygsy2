package com.cmtt.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cmtt.base.entity.*;
import com.cmtt.base.mapper.SsOrderInfoMapper;
import com.cmtt.base.mapper.SsPaymentPointsMapper;
import com.cmtt.base.service.ISsOrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cmtt.base.utils.RC;
import com.tencentcloudapi.tcb.v20180608.models.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * <p>
 * 订单信息 服务实现类
 * </p>
 *
 * @author linsaveAllOrderInfoReturnOrderNum
 * @since 2021-01-30
 */
@Service
public class SsOrderInfoServiceImpl extends ServiceImpl<SsOrderInfoMapper, SsOrderInfo> implements ISsOrderInfoService {

    @Autowired
    private SsOrderInfoMapper  ssOrderInfoMapper;

    @Autowired
    private SsPaymentPointsMapper ssPaymentPointsMapper;
    @Override
    public List<Map<Integer, String>> getLastOrder(Integer status, String openId) {
        return ssOrderInfoMapper.getLastOrder(status,openId);
    }

    @Override
    public IPage<SsOrderInfo> getAllOrderDetailList(IPage<SsOrderInfo> page, Wrapper<SsOrderInfo> queryWrapper) {
        return ssOrderInfoMapper.getAllOrderDetailList(page,queryWrapper);
    }

    @Override
    //添加订单相关插入
    @Transactional(rollbackFor = Exception.class)
    public boolean saveAllOrderInfo(SsOrderInfo ssOrderInfo,List<SsOrderDetail> ssOrderDetails) {
        try {
            ssOrderInfoMapper.saveOrderInfo(ssOrderInfo); //存订单基础表


            for (int i = 0; i < ssOrderDetails.size(); i++) {//存订单明细
                SsOrderDetail ssOrderDetail = new SsOrderDetail();

                ssOrderDetail.setOrderNumber(ssOrderInfo.getOrderNumber());

                Integer num = ssOrderDetails.get(i).getNum(); //水票的话，该处代表买水票的条数；商品则对对应数量
                ssOrderDetail.setNum(num);
                ssOrderDetail.setAmount(ssOrderDetails.get(i).getAmount());
                ssOrderDetail.setPrice(ssOrderDetails.get(i).getPrice());
                ssOrderDetail.setMemo(ssOrderDetails.get(i).getMemo());
                ssOrderDetail.setAddressId(ssOrderDetails.get(i).getAddressId());

                SsCustomerAddress ssCustomerAddress = ssOrderInfoMapper.getWaterStationByOrderNumber(num);
                if (ssCustomerAddress != null) {
                    ssOrderDetail.setStationId(ssCustomerAddress.getStationId());
                } else {
                    ssOrderDetail.setStationId(null);
                }

                Integer goodsId = ssOrderDetails.get(i).getGoodsId();
                ssOrderDetail.setGoodsId(goodsId);

                SsGoodsInfo ssGoodsInfo = getTicketOrNot(goodsId);

                ssOrderInfoMapper.saveOrderDetail(ssOrderDetail);
                if (ssOrderInfo.getPayStatus() == 203) {
                    if (ssGoodsInfo.getGoodsTypeId() == 1) {//如果是水票，需要往水票表插入

                        //得到水票总数据条数
                        int totalCustomerTicket = num * (ssGoodsInfo.getTicketNum());

                        for (int j = 0; j < totalCustomerTicket; j++) {
                            SsCustomerTicket ssCustomerTicket = new SsCustomerTicket();
                            ssCustomerTicket.setCustomerId(ssOrderInfo.getCustomerId());

                            Integer ticketCode = getTicketCode();
                            ssCustomerTicket.setTicketCode(ticketCode + "");
                            ssCustomerTicket.setUseStatus(1);
                            ssCustomerTicket.setTicketId(ssGoodsInfo.getTicketId());
                            ssOrderInfoMapper.saveCustomerTicket(ssCustomerTicket);
                        }
                    }
                }
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @Override
    public SsGoodsInfo getTicketOrNot(Integer goodsId) {
        SsGoodsInfo ssGoodsInfo = ssOrderInfoMapper.getTicketOrNot(goodsId);

        return ssGoodsInfo;
    }

    private int getTicketCode(){
        StringBuilder str =new StringBuilder();//定义变长字符串
        Random random =new Random();
//随机生成数bai字，并添du加到字符串
        for(int i=0;i<8;i++){
           str.append(random.nextInt(10));
        }
//将字符串转zhi换为数dao字并输出
        int num=Integer.parseInt(str.toString());
        return num ;
    }

    @Override
   public List<SsOrderInfo> getOrderInfoByStationId(Integer stationId){
       return ssOrderInfoMapper.getOrderInfoByStationId(stationId);
    }

    //订单水站派送员开始派送
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean  updateByOrderNum(SsOrderInfo ssOrderInfo, SsOrderDelivery ssOrderDelivery){
        try {
            ssOrderInfoMapper.updateOrderInfoByOrderNum(ssOrderInfo.getOrderNumber(),ssOrderInfo.getOrderStatus()+"");
            ssOrderInfoMapper.insertByOrderNumForDeliver(ssOrderDelivery);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    //订单派送中的状态修改
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean  updateByOrderNumForDeliver(SsOrderDelivery ssOrderDelivery){

        try{
            ssOrderInfoMapper.updateByOrderNumForDeliver(Integer.parseInt(ssOrderDelivery.getOrderNumber()));

            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public IPage<SsOrderInfo> getOrderInfoByStationIdPage(IPage<SsOrderInfo> page, Wrapper<SsOrderInfo> queryWrapper) {
        return ssOrderInfoMapper.getOrderInfoByStationIdPage(page,queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SsOrderInfo saveAllOrderInfoReturnOrderNum(SsOrderInfo ssOrderInfo, List<SsOrderDetail> ssOrderDetails) {

        try {
            SsOrderInfo ssOrderInfoNew = new  SsOrderInfo();



            int totalPrice = 0;

            SsGoodsInfo ssGoodsInfo = new SsGoodsInfo();

            for (int i = 0; i < ssOrderDetails.size(); i++) {//存订单明细
                SsOrderDetail ssOrderDetail = new SsOrderDetail();

                 ssGoodsInfo= ssOrderInfoMapper.getSsGoodsInfoByGoodsId(ssOrderDetails.get(i).getGoodsId());
                 Integer num = ssOrderDetails.get(i).getNum(); //水票的话，该处代表买水票的条数；商品则对对应数量


                Double unitPrice = ssGoodsInfo.getUnitPrice();

                double  amount = unitPrice*num;
                totalPrice += amount;
                ssOrderDetail.setOrderNumber(ssOrderInfo.getOrderNumber());

                ssOrderDetail.setNum(num);
                ssOrderDetail.setAmount(amount); //金额小计
                ssOrderDetail.setPrice(unitPrice); //单价
                ssOrderDetail.setMemo(ssOrderDetails.get(i).getMemo());
                ssOrderDetail.setAddressId(ssOrderDetails.get(i).getAddressId());

                SsCustomerAddress ssCustomerAddress = ssOrderInfoMapper.getWaterStationByOrderNumber(Integer.parseInt(ssOrderInfo.getOrderNumber()));
                if (ssCustomerAddress != null) {
                    ssOrderDetail.setStationId(ssCustomerAddress.getStationId());
                } else {
                    ssOrderDetail.setStationId(null);
                }

                Integer goodsId = ssOrderDetails.get(i).getGoodsId();
                ssOrderDetail.setGoodsId(goodsId);

                ssOrderInfoMapper.saveOrderDetail(ssOrderDetail);
            }
            ssOrderInfo.setTotalPrice(totalPrice);
            ssOrderInfoNew.setOrderNumber(ssOrderInfo.getOrderNumber());
            ssOrderInfo.setPayStatus(RC.PAY_NO.code());
            ssOrderInfoNew.setTotalPrice(totalPrice);
            if(ssGoodsInfo!=null){
                ssOrderInfoNew.setGoodsName(ssGoodsInfo.getGoodsName());
            }

            ssOrderInfoMapper.saveOrderInfo(ssOrderInfo); //存订单基础表
            return ssOrderInfoNew;
        }catch(Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return null;
        }
    }

    /**
     * 水票支付
     * @param ssOrderInfo
     * @param ssOrderDetails
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SsOrderInfo PayTicketReturnOrderNum(SsOrderInfo ssOrderInfo,List<SsOrderDetail> ssOrderDetails){
        try {
            SsOrderInfo ssOrderInfoNew = new  SsOrderInfo();



            int totalPrice = 0;
            List<SsWaterTicket> listssWaterTicketsNew = new ArrayList();
            List<SsOrderDetail> listSsOrderDetail =  new ArrayList<>();
           // int ticketNum = 0;
            double unitPrice = 0;
            for (int i = 0; i < ssOrderDetails.size(); i++) {//存订单明细
                SsOrderDetail ssOrderDetail = new SsOrderDetail();

                SsGoodsInfo ssGoodsInfo= ssOrderInfoMapper.getSsGoodsInfoByGoodsId(ssOrderDetails.get(i).getGoodsId());

                Integer num = ssOrderDetails.get(i).getNum(); //水票的话，该处代表买水票的条数；商品则对对应数量
              //  ticketNum = num;
                 unitPrice = ssGoodsInfo.getUnitPrice();//获得对应商品的单价

                double  amount = unitPrice*num;
                totalPrice += amount;
                ssOrderDetail.setOrderNumber(ssOrderInfo.getOrderNumber());

                ssOrderDetail.setNum(num);
                ssOrderDetail.setAmount(amount); //金额小计
                ssOrderDetail.setPrice(unitPrice); //单价
                ssOrderDetail.setMemo(ssOrderDetails.get(i).getMemo());
                ssOrderDetail.setAddressId(ssOrderDetails.get(i).getAddressId());

//                SsCustomerAddress ssCustomerAddress = ssOrderInfoMapper.getWaterStationByOrderNumber(Integer.parseInt(ssOrderInfo.getOrderNumber()));
//                if (ssCustomerAddress != null) {
//                    ssOrderDetail.setStationId(ssCustomerAddress.getStationId());
//                } else {
//                    ssOrderDetail.setStationId(null);
//                }

                Integer goodsId = ssOrderDetails.get(i).getGoodsId();
                ssOrderDetail.setGoodsId(goodsId);

               List<SsWaterTicket>  ssWaterTickets = ssOrderInfoMapper.getWaterTicketByTicketValue(unitPrice);


               for(int j=0;j<ssWaterTickets.size();j++){
                   if((ssWaterTickets.get(j).getTicketValue()).equals(unitPrice)){//水票单价和商品单价相等
                       listssWaterTicketsNew.add(ssWaterTickets.get(j));
                   }
               }
                listSsOrderDetail.add(ssOrderDetail);

            }

           // SsWaterTicket ssWaterTicket = ssOrderInfoMapper.getWaterTicketByTicketId(Integer.parseInt(ssOrderInfo.getTicketId()));



          //  BigDecimal waterTicketValue = ssWaterTicket.getTicketValue();
          //  double waterTicketPrice = waterTicketValue.doubleValue(); //水票单价

            double ticketNum = totalPrice/unitPrice; //购买商品需要的水票数据
//根据商品ticketId查询
//            List<SsCustomerTicket> ssCustomerTicketList = ssOrderInfoMapper.
//                    getWaterTicketTotalByTicketId(Integer.parseInt(ssOrderInfo.getTicketId()),Integer.parseInt(ssOrderInfo.getCustomerId()));//获取该类水票可用的水票数量
            List<SsCustomerTicket> ssCustomerTicketList = ssOrderInfoMapper
                    .getWaterTicketTotalByTicketValue(unitPrice,Integer.parseInt(ssOrderInfo.getCustomerId()));
            int ticketTotal = ssCustomerTicketList.size();

            if(ticketNum<=ticketTotal){ //购买该商品需要的该类水票数足够时

               // ssOrderInfo.setTotalPrice(totalPrice);
                ssOrderInfoNew.setOrderNumber(ssOrderInfo.getOrderNumber());

                for(int i = 0;i<ticketNum;i++){//将水票进行核销
                    SsCustomerTicket customerTicket = new SsCustomerTicket();
                    customerTicket.setTicketId(ssCustomerTicketList.get(i).getTicketId());
                    customerTicket.setTicketCode(ssCustomerTicketList.get(i).getTicketCode());
                    customerTicket.setCustomerId(ssCustomerTicketList.get(i).getCustomerId());
                    LocalDateTime createTime = LocalDateTime.now(); //创建时间
                    customerTicket.setUseTime(createTime);
                    ssOrderInfoMapper.updateCustomerTicketByTicket(customerTicket);
                }
                // ssOrderInfoNew.setTotalPrice(totalPrice);
                ssOrderInfo.setPayStatus(203);
                ssOrderInfo.setTotalPrice(totalPrice);
                ssOrderInfoMapper.saveOrderInfo(ssOrderInfo); //存订单基础表

                for(int t=0;t<listSsOrderDetail.size();t++){
                    ssOrderInfoMapper.saveOrderDetail(listSsOrderDetail.get(t));
                }

                return ssOrderInfoNew;

            }else {
                return null;
            }
        }catch(Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return null;
        }
    }


    /**
     * 支付成功后回写状态调用的订单生成接口
     * @param orderNum
     * @param status
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean UpdateAllOrderInfo(String orderNum,String status) {
        try{


            if((RC.PAY_NO.code()).equals(status)){//支付失败
                return false;
            }

            SsOrderInfo  ssOrderInfo = ssOrderInfoMapper.getOrderInfoByOrderNumber(Integer.parseInt(orderNum));
            List<SsOrderDetail> ssOrderDetails = ssOrderInfoMapper.getOrderDetailByOrderNumber(Integer.parseInt(orderNum));
          //  SsGoodsInfo ssGoodsInfo = ssOrderInfoMapper.getSsGoodsInfoByOrderNumber(Integer.parseInt(orderNum));
            SsPaymentPoints ssPaymentPoints = new SsPaymentPoints();
            if(ssOrderDetails==null){
                return false;
            }

            for(int i = 0 ;i<ssOrderDetails.size();i++) {

                Integer goodsId = ssOrderDetails.get(i).getGoodsId();
                Integer num = ssOrderDetails.get(i).getNum(); //水票的话，该处代表买水票的条数；商品则对对应数量

                SsGoodsInfo ssGoodsInfo = getTicketOrNot(goodsId);

                ssPaymentPoints.setGoodsId(goodsId+"");
                ssPaymentPoints.setGoodsNum(num);
                ssPaymentPoints.setOrderNumber(orderNum+"");

                int totalPoint = Integer.parseInt(ssOrderDetails.get(i).getAmount()+"");

                ssPaymentPoints.setTotalPoints(totalPoint);
                ssPaymentPoints.setCustomerId(Integer.parseInt(ssOrderInfo.getCustomerId()));


                if (RC.PAY_YES.equals(ssOrderInfo.getPayStatus())) { //已支付状态
                    if (ssGoodsInfo.getGoodsTypeId() == 1) { //类型为水票
                        //得到水票总数据条数
                        int totalCustomerTicket = num * (ssGoodsInfo.getTicketNum());

                        LocalDateTime createTime = LocalDateTime.now(); //创建时间

                        for (int j = 0; j < totalCustomerTicket; j++) {
                            SsCustomerTicket ssCustomerTicket = new SsCustomerTicket();
                            ssCustomerTicket.setCustomerId(ssOrderInfo.getCustomerId());

                            Integer ticketCode = getTicketCode();
                            ssCustomerTicket.setTicketCode(ticketCode + "");
                            ssCustomerTicket.setUseStatus(1);
                            ssCustomerTicket.setTicketId(ssGoodsInfo.getTicketId());
                            ssCustomerTicket.setCreateTime(createTime);
                            ssOrderInfoMapper.saveCustomerTicket(ssCustomerTicket);
                        }
                    }

                }


                ssOrderInfoMapper.updateOrderInfoByOrderNum(orderNum, status);//支付后，修改订单状态
                ssPaymentPointsMapper.insertPaymentPoints(ssPaymentPoints);


            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }

    }
}
