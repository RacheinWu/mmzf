package com.rachein.mmzf.utils;

import com.alibaba.fastjson.JSON;
import com.rachein.mmzf.redis.RedisService;
import com.rachein.mmzf.redis.myPrefixKey.ACTokenKey;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
public class AccessTokenUtil {

//    @Autowired
    public static RedisService redisService;

//    @Value("${wechat.appid}")
    public static String appid;
//    @Value("${wechat.appsecret}")
    public static String appsecret;
//    @Value("${wechat.token-url}")
    public static String getTokenUrl;


    public synchronized static String getToken() {
        String accessToken = null;
        accessToken = redisService.get(ACTokenKey.accessToken, ACTokenKey.NAME, String.class);
        //如果token不存在于缓存:
        if (StringUtils.isBlank(accessToken)) {
            //请求中转:
            String url = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=" + accessToken;
            try {
                Response response = HttpRequestUtil.get(url);
                //access_token是否有效
                if (!response.isSuccessful() || !isEffective(accessToken)) {
                    //无效：那么就刷新：
                    accessToken = getTokenNet();
                    log.info("*>>[access_token] 刷新 >> " + accessToken);
                    //放入redis中刷新：
                    redisService.set(ACTokenKey.accessToken, ACTokenKey.NAME ,accessToken);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (!isEffective(accessToken)) {
                //无效
                accessToken = getTokenNet();
                log.info("*>>[access_token] 刷新 >> " + accessToken);
                //放入redis中刷新：
                redisService.set(ACTokenKey.accessToken, ACTokenKey.NAME ,accessToken);
            }
        }
        return accessToken;
    }

    //OkHttp 请求 获得access_token
    private static String getTokenNet() {
        Response response = null;
        String accessToken = null;
        try {
            response = HttpRequestUtil.get(getTokenUrl + "?grant_type=client_credential&appid=" + appid + "&secret=" + appsecret);
            if (response.isSuccessful()) {
                String json = response.body().string();
                if (json.contains("errcode")) {
                    //失败：
                    log.error("【错误】获取access_token 失败 -> 中转请求失败...");
                }
                else {
                    //success:
                    accessToken = JSON.parseObject(json).getString("access_token");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accessToken;
    }

    //校验access_token是否有效
    private static Boolean isEffective(String accessToken) {
        String url = "https://api.weixin.qq.com/cgi-bin/get_api_domain_ip?access_token=" + accessToken;
        try {
            Response response = HttpRequestUtil.get(url);
            String json = response.body().string();
            if (json.contains("ip_list")) {
                log.info("【access-token】可用");
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
