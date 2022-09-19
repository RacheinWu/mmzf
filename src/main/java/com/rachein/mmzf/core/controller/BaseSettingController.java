package com.rachein.mmzf.core.controller;

import com.rachein.mmzf.entity.RO.SettingSubscribeRo;
import com.rachein.mmzf.redis.RedisService;
import com.rachein.mmzf.redis.myPrefixKey.SettingKey;
import com.rachein.mmzf.result.Result;
import com.rachein.mmzf.utils.AccessTokenUtil;
import com.rachein.mmzf.utils.HttpRequestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/8/18
 * @Description
 */
@RestController
@Api(tags = "基础设置模块")
@RequestMapping("api/setting")
public class BaseSettingController {

    @Autowired
    private RedisService redisService;

    @ApiOperation("菜单设置")
    @PostMapping("/menu")
    public void menu() {
        String json = "{" +
                "     \"button\":[" +
                "     {\t" +
                "          \"type\":\"click\"," +
                "          \"name\":\"填写个人消息\"," +
                "          \"key\":\"V1001_TODAY_MUSIC\"" +
                "      }," +
                "      {" +
                "           \"name\":\"菜单\"," +
                "           \"sub_button\":[" +
                "           {\t" +
                "               \"type\":\"view\"," +
                "               \"name\":\"搜索\"," +
                "               \"url\":\"http://www.soso.com/\"" +
                "            }," +
                "            {" +
                "               \"type\":\"click\"," +
                "               \"name\":\"赞一下我们\"," +
                "               \"key\":\"V1001_GOOD\"" +
                "            }]" +
                "       }]" +
                " }";
        String url = " https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + AccessTokenUtil.getToken();
        try {
            HttpRequestUtil.post(url, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation("添加消息模板")
    @PostMapping("/news/model/add")
    public void addNewModel() {

    }

    @ApiOperation("设置订阅公众号后的行为")
    @PostMapping("/subscribe")
    public Result<Object> subscribe(@RequestBody SettingSubscribeRo subscribeRo) {
        String content = subscribeRo.getContent();
        //存入缓存中
        redisService.set(SettingKey.subscribe_reply, SettingKey.SUBSCRIBE_REPLY_KEY, content);
        return Result.success("设置成功");
    }



}
