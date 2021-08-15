package com.jy.common.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jy.common.pojo.base.IdFieldEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * 用户
 *
 * @author jinchunzhao
 * @version 1.0
 * @date 2021-08-14 10:21
 */
@Data
@TableName(value = "user")
public class User extends IdFieldEntity {


    @NotBlank(message = "用户名不能为空")
    @Length(max = 25, message = "用户名字符长度不能超过25位")
    @ApiModelProperty(value = "用户昵称")
    @TableField(value = "user_name")
    private String userName;

    @NotBlank(message = "用户密码不能为空")
    @Length(max = 25, message = "用户密码字符长度不能超过25位")
    @Pattern(message = "密码必须是包含大小写字母、数字、特殊符号的8位以上组合",regexp = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\\\W]+$)(?![A-Za-z\\\\W]+$)(?![A-Z0-9\\\\W]+$)[a-zA-Z0-9\\\\W]{8,25}$")
    @ApiModelProperty(value = "用户密码")
    @TableField(value = "password")
    private String password;

    @NotBlank(message = "用户手机号不能为空")
    @Length(min = 11, max = 11, message = "用户手机号符长度必须是11位")
    @Pattern(message = "手机号格式不正确",regexp = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$")
    @ApiModelProperty(value = "用户手机号")
    @TableField(value = "tel")
    private String tel;

    @ApiModelProperty(value = "注册时间")
    @TableField(value = "reg_time")
    private Date regTime;

}
