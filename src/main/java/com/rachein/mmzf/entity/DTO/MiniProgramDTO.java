package com.rachein.mmzf.entity.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/8/27
 * @Description 跳小程序所需数据，不需跳小程序可不用传该数据
 */
@Data
public class MiniProgramDTO {
    private String appid;
    @ApiModelProperty("所需跳转到小程序的具体页面路径，支持带参数,（示例index?foo=bar），要求该小程序已发布，暂不支持小游戏")
    private String pagepath;
}
