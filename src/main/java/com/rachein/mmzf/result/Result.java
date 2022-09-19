package com.rachein.mmzf.result;

import lombok.Data;

/**
 * @author 计算机系 ITAEM 吴远健
 * @Description
 * @date 2022/5/14 17:14
 */
@Data
public class Result<T> {

    private Integer code;
    private String msg = "";
    private T data;


    /**
     * 成功返回的参数类
     * @param msg 成功消息
     * @param data 成功所返回的数据
     * @param <T>
     * @return
     */
    //带参数
    public static <T> Result<T> success(String msg, T data) {return new Result<>(msg, data);}

    //不带参数
    public static <T> Result<T> success(String msg) {return new Result<>(msg);}

    /**
     * 失败返回的参数类
     * @param codeMsg
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(CodeMsg codeMsg) {return new Result<>(codeMsg);}

    //success
    private Result(String msg, T data) {
        this.msg = msg;
        this.data = data;
        this.code = 200;
    }

    private Result(String msg) {
        this.msg = msg;
        this.code = 200;
    }

    private Result(CodeMsg codeMsg) {
        this.msg = codeMsg.getMsg();
        this.code = codeMsg.getCode();
    }
}
