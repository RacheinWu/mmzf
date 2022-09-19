package com.rachein.mmzf.entity.VO;

import lombok.Data;

import java.util.List;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/8/25
 * @Description
 */
@Data
public class ArticleContentVo {
    private List<ArticleInfoVo> news_item;
    private String create_time;
    private String update_time;
}
