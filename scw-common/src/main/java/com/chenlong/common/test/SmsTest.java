package com.chenlong.common.test;

import com.chenlong.common.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

public class SmsTest {

    public static void main(String[] args) throws Exception {
        String host = "http://dingxin.market.alicloudapi.com";
        String path = "/dx/sendSms";
        String method = "POST";
        String appcode = "a1a9fc8b65b647b2a71ce8db0324b312";

        Map<String, String> headers = new HashMap();
        headers.put("Authorization", "APPCODE " + appcode);

        Map<String, String> query = new HashMap<>();
        query.put("mobile", "18703685272");
        query.put("param", "code:4568");
        query.put("tpl_id", "TP1711063");

        HttpResponse response = HttpUtils.doPost(host, path, method, headers, query, "");
        System.out.println(EntityUtils.toString(response.getEntity()));


    }



}
