package com.rachein.mmzf.utils;


import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @Author 吴远健
 * @Desc 中转请求 - 工具类
 */
public class HttpRequestUtil {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");//用来文件IO交互的 文件格式声明
    private static final OkHttpClient client = new OkHttpClient();//全局变量，生成一次就行了

    /**
     *
     * 【请求类型】 POST
     * @param url 接口地址
     * @param json  需要传的参数 （已经进行JSON 转化 即 Object -> String）
     * @return Response 响应体 其中可以通过response.body().getString() 获取响应回来的参数
     * @throws IOException
     *
     */
    public static Response post(String url, String json) throws IOException {
        //设置请求体：
        RequestBody body = RequestBody.create(JSON, json);
        //创建请求
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        //tips: 一次请求 需要的东西 是： 接口地址url + 请求参数（如果是POST请求） 因为是链式编程，所以开发方便很多😋
        return client.newCall(request).execute();//执行
    }


    /**
     * 【请求类型】 POST
     * @param url
     * @param o 没有预先进行JSON 转换，所以相比较上面的 这里就多了一步 json格式转换
     * @return response
     * @throws IOException
     *
     */
    public static Response post(String url, Object o) throws IOException {
        String json = com.alibaba.fastjson.JSON.toJSONString(o);
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return client.newCall(request).execute();
    }


    //GET

    /**
     * 【请求类型】 GET
     * @param url 其中参数夹在url中
     * @return response
     * @throws IOException
     */
    public static Response get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        return client.newCall(request).execute();
    }

    //Post + file:

    /**
     * 【请求类型】 POST
     * @param url 接口地址
     * @param multipartFile 一种文件传输的类型，可以试试自己用File类也可以的，不过标准情况下，我们传输MultipartFile的格式
     *                      可以试试File 转换 MultipartFile 互相转换，自己探索一下😋
     * @param fileName 参数名称
     * @return response
     * @throws IOException
     */
    public static Response post(String url, MultipartFile multipartFile, String fileName) throws IOException {
        File file = MultipartToFileUtil.transferToFile(multipartFile);//在这里进行转换，也可以参数直接传File类，试试自己修改一下
        //设置请求体
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//设置文件的格式
                .addFormDataPart("media", file.getName(),//注意，这里的参数名字需要自己根据需求进行修改，可以进行改造
                        RequestBody.create(MediaType.parse("multipart/form-data"), file))//设置参数
                .build();
//        System.out.println(url);
        //设置请求：一直是url + 请求参数（因为是post请求）
        Request request = new Request.Builder().url(url)
                .post(requestBody)
                .build();
        return client.newCall(request).execute();
    }
}
