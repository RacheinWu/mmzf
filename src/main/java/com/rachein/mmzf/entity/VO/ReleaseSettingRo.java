package com.rachein.mmzf.entity.VO;

import com.rachein.mmzf.entity.DB.MediaID;
import lombok.Data;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/8/25
 * @Description
 */
@Data
public class ReleaseSettingRo {
    private String tag;
    private MediaID media_id;
}
