package com.rachein.mmzf.entity.RO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/8/25
 * @Description
 */
@Data
public class ArticleRemoveRo {
    private String article_id;
    @ApiModelProperty("要删除的文章在图文消息中的位置，第一篇编号为1，该字段不填或填0会删除全部文章")
    private Integer index;
}
