package com.chenlong.common.test;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpTest {

    public static void main(String[] args) throws IOException {
        //创建一个 httpCilent
        HttpClient httpClient = new DefaultHttpClient();
        //创建一个请求
        HttpGet httpGet = new HttpGet("http://www.ujiuye.com");
        //发送请求
        HttpResponse response = httpClient.execute(httpGet);
        //获取响应数据
        System.out.println(EntityUtils.toString(response.getEntity(),"gb2312"));


    }

}
