package com.rachein.mmzf.queue.user;

import com.alibaba.fastjson.JSON;
import com.rachein.mmzf.core.service.IUserService;
import com.rachein.mmzf.entity.DB.User;
import com.rachein.mmzf.utils.AccessTokenUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

@Slf4j
@WebListener
public class UserQueue implements ServletContextListener {

    //存放用户的openid的阻塞队列
    public static final BlockingDeque<String> QUEUE = new LinkedBlockingDeque<>();
    //监听阻塞队列的线程数量：
    private static final int THREAD_COUNT = 10;
    public static String getUserInfoUrl;
    public static IUserService userService;
    /**
     * 监听堵塞队列，异步执行任务
     */
    public static void listen() {
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        while (true) {
                            //从队列中获取数据；
                            String openid = QUEUE.take();
                            System.out.println("获取到的openid：**************>>> " + openid);
                            String accessToken = AccessTokenUtil.getToken();
                            //中转：
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder().url(getUserInfoUrl + "?access_token=" +accessToken + "&openid=" + openid + "&lang=zh_CN").build();
                            Response response = client.newCall(request).execute();
                            //
                            if (response.isSuccessful()) {
//                                System.out.println(response.body().string());
                                //save:
                                String json = response.body().string();
                                System.out.println(json);
                                User user = JSON.parseObject(json, User.class);
                                log.info("数据库添加 <<<< " + user.toString());
                                userService.save(user);
                            }
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                        log.error("异步处理openid失败。。。");
                    }
                }
            });
            thread.start();
        }
    }
}
