package com.cmtt.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmtt.base.entity.SsCustomerAddress;
import com.cmtt.base.entity.SsCustomerInfo;
import com.cmtt.base.mapper.SqlMapper;
import com.cmtt.base.mapper.SsCustomerAddressMapper;
import com.cmtt.base.mapper.SsCustomerInfoMapper;
import com.cmtt.base.service.ISsCustomerAddressService;
import com.cmtt.base.utils.JwtUtils;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.security.Signature;
import java.util.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.PrivateKey;

@SpringBootTest
class SsCustomerTests {


    CloseableHttpClient httpClient;
    @Resource
    SsCustomerAddressMapper ssCustomerAddressMapper;
    @Resource
    SsCustomerInfoMapper ssCustomerInfoMapper;
    @Autowired
    public ISsCustomerAddressService ssCustomerAddressService;

    @Autowired
    SqlMapper sqlMapper;
    @Test
    void contextLoads() {
        try {
            Map<String,Object> params = new HashMap<>();
            params.put("sql","select * from ss_customer_info where customer_name like #{code}");
            params.put("code","张%");
            List<Map<String, Object>> ans = sqlMapper.sqltest(params);
            if (Objects.nonNull(ans) && ans.size() > 0){
                System.out.println(ans.get(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @Test
    void findCustomerAddressList(){ //查询用户对应的地址

       /* QueryWrapper<SsCustomerAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply("status={0}","100")
                .inSql("customer_id","select id from ss_customer_info where customer_id = 1");
        List<SsCustomerAddress> userList = ssCustomerAddressMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
*/
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
