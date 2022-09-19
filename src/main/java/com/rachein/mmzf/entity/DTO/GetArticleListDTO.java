package com.rachein.mmzf.entity.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/8/25
 * @Description
 */
@Data
public class GetArticleListDTO {
    @ApiModelProperty("从全部素材的该偏移位置开始返回，0表示从第一个素材返回")
    private Integer offset;
    private Integer count;
    @ApiModelProperty("1 表示不返回 content 字段，0 表示正常返回，默认为 1")
    private Integer no_content = 1;
}
