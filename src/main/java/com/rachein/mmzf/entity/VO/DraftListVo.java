package com.rachein.mmzf.entity.VO;

import lombok.Data;

import java.util.Date;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/8/25
 * @Description
 */
@Data
public class DraftListVo {
    private String article_id;
    private String media_id;
    private ArticleContentVo content;
    private String update_time;
}
