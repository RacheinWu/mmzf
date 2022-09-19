package com.rachein.mmzf.config;

import com.rachein.mmzf.core.service.IUserService;
import com.rachein.mmzf.queue.user.UserQueue;
import com.rachein.mmzf.redis.RedisService;
import com.rachein.mmzf.utils.AccessTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class InitialAutoRoboter {


    @Value("${wechat.appid}")
    private String appid;
    @Value("${wechat.appsecret}")
    private String appsecret;
    @Value("${wechat.token-url}")
    private String getTokenUrl;
    @Value("${wechat.user-info-url}")
    private String userInfoUrl;


    @Autowired
    private RedisService redisService;

    @Autowired
    private IUserService userService;

    @Bean
    public void run() {
        log.info(">>>>>>>>>>>>>>>>> 读取配置中...");
        System.out.println("***-> appid = " + appid);
        System.out.println("***-> appsecret = " + appsecret);
        System.out.println("***-> getTokenUrl = " + getTokenUrl);
        System.out.println("***-> userInfoUrl = " + userInfoUrl);
        AccessTokenUtil.appsecret = appsecret;
        AccessTokenUtil.getTokenUrl = getTokenUrl;
        AccessTokenUtil.appid = appid;
        AccessTokenUtil.redisService = redisService;
        UserQueue.userService = userService;
        //
        UserQueue.getUserInfoUrl = userInfoUrl;
        log.info("<<<<<<<<<<<<<<<<<< 已从配置文件中读取配置!");
        //
        UserQueue.listen();
        log.info("<<<<<<<<<<<<<<<<<< 用户监听队列已开启....");

    }
}
