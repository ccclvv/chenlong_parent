package com.chenlong.user.component;

import com.chenlong.common.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component //这个对象交给容器管理
public class SmsTemplate {

    @Value("${sms.host}")
    private String host;
    @Value("${sms.path}")
    private String path;
    @Value("${sms.method}")
    private String method;
    @Value("${sms.appcode}")
    private String appcode;

    //这里定义一个方法  用户调用这个方法就可以发送短信
    public String sendSms(String mobile, String code){
        Map<String, String> headers = new HashMap();
        headers.put("Authorization", "APPCODE " + appcode);

        Map<String, String> query = new HashMap<>();
        query.put("mobile", mobile);
        query.put("param", "code:" + code);
        query.put("tpl_id", "TP1711063");

        HttpResponse response = null;
        try {
            response = HttpUtils.doPost(host, path, method, headers, query, "");
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
            return "fali";
        }

    }

}
