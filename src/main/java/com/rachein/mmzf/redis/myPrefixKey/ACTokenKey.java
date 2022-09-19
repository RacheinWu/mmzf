package com.rachein.mmzf.redis.myPrefixKey;

import com.rachein.mmzf.redis.BasePrefix;

public class ACTokenKey extends BasePrefix {

    public static final int TOKEN_EXPIRE = 7200;
    public static final String NAME = "access_token";

    /**
     * 防止被外面实例化
     * @param expireSeconds
     * @param prefix
     */
    private ACTokenKey(int expireSeconds, String prefix) {super(expireSeconds, prefix);}

    /**
     * 需要缓存的字段：
     */
    public static ACTokenKey accessToken = new ACTokenKey(TOKEN_EXPIRE,"access-token");

//    public static ACTokenKey getById = new ACTokenKey(TOKEN_EXPIRE,"user-id");

}
