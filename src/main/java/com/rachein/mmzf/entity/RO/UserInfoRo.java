package com.rachein.mmzf.entity.RO;

import lombok.Data;

import java.util.Date;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/8/11
 * @Description
 */
@Data
public class UserInfoRo {
    private String openId;
    private UserSettingRo info;
}
