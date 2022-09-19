package com.rachein.mmzf.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/8/18
 * @Description 菜单按钮
 */
@Getter
public enum MenuType {
    DESCRIPTION(0, "description"),
    SINGLE(1, "single"),
    MULTI(2, "multi"),
    SELECT(3, "select"),
    TEXT(4, "text"),
    TEXTAREA(5, "textarea"),
    RADIO(6, "radio"),
    MATRIX_RADIO(7, "matrix_radio"),
    CHECKBOX(8, "checkbox");

    @EnumValue
    private final Integer val;

    @JsonValue
    private final String desc;

    MenuType(Integer val, String desc) {
        this.val = val;
        this.desc = desc;
    }
}
