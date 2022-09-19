package com.rachein.mmzf.core.controller;

import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.TypeReference;
import com.rachein.mmzf.entity.DB.MediaID;
import com.rachein.mmzf.entity.DTO.GetArticleListDTO;
import com.rachein.mmzf.entity.RO.ArticlesAddRo;
import com.rachein.mmzf.entity.RO.GetArticleListRo;
import com.rachein.mmzf.entity.VO.ArticleInfoVo;
import com.rachein.mmzf.entity.VO.DraftListVo;
import com.rachein.mmzf.exception.GlobalException;
import com.rachein.mmzf.result.CodeMsg;
import com.rachein.mmzf.result.Result;
import com.rachein.mmzf.utils.AccessTokenUtil;
import com.rachein.mmzf.utils.HttpRequestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/8/9
 * @Description
 */
@Api(tags = "图文草稿箱模块")
@RestController
@RequestMapping("api/draft")
public class DraftController {

    @Value("${setting.page.count}")
    private String pageCount;

    @ApiOperation("添加文章草稿")
    @PostMapping("/add")
    public Result<MediaID> addArticleToDraw(@RequestBody ArticlesAddRo articles) {
        String s = JSON.toJSONString(articles);
        System.out.println(s);
        String accessToken = AccessTokenUtil.getToken();
        //http 请求方式：POST（请使用 https 协议）https://api.weixin.qq.com/cgi-bin/draft/add?access_token=ACCESS_TOKEN
        String url = "https://api.weixin.qq.com/cgi-bin/draft/add?access_token=" + accessToken;
        Response response = null;
        MediaID mediaID = null;
        try {
            response = HttpRequestUtil.post(url, JSON.toJSONString(articles));
            if (response.isSuccessful()) {
                //请求成功:
                String json = response.body().string();
                System.out.println(json );
                if (json.contains("errcode")) {
                    throw new GlobalException(CodeMsg.DRAFT_SAVE_ERROR);
                }
                mediaID = JSON.parseObject(json, MediaID.class);
                System.out.println(json);
                //备份到数据库中：
                System.out.println(mediaID);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new GlobalException(CodeMsg.DRAFT_SAVE_ERROR);
        }
        return Result.success("文章已添加到草稿箱中！", mediaID);
    }

    @ApiOperation("获取mediaId的文章info")
    @PostMapping("/get")
    public Result<ArrayList<ArticleInfoVo>> get(@RequestBody MediaID mediaID) {
        //http 请求方式：POST（请使用 https 协议）https://api.weixin.qq.com/cgi-bin/draft/get?access_token=ACCESS_TOKEN
        String url = "https://api.weixin.qq.com/cgi-bin/draft/get?access_token=" + AccessTokenUtil.getToken();
        try {
            Response response = HttpRequestUtil.post(url, mediaID);
            if (response.isSuccessful()) {
                String json = response.body().string();
                System.out.println(json);
                String news_item = (String) JSON.parseObject(json).get("news_item");
                ArrayList<ArticleInfoVo> articleInfoVos = JSON.parseObject(news_item, new TypeReference<ArrayList<ArticleInfoVo>>(){});
                return Result.success("success", articleInfoVos);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new GlobalException(CodeMsg.NOT_FOUND);
        }
        return null;
    }

    @ApiOperation("获取草稿箱list【分页】")
    @GetMapping("/list/{page}")
    public Result<ArrayList<DraftListVo>> list(@PathVariable("page") Integer page) {
        //http 请求方式：POST（请使用 https 协议）https://api.weixin.qq.com/cgi-bin/draft/batchget?access_token=ACCESS_TOKEN
        GetArticleListDTO dto = new GetArticleListDTO();
        //将page转换为 开头下标：
        Integer count = Integer.parseInt(pageCount);
        int head = (page - 1) * count;
        dto.setOffset(head);//设置开头索引
        dto.setCount(count);
        String url = "https://api.weixin.qq.com/cgi-bin/draft/batchget?access_token=" + AccessTokenUtil.getToken();
        try {
            Response responses = HttpRequestUtil.post(url, dto);
            if (responses.isSuccessful()) {
                String responseJson = responses.body().string();
                System.out.println(responseJson);
                String item = JSON.parseObject(responseJson).getString("item");
                ArrayList<DraftListVo> draftListVos = JSON.parseObject(item, new TypeReference<ArrayList<DraftListVo>>() {
                });
                return Result.success("success", draftListVos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void remove() {
        //http 请求方式：POST（请使用 https 协议）https://api.weixin.qq.com/cgi-bin/draft/delete?access_token=ACCESS_TOKEN
    }

    public void modify() {
        //http 请求方式：POST（请使用 https 协议）https://api.weixin.qq.com/cgi-bin/draft/update?access_token=ACCESS_TOKEN
    }

    @ApiOperation("获取总数")
    @GetMapping("/getTotal")
    public void getTotal() {
        String url = "https://api.weixin.qq.com/cgi-bin/draft/count?access_token=" + AccessTokenUtil.getToken();
        try {
            Response response = HttpRequestUtil.get(url);
            String responseJson = response.body().string();
            System.out.println(responseJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
