package com.rachein.mmzf;

import com.alibaba.fastjson.JSON;
import com.rachein.mmzf.utils.HttpRequestUtil;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class MmzfApplicationTests {

    @Value("${wechat.appid}")
    private static String appid;
    @Value("${wechat.appsecret}")
    private  String appsecret;
    @Value("${wechat.token-url}")
    private  String getTokenUrl;

    @Test
    void contextLoads() {
        System.out.println(appid);
        System.out.println(appsecret);
        System.out.println(getTokenUrl);
    }

    @Test
    void c() {
        try {
            String json = "{\"type\":\"thump\",\"offset\":0,\"count\":20}";
            Response re = HttpRequestUtil.post("https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=59_yg2nD3Am7aiYUTapmlLzJyUszEyqAtTeJxbK5rt4CVjFFJARKy14JYJPRXsJIRILY98gdnGZXfKu5xOZ5Xo3xA7_C2Sbxw58qdzk4lJb-IvAfBBuroM9nmzL8Seu2ZFVn4jXq8Gd1hxRrCvgGUTeACAJXW", json);
            String string = re.body().string();
            System.out.println(string);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    void d() {
//        String json = ""
//        String j = (String)JSON.parseObject(json).get("total_count");
//        System.out.println(j);
    }

}
