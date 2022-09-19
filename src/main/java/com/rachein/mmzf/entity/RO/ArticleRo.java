package com.rachein.mmzf.entity.RO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/8/10
 * @Description
 */
@Data
public class ArticleRo {
    private String title;
    private String author;
    @ApiModelProperty("图文消息的摘要，仅有单图文消息才有摘要，多图文此处为空。如果本字段为没有填写，则默认抓取正文前54个字。")
    private String digest;
    @ApiModelProperty("图文消息的具体内容，支持 HTML 标签，必须少于2万字符，小于1M，且此处会去除 JS ,涉及图片 url 必须来源 \"上传图文消息内的图片获取URL\"接口获取。外部图片 url 将被过滤。")
    private String content;
    @ApiModelProperty("图文消息的原文地址，即点击“阅读原文”后的URL")
    private String content_source_url;
    @ApiModelProperty("图文消息的封面图片素材id（必须是永久MediaID）")
    private String thumb_media_id;
    @ApiModelProperty("Uint32 是否打开评论，0不打开(默认)，1打开")
    private Integer need_open_comment;
    @ApiModelProperty("Uint32 是否粉丝才可评论，0所有人可评论(默认)，1粉丝才可评论")
    private Integer only_fans_can_comment;
//    @ApiModelProperty("是否显示封面，1为显示，0为不显示")
//    private Integer show_cover_pic;
}
