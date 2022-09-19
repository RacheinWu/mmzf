package com.rachein.mmzf.entity.DB;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author 吴远健
 * @since 2022-08-06
 */
@Data
@TableName("t_user")
@ApiModel(value = "User对象", description = "用户")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    @TableField(value = "gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;

    @ApiModelProperty("人群标签id")
    @TableField("tag_id")
    private String tagId;

    @ApiModelProperty("图片存储路径")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty("微信联动")
    @TableField("openid")
    private String openid;

    @ApiModelProperty("在读院校")
    @TableField("in_school")
    private String inSchool;

    @ApiModelProperty("毕业院校")
    @TableField("graduate_school")
    private String graduateSchool;

    @ApiModelProperty("专业大类id")
    @TableField("academy_id")
    private Integer academyId;

    @ApiModelProperty("专业名称")
    @TableField("majority")
    private String majority;

    @ApiModelProperty("状态: 0/1 -> 不正常/正常")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("民族")
    @TableField("nationality_id")
    private Integer nationalityId;

    @ApiModelProperty("身份地位")
    @TableField("identity_state")
    private Integer identityState;

    @ApiModelProperty("姓名")
    @TableField("nickname")
    private String nickname;

    @ApiModelProperty("性别")
    @TableField("gender")
    private String gender;

    @ApiModelProperty("联系方式")
    @TableField("contact")
    private String contact;

    @TableField("birthday")
    private LocalDate birthday;

    @ApiModelProperty("籍贯")
    @TableField("address")
    private String address;

    @ApiModelProperty("是否管理员？ 0/1 -> 否/是")
    @TableField("is_admin")
    private Integer isAdmin;

    @ApiModelProperty("订阅时间")
    @TableField("subscribe_time")
    private String subscribeTime;


}
