package com.huahua.sms.send;

import com.huahua.sms.utils.HttpUtils;
import org.apache.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public class SmsSendCode {
    public static void sendCode(String appcode,String mobile,String tplId,String code) {
        String host = "http://dingxintz.market.alicloudapi.com";
        String path = "/dx/notifySms";
        String method = "POST";
       // String appcode = "1700bd3f5847403fbe29e5ded27be5d2";
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization","APPCODE"+appcode);
        Map<String, String> querys = new HashMap<>();
        querys.put("mobile",mobile);
        querys.put("param","name:"+code);
        querys.put("tpl_id",tplId);
        Map<String, String> bodys = new HashMap<>();
        HttpResponse response = null;
        try {
            response = HttpUtils.doPost(host,path,method,headers,querys,bodys);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(response.toString());
    }
}
