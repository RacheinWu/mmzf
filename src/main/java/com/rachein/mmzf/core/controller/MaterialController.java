package com.rachein.mmzf.core.controller;

import com.alibaba.fastjson.JSON;
import com.rachein.mmzf.entity.DB.MediaID;
import com.rachein.mmzf.exception.GlobalException;
import com.rachein.mmzf.result.CodeMsg;
import com.rachein.mmzf.result.Result;
import com.rachein.mmzf.utils.AccessTokenUtil;
import com.rachein.mmzf.utils.HttpRequestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import okhttp3.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/8/9
 * @Description
 */
@Api(tags = "图片视频素材模块")
@RestController
@RequestMapping("api/material")
public class MaterialController {

    @ApiOperation(value = "新增其他类型永久素材", notes = "最近更新：永久图片素材新增后，将带有 URL 返回给开发者，开发者可以在腾讯系域名内使用（腾讯系域名外使用，图片将被屏蔽）。\n" +
            "\n" +
            "2、公众号的素材库保存总数量有上限：图文消息素材、图片素材上限为100000，其他类型为1000。\n" +
            "\n" +
            "3、素材的格式大小等要求与公众平台官网一致：\n" +
            "\n" +
            "图片（image）: 10M，支持bmp/png/jpeg/jpg/gif格式\n" +
            "\n" +
            "语音（voice）：2M，播放长度不超过60s，mp3/wma/wav/amr格式\n" +
            "\n" +
            "视频（video）：10MB，支持MP4格式\n" +
            "\n" +
            "缩略图（thumb）：64KB，支持 JPG 格式")
    @PostMapping("upload/{type}")
    public Result<MediaID> upload(@PathVariable String type, @RequestParam("file") MultipartFile multipartFile) {
//        File file = MultipartToFileUtil.transferToFile(multipartFile);
//        OkHttpClient client = new OkHttpClient();
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("media", file.getName(),
//                        RequestBody.create(MediaType.parse("multipart/form-data"), file))
//                .build();
//        String url = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token="+ AccessTokenUtil.getToken() +"&type=" + type;
        String url = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token="+ AccessTokenUtil.getToken() +"&type=" + type;
//        System.out.println(url);
//        Request request = null;
//
//             request = new Request.Builder().url(url)
//                    .post(requestBody)
//                    .build();
        String mediaId = null;
        try {
//            Response response = client.newCall(request).execute();
            Response response = HttpRequestUtil.post(url, multipartFile, "media");
            if (response.isSuccessful()) {
                String json = response.body().string();
                System.out.println(json );
                mediaId = JSON.parseObject(json).getString("media_id");
            }
        } catch (Exception e) {
            if (e instanceof MultipartException) {
                throw new GlobalException(CodeMsg.FILE_FORMAT_ERROR);
            }
            e.printStackTrace();
        }
        MediaID mediaID = new MediaID();
        mediaID.setMedia_id(mediaId);
        return Result.success("上传成功!", mediaID);
    }

    @ApiOperation(value = "上传图文所需要的图片素材", notes = "本接口所上传的图片不占用公众号的素材库中图片数量的100000个的限制。图片仅支持jpg/png格式，大小必须在1MB以下。")
    @PostMapping("/uploadImg")
    public String uploadArticleImg(@RequestParam("file") MultipartFile multipartFile) {
        String url = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=" + AccessTokenUtil.getToken();
        String imgUrl = null;
        try {
            Response response = HttpRequestUtil.post(url, multipartFile, "media");
            if (response.isSuccessful()) {
                String json = response.body().string();
                imgUrl = JSON.parseObject(json).getString("media_id");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imgUrl;
    }
}
