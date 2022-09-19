package com.rachein.mmzf.core.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rachein.mmzf.utils.AccessTokenUtil;
import com.rachein.mmzf.utils.MultipartToFileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import okhttp3.*;
import okhttp3.RequestBody;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/8/9
 * @Description
 */
@RestController
@Api(tags = "【图文消息】文章 模块")
public class ArticleController {

    private String url = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=";

    @ApiOperation(value = "上传图文消息内的图片获取URL【订阅号与服务号认证后均可用】",
            tags = "请注意，本接口所上传的图片不占用公众号的素材库中图片数量的5000个的限制。图片仅支持jpg/png格式，大小必须在1MB以下。")
    @PostMapping("/upload")
    /**
     * @Param
     * @return url
     */
    public Map<String, String> uploadImg(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        File file = MultipartToFileUtil.transferToFile(multipartFile);
        OkHttpClient client = new OkHttpClient();
        String accessToken = AccessTokenUtil.getToken();
        String url = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=" + accessToken;
        try {
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", file.getName(),
                            RequestBody.create(MediaType.parse("multipart/form-data"), file))
                    .build();
            Request request = new Request.Builder().url(url + accessToken)
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String json = response.body().string();
                JSONObject jsonObject = JSON.parseObject(json);
                url = jsonObject.getString("url");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, String> map = new HashMap<>();
        map.put("url", url);
        return map;
    }

    @ApiOperation("对针对的用户进行发送图文")
    @PostMapping("/group/release")
    public void releaseToOpenID() {
        //获取对应的发送标签
        //从数据库中查询得到list
        //对应发送
        //返回发送结果
    }

    @ApiOperation("获取待确定发送的图文列表")
    @GetMapping("/draft/all")
    public void getDraftList() {

    }

    @ApiOperation("根据图文id获取详情信息")
    @GetMapping("/{media_id}")
    public void getInfo(@PathVariable String media_id) {

    }

    @ApiOperation("删除草稿中的图文")
    @GetMapping("/delete/{media_id}")
    public void delete(@PathVariable String media_id) {
        //Sa-token
    }

    @ApiOperation("修改草稿中的图文内容")
    @PostMapping("/draft/update/{media_id}")
    public void update(@PathVariable String media_id) {

    }

    @ApiOperation("向所有群体发送图文")
    @PostMapping("/release")
    public void release() {

    }

}
