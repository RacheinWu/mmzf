package com.rachein.mmzf.controller;

import com.rachein.mmzf.core.service.IUserService;
import com.rachein.mmzf.queue.user.UserQueue;
import com.rachein.mmzf.redis.RedisService;
import com.rachein.mmzf.redis.myPrefixKey.SettingKey;
import com.rachein.mmzf.utils.AccessTokenUtil;
import com.rachein.mmzf.utils.MessageUtil;
import com.rachein.mmzf.utils.XmlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@RestController
@Slf4j
public class VXController {

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisService redisService;

    @GetMapping("/wechat")
    //微信认证测试
    public String check(String signature, String timestamp, String nonce, String echostr) {
        System.out.println(signature);
        return echostr;
    }

    @PostMapping(value = "/wechat", produces = {"application/xml;charset=utf-8"})
    public Object doRun(HttpServletRequest request) throws IOException {
        Map<String, String> map = XmlUtil.getMap(request.getInputStream());
        String msgType = map.get("MsgType");
        String fromUserName = map.get("FromUserName");
        String toUserName = map.get("ToUserName");
        String reply = "";
        if (msgType.equals("event")) {
            String event = map.get("Event");
            //订阅
            if (event.equals("subscribe")) {
                reply = "欢迎订阅。。。。。点击此处进行完善身份信息，这样才能获取个性化的推荐信息哦！<a href =\"http://cn-hk-nf-1.natfrp.cloud:32064/i/fillInfo.html\">【点击此处】</a>";
//                reply = redisService.get(SettingKey.subscribe_reply, SettingKey.SUBSCRIBE_REPLY_KEY, String.class);
                System.out.println(reply);
                log.info(fromUserName);
                UserQueue.QUEUE.push(fromUserName);
            }
            else if (event.equals("unsubscribe")) {
                reply = "触发不再关注事件";
                userService.removeByOpenId(fromUserName);
            }
        }
        return MessageUtil.formatMsg(fromUserName, toUserName, 1412, "text", reply);
    }


    @GetMapping("/test/acc")
    public void d() {
        AccessTokenUtil.getToken();
    }
}