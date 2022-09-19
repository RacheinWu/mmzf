package com.rachein.mmzf.entity.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/8/27
 * @Description
 */
@Data
public class NewsReleaseDTO {
    private String touser;
    private String template_id;
    @ApiModelProperty("模板跳转链接（海外帐号没有跳转能力）")
    private String url;
    @ApiModelProperty("跳小程序所需数据，不需跳小程序可不用传该数据")
    private MiniProgramDTO miniprogram;
    @ApiModelProperty("防重入id。对于同一个openid + client_msg_id, 只发送一条消息,10分钟有效,超过10分钟不保证效果。若无防重入需求，可不填")
    private String client_msg_id;
    @ApiModelProperty(value = "模板数据", notes = "示例：\"keyword3\": {\n" +
            "                       \"value\":\"2014年9月22日\",\n" +
            "                       \"color\":\"#173177\"\n" +
            "                   },")
//    private Map<String, String> data;
    private NewsContentDTO data;
}
