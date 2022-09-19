package com.rachein.mmzf.core.controller;

import com.alibaba.fastjson.JSON;
import com.rachein.mmzf.core.service.IUserService;
import com.rachein.mmzf.entity.DB.User;
import com.rachein.mmzf.entity.DTO.NewsContentDTO;
import com.rachein.mmzf.entity.DTO.NewsReleaseDTO;
import com.rachein.mmzf.entity.RO.UserInfoRo;
import com.rachein.mmzf.entity.RO.UserSettingRo;
import com.rachein.mmzf.exception.GlobalException;
import com.rachein.mmzf.result.CodeMsg;
import com.rachein.mmzf.result.Result;
import com.rachein.mmzf.utils.AccessTokenUtil;
import com.rachein.mmzf.utils.HttpRequestUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author 吴远健
 * @since 2022-08-06
 */
@RestController
@RequestMapping("user")
@Slf4j
public class UserController {

    @Value("${wechat.appid}")
    private String appid;

    @Value("${wechat.appsecret}")
    private String appSecret;

    @Autowired
    private IUserService userService;

    @ApiOperation("填写完整信息")
    @PostMapping("/fill")
    public Result<Object> fillInfo(@RequestBody UserInfoRo userInfoRo) {
        UserSettingRo info = userInfoRo.getInfo();
        //校验是否存在该openID用户
        User user;
        user = userService.lambdaQuery().eq(User::getOpenid, userInfoRo.getOpenId()).one();
        if (Objects.isNull(user)) {
            throw new GlobalException(CodeMsg.NOT_FOUND);
        }
        //保存信息
        BeanUtils.copyProperties(userInfoRo, user);
        userService.lambdaUpdate().eq(User::getOpenid, userInfoRo.getOpenId()).set(User::getTagId, info.getTagId()).update();
        //成功，就向微信服务器调用发送模板消息接口:
        NewsReleaseDTO dto = new NewsReleaseDTO();
        dto.setTouser(userInfoRo.getOpenId());//设置发送对象
        dto.setUrl("https://baidu.com");
        dto.setTemplate_id("WDVyckXN5lcKjbw3_SduMGgRfwhaPUhu4Q1tBfPzsMU");
        //设置消息的内容:
        NewsContentDTO contentDTO = new NewsContentDTO();
        contentDTO.setKeyWord("个人信息填写完成", info.getNickname(), info.getGraduateSchool(), info.getMajority());
        dto.setData(contentDTO);
        String s = JSON.toJSONString(dto);
        log.info(s);
        //请求转发:
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + AccessTokenUtil.getToken();
        try {
            Response response = HttpRequestUtil.post(url, dto);
            if (response.isSuccessful()) {
                String responseJson = response.body().string();
                System.out.println(responseJson);
                if (!JSON.parseObject(responseJson).getString("errcode").equals("0")) {
                    throw new GlobalException(CodeMsg.ERROR_SERVER);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new GlobalException(CodeMsg.ERROR_SERVER);
        }
        return Result.success("填写个人信息成功!");
    }


    @ApiOperation("通过 code 换取网页授权access_token")
    @GetMapping("access/{code}")
    public Result<String> getAcTByCode(@PathVariable String code) {
        String openid = null;
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid + "&secret="+appSecret+"&code=" + code + "&grant_type=authorization_code";
//        System.out.println(url);
        try {
            Response response = HttpRequestUtil.get(url);
            if (response.isSuccessful()) {
                String responseJson = response.body().string();
                openid = JSON.parseObject(responseJson).getString("openid");
//                System.out.println(responseJson);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.success("success", openid);
    }
}
