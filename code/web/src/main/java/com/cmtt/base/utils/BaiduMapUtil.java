package com.cmtt.base.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 处理百度地图地址转编码、获取经纬度
 *
 * @author yuzm
 * @Date 2021-2-19
 */
public class BaiduMapUtil {
    //秘钥
    private static String AK = "HIztk0ifyOeqlRxuO5KGc5tm";

    public static void main(String[] args) {
        BaiduMapUtil mapUtil = new BaiduMapUtil();


        //mapUtil.parseAddress("贵州省贵阳市观山湖区");
        //mapUtil.parseAddress("贵州省六盘水市水城区");
        //mapUtil.parseAddress("兴义市顶效镇哪叠小学");
        mapUtil.parseAddress("贵州省黔西南布依族苗族自治州兴义市民航大道黔西南民族职业技术学院");   //{"lng":104.92672116540757,"lat":25.09988414396141}

        //mapUtil.change2BaiduLngAndLat(BigDecimal.valueOf(114.21892734521), BigDecimal.valueOf(29.575429778924));//测试经纬度，与转出后的结果相差929.99米

        // mapUtil.change2BaiduLngAndLat(BigDecimal.valueOf(105.00859627157858), BigDecimal.valueOf(25.145466259042455));//测试经纬度，与转出后的结果相差929.99米
    }

    /**
     * 中文地址转换为编码
     * @param address
     */
    public String parseAddress(String address) {
        //String url = "http://api.map.baidu.com/geocoder/v2/?output=json&ak=" + AK + "&address="+address; //V2

        try {
            String url = "http://api.map.baidu.com/geocoding/v3/?address="+address+"&output=json&ret_coordtype=bd09ll&ak=" + AK;
            String content = this.doGetRequest(url);
            Double[] lngAndLat  = this.getLngAndLat(content);

            String url2 = "http://api.map.baidu.com/reverse_geocoding/v3/?output=json&ak=" + AK + "&location="+(lngAndLat[1] + "," + lngAndLat[0]);
            String content2 =  this.doGetRequest(url2);
            String adcode = this.getAdcode(content2);

            System.out.println("Response content: " + content);
            System.out.println("Response content2: " + content2);
            System.out.println("adcode="+adcode);
            return adcode;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private String doGetRequest(String url){
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpget = new HttpGet(url);
            CloseableHttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取行政编码
     *
     * @param result
     * @return
     */
    private String getAdcode(String result) {
        if (!result.isEmpty()) {
            JSONObject jsonObject = JSON.parseObject(result);
            if (jsonObject.getIntValue("status") == 0) {
                JSONObject resultJson = jsonObject.getObject("result", JSONObject.class);
                Map<String, String> address = resultJson.getObject("addressComponent", Map.class);
                return address.get("adcode");
            }
        }
        return null;
    }

    /**
     * 获取经纬度信息
     *
     * @param result
     * @return
     */
    private Double[] getLngAndLat(String result) {
        if (!result.isEmpty()) {
            JSONObject jsonObject = JSON.parseObject(result);
            if (jsonObject.getIntValue("status") == 0) {
                JSONObject resultJson = jsonObject.getObject("result", JSONObject.class);
                JSONObject location = resultJson.getObject("location", JSONObject.class);
                return new Double[]{location.getDouble("lng"), location.getDouble("lat")};
            }
        }
        return null;
    }

    /**
     * 非百度经纬度转成百度经纬度
     * @param lng 非百度经度
     * @param lat 非百度维度
     * @return
     */
    public BigDecimal[] change2BaiduLngAndLat(BigDecimal lng, BigDecimal lat){
        //非百度经纬度转百度经纬度API地址
        String CHANGE_URL = "http://api.map.baidu.com/geoconv/v1/?from=3&to=5&output=json";
        String url = CHANGE_URL + "&ak=" + AK + "&coords=" + lng + "," + lat;

        String content = doGetRequest(url);
        System.out.println("Response content: " + content);
        BigDecimal[] lngAndLat = this.getBaiduLngAndLat(content);
        System.out.println("lngAndLat----\nx=" + lngAndLat[0]+"\ny="+lngAndLat[1]);
        BigDecimal a = lngAndLat[0];
        return lngAndLat;
    }

    /**
     * 获取经纬度信息
     *
     * @param result
     * @return
     */
    private BigDecimal[] getBaiduLngAndLat(String result) {
        Double[] lngAndLat = new Double[1];
        if (!result.isEmpty()) {
            JSONObject jsonObject = JSON.parseObject(result);
            int status = jsonObject.getIntValue("status");
            if (status == 0) {
                JSONArray resultArr = jsonObject.getJSONArray("result");
                JSONObject subObject=resultArr.getJSONObject(0);
                System.out.println("x="+subObject.get("x"));
                System.out.println("y="+subObject.get("y"));
                return new BigDecimal[]{subObject.getBigDecimal("x"), subObject.getBigDecimal("y")};
            }
        }
        return null;
    }
}
