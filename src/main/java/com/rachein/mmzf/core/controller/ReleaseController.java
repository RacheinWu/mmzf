package com.rachein.mmzf.core.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.rachein.mmzf.core.service.IUserService;
import com.rachein.mmzf.entity.DB.User;
import com.rachein.mmzf.entity.DTO.GetArticleListDTO;
import com.rachein.mmzf.entity.DTO.ReleaseMediaDTO;
import com.rachein.mmzf.entity.RO.ArticleRemoveRo;
import com.rachein.mmzf.entity.RO.GetArticleListRo;
import com.rachein.mmzf.entity.VO.ArticleInfoVo;
import com.rachein.mmzf.entity.VO.ReleaseSettingRo;
import com.rachein.mmzf.exception.GlobalException;
import com.rachein.mmzf.result.CodeMsg;
import com.rachein.mmzf.result.Result;
import com.rachein.mmzf.utils.AccessTokenUtil;
import com.rachein.mmzf.utils.HttpRequestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import okhttp3.Response;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/8/9
 * @Description
 */
@Api(tags = "发布 | 成功后的管理 模块")
@RestController
@RequestMapping("api/article")
public class ReleaseController {

    @Autowired
    private IUserService userService;

    @Value("${setting.page.count}")
    private String pageCount;

    @ApiOperation("预览接口【订阅号与服务号认证后均可用】")
    @PostMapping("/preview")
    public void preview(@RequestBody ReleaseMediaDTO releaseMediaDTO) {
//        ReleaseMediaDTO releaseMediaDTO = new ReleaseMediaDTO();
//        releaseMediaDTO.setTouser(new String[]{"o611U6tHLILanViepWbx27xQ3X40"});
//        releaseMediaDTO.setMsgtype("mpnews");
//        MediaID mediaID = new MediaID();
//        mediaID.setMedia_id("vdqyELzdJq8cs1MEr91z5OCCsHQUg1Dyasosr4bgaCVugigCZrjkf78-LDg2kioB");
        System.out.println(releaseMediaDTO);
//        releaseMediaDTO.setMpnews(mediaID);
        String json = JSON.toJSONString(releaseMediaDTO);
        Response response = null;
        try {
            response = HttpRequestUtil.post("https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=" + AccessTokenUtil.getToken(), json);
            if (response.isSuccessful()) {
                //成功！
                System.out.println(response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation("根据 OpenID 列表群发【订阅号不可用，服务号认证后可用】")
    @PostMapping("/release")
    public Result<Object> send(@RequestBody ReleaseSettingRo releaseSettingRo) {
        System.out.println(releaseSettingRo);
        String tag = releaseSettingRo.getTag();
        if (StringUtils.isBlank(tag)) throw new GlobalException(CodeMsg.ERROR_SERVER);
        //http请求方式: POST https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN
        String url = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=" + AccessTokenUtil.getToken();
        //从数据库中获取：
        String[] toUsers = userService.lambdaQuery()
                .eq(User::getTagId, tag)
                .list()
                .stream()
                .map(User::getOpenid)
                .toArray(String[]::new);
        System.out.println(Arrays.toString(toUsers));
//        String[] toUsers ={"o611U6tHLILanViepWbx27xQ3X40", "o611U6vzNzV6s8PGec-WUcPgvFhs"};
        ReleaseMediaDTO releaseMediaDTO = new ReleaseMediaDTO();
        releaseMediaDTO.setTouser(toUsers);
        releaseMediaDTO.setMpnews(releaseSettingRo.getMedia_id());
        //中转请求 发送:
        try {
            Response response = HttpRequestUtil.post(url, releaseMediaDTO);
            if (response.isSuccessful()) {
                String responseJson = response.body().string();
                System.out.println(responseJson);
                if (!JSON.parseObject(responseJson).get("errcode").equals(0)) {
                    throw new GlobalException(CodeMsg.ARTICLE_RELEASE);
                }
            }
        } catch (IOException e) {
            throw new GlobalException(CodeMsg.ARTICLE_RELEASE);
        }
        return Result.success("发布成功!");

    }

    @ApiOperation("获取发送成功的列表")
    @GetMapping("success/all/{page}")
    public Result<ArrayList<ArticleInfoVo>> listSuccess(@PathVariable Integer page) {
        String url = "https://api.weixin.qq.com/cgi-bin/freepublish/batchget?access_token=" + AccessTokenUtil.getToken();

        GetArticleListDTO dto = new GetArticleListDTO();
        Integer count = Integer.parseInt(pageCount);
        int head = (page - 1) * count;
        dto.setCount(count);
        dto.setOffset(0);

        try {
            Response response = HttpRequestUtil.post(url, dto);
            if (response.isSuccessful()) {
                String responseJson = response.body().string();
                System.out.println(responseJson);
                ArrayList<ArticleInfoVo> articleInfoVos = JSON.parseObject(responseJson, new TypeReference<ArrayList<ArticleInfoVo>>() {
                });
                return Result.success("success", articleInfoVos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @ApiOperation(value = "删除群发【订阅号与服务号认证后均可用】",notes = "请注意：\n" +
            "\n" +
            "只有已经发送成功的消息才能删除\n" +
            "删除消息是将消息的图文详情页失效，已经收到的用户，还是能在其本地看到消息卡片。\n" +
            "删除群发消息只能删除图文消息和视频消息，其他类型的消息一经发送，无法删除。\n" +
            "如果多次群发发送的是一个图文消息，那么删除其中一次群发，就会删除掉这个图文消息页，导致所有群发都失效\n" +
            "此操作不可逆，请谨慎操作。"
    )
    @PostMapping("success/delete")
    public Result<Object> delete(@RequestBody ArticleRemoveRo removeRo) {
        String url = "https://api.weixin.qq.com/cgi-bin/freepublish/delete?access_token=" + AccessTokenUtil.getToken();
        try {
            Response response = HttpRequestUtil.post(url, removeRo);
            if (response.isSuccessful()) {
                String responseJson = response.body().string();
                Integer errcode = (Integer)JSON.parseObject(responseJson).get("errcode");
                if (errcode == 0) {
                    return Result.success("删除成功！");
                } else {
                    throw new GlobalException(CodeMsg.ARTICLE_DELETE);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new GlobalException(CodeMsg.ARTICLE_DELETE);
        }
        return null;
    }
}
