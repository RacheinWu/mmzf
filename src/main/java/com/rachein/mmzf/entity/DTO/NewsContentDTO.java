package com.rachein.mmzf.entity.DTO;

import lombok.Data;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/9/1
 * @Description
 */
@Data
public class NewsContentDTO {
    private NewsContentDetailsDTO first;
    private NewsContentDetailsDTO keyword1;
    private NewsContentDetailsDTO keyword2;
    private NewsContentDetailsDTO keyword3;

    public void setKeyWord(String... args) {
        first = new NewsContentDetailsDTO(args[0]);
        keyword1 = new NewsContentDetailsDTO(args[1]);
        keyword2 = new NewsContentDetailsDTO(args[2]);
        keyword3 = new NewsContentDetailsDTO(args[3]);
    }
}
