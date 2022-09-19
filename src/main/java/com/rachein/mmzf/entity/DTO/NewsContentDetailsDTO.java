package com.rachein.mmzf.entity.DTO;

import lombok.Data;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/9/1
 * @Description "first": {
 *                        "value":"恭喜你购买成功！",
 *                        "color":"#173177"
 *                    },
 */
@Data
public class NewsContentDetailsDTO {
    private String value;
    private String color = "#173177";

    public NewsContentDetailsDTO() {
    }

    public NewsContentDetailsDTO(String value) {
        this.value = value;
    }
}
