package com.cmtt.base;

import com.cmtt.base.config.ss.configuration.JwtAuthenticationToken;
import com.cmtt.base.entity.SsOrderDetail;
import com.cmtt.base.entity.SsOrderInfo;
import com.cmtt.base.entity.SysUser;
import com.cmtt.base.mapper.SqlMapper;
import com.cmtt.base.mapper.SsCustomerAddressMapper;
import com.cmtt.base.mapper.SsCustomerInfoMapper;
import com.cmtt.base.service.ISsCustomerAddressService;
import com.cmtt.base.service.ISsOrderInfoService;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest
class SsOrderTests {


    CloseableHttpClient httpClient;
    @Resource
    SsCustomerAddressMapper ssCustomerAddressMapper;
    @Resource
    SsCustomerInfoMapper ssCustomerInfoMapper;
    @Autowired
    public ISsOrderInfoService ssOrderInfoService;

    @Test
    void contextLoads() {
        try {
//            Map<String,Object> params = new HashMap<>();
//            params.put("sql","select * from ss_customer_info where customer_name like #{code}");
//            params.put("code","张%");
//            List<Map<String, Object>> ans = sqlMapper.sqltest(params);
//            if (Objects.nonNull(ans) && ans.size() > 0){
//                System.out.println(ans.get(0));
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @Test
    void saveAllOrderInfo(){ //查询用户对应的地址

        SsOrderInfo ssOrderInfo = new SsOrderInfo();

        String orderNum = String.valueOf(System.currentTimeMillis()/ 1000); //订单编号

        String  openID = "1";
        int stationId = 1;
        double totalPrice = 30;
        String description ="test";
        int status = 1;
        LocalDateTime createTime = LocalDateTime.now(); //创建时间

        List<SsOrderDetail> ssOrderDetails = new ArrayList<SsOrderDetail>();

        SsOrderDetail ssOrderDetail1 = new SsOrderDetail();
        SsOrderDetail ssOrderDetail2 = new SsOrderDetail();
        SsOrderDetail ssOrderDetail3 = new SsOrderDetail();
        int goodsId1 = 3;
        int num1  = 1;
        double price1 = 30;
        String memo1 = "test";
        String payWay1 = "1";
        double amount1=30;

        int goodsId2 = 2;
        int num2  = 2;
        double price2 = 16;
        String memo2 = "test2";
        String payWay2 = "2";
        double amount2=32;

        //加入类型为水票
        int goodsId3 = 28;
        int num3  = 2;
        double price3 = 16;
        String memo3 = "测试类型为水票";
        String payWay3 = "2";
        double amount3=32;

        int addressId = 1;

        ssOrderDetail1.setOrderNumber(orderNum);
        ssOrderDetail1.setGoodsId(goodsId1);
        ssOrderDetail1.setNum(num1);
        ssOrderDetail1.setPrice(price1);
        ssOrderDetail1.setMemo(memo1);
       // ssOrderDetail1.setPayWay(payWay1);
        ssOrderDetail1.setAmount(amount1);
        ssOrderDetail1.setAddressId(addressId);
        ssOrderDetail1.setStationId(stationId);

        ssOrderDetail2.setOrderNumber(orderNum);
        ssOrderDetail2.setGoodsId(goodsId2);
        ssOrderDetail2.setNum(num2);
        ssOrderDetail2.setPrice(price2);
        ssOrderDetail2.setMemo(memo2);
        //ssOrderDetail2.setPayWay(payWay2);
        ssOrderDetail2.setAmount(amount2);
        ssOrderDetail2.setAddressId(addressId);
        ssOrderDetail2.setStationId(stationId);

        ssOrderDetail3.setOrderNumber(orderNum);
        ssOrderDetail3.setGoodsId(goodsId3);
        ssOrderDetail3.setNum(num3);
        ssOrderDetail3.setPrice(price3);
        ssOrderDetail3.setMemo(memo3);
        //ssOrderDetail3.setPayWay(payWay3);
        ssOrderDetail3.setAmount(amount3);
        ssOrderDetail3.setAddressId(addressId);
        ssOrderDetail3.setStationId(stationId);

        ssOrderDetails.add(ssOrderDetail1);
        ssOrderDetails.add(ssOrderDetail2);
        ssOrderDetails.add(ssOrderDetail3);

        ssOrderInfo.setOrderStatus(status); //1-待分配，2-配送中，3-已完成，4-已取消
        ssOrderInfo.setOrderDate(createTime);
        ssOrderInfo.setAppointTime(createTime);
        ssOrderInfo.setOrderNumber(orderNum);
        ssOrderInfo.setCustomerId(openID);
        ssOrderInfo.setTotalPrice(totalPrice);
        ssOrderInfo.setDescription(description);
        ssOrderInfo.setSsOrderDetails(ssOrderDetails);

      //  ssOrderInfoService.saveAllOrderInfo(ssOrderInfo);

    }

    /**
     * 创建日期为2019年2月14日并且直属上级姓名为王姓
     * date_format(create_time,'%Y-%m-%d') and manager_id in (select id from user where name like '王%')
     */
    @Test
   public void selectByWrapper2(){
       /* Page<Map<String, Object>> page = ssCustomerAddressService.selectListPage(1, 2,"1");
        List list = page.getRecords();
        list.forEach(System.out::println);*/
    }

    @Before
    public void setup() throws IOException {

    }

}
