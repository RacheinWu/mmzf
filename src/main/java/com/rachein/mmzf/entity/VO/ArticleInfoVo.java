package com.rachein.mmzf.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/8/25
 * @Description
 */
@Data
public class ArticleInfoVo {
    private String title;
    private String author;
    private String digest;
    private String content;
    private String content_source_url;
    private String thumb_media_id;
    private Integer show_cover_pic;
    private Integer need_open_comment;
    private Integer only_fans_can_comment;
    @ApiModelProperty("草稿的临时链接")
    private String url;
}
