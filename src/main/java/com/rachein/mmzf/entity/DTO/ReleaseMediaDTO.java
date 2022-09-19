package com.rachein.mmzf.entity.DTO;

import com.rachein.mmzf.entity.DB.MediaID;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/8/9
 * @Description 发布media 的设置类
 */
@Data
public class ReleaseMediaDTO {

    @ApiModelProperty("填写图文消息的接收者，一串 OpenID 列表，OpenID最少2个，最多10000个")
    private String[] touser;
    @ApiModelProperty("用于设定即将发送的图文消息, 此处包含了media_id")
    private MediaID mpnews;
    @ApiModelProperty("群发的消息类型，图文消息为mpnews，文本消息为text，语音为voice，音乐为music，图片为image，视频为video，卡券为wxcard, 默认为mapnews")
    private String msgtype = "mpnews";
    @ApiModelProperty("图文消息被判定为转载时，是否继续群发。 1为继续群发（转载），0为停止群发。 该参数默认为1。")
    private Integer send_ignore_reprint = 1;
//    @ApiModelProperty("接收者人群标签")
//    public String target;
}
