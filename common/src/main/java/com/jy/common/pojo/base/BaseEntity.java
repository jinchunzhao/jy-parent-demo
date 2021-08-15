package com.jy.common.pojo.base;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 基础entity，无ID的表继承该类
 * 
 * @author jinchunzhao
 * @version 1.0
 * @date 2021-07-31 15:13
 */
@Getter
@Setter
public class BaseEntity implements Serializable {

    /**
     * 创建人
     */
    @ApiModelProperty(value = "记录创建ren")
    @TableField(value = "create_user_id", fill = FieldFill.INSERT)
    private Long createUserId;

    /**
     * 最后操作人id
     */
    @ApiModelProperty(value = "最后操作人id")
    @TableField(value = "update_user_id", fill = FieldFill.INSERT_UPDATE)
    private Long updateUserId;

    /**
     * 记录创建时间，即本条记录的新增时间
     */
    @ApiModelProperty(value = "记录创建时间，即本条记录的新增时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 最后操作时间，本条记录的新增、修改动作，即更新本时间戳
     */
    @ApiModelProperty(value = "最后操作时间，本条记录的新增、修改动作，即更新本时间戳")
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;

}
