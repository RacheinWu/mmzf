package com.rachein.mmzf.redis.myPrefixKey;

import com.rachein.mmzf.redis.BasePrefix;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/8/18
 * @Description
 */
public class SettingKey extends BasePrefix {

    private static final int TOKEN_EXPIRE = -1;
    public static final String SUBSCRIBE_REPLY_KEY = "SUBSCRIBE_REPLY_KEY";
    /**
     * 防止被外面实例化
     * @param expireSeconds
     * @param prefix
     */
    private SettingKey(int expireSeconds, String prefix) {super(expireSeconds, prefix);}

    /**
     * 需要缓存的字段：
     */
    public static SettingKey subscribe_reply = new SettingKey(TOKEN_EXPIRE,"subscribe-reply");

    /**
     * 需要缓存的字段：
     */
//    public static SettingKey subscribe_reply = new SettingKey(TOKEN_EXPIRE,"subscribe-reply");
}
