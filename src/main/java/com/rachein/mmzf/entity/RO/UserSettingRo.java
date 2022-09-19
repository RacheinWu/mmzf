package com.rachein.mmzf.entity.RO;

import lombok.Data;

import java.util.Date;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/8/11
 * @Description
 */
@Data
public class UserSettingRo {
    private String inSchool;
    private String graduateSchool;
    private Long academyId;
    private String majority;
    private Integer nationalityId;
    private Integer identityState;
    private String nickname;
    private String gender;
    private String contact;
    private Date birthday;
    private String address;
    private String tagId;
}
