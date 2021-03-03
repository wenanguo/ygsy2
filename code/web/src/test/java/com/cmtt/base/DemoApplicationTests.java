package com.cmtt.base;

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
import java.security.Signature;
import java.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.PrivateKey;

//@SpringBootTest
class DemoApplicationTests {

    CloseableHttpClient httpClient;

    @Test
    void contextLoads() {

    }


    @Test
    void test1(){



    }

    @Before
    public void setup() throws IOException {
       
        }

}
