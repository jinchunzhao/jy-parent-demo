package com.jy.common.pojo.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * identity，有ID的表继承该类
 * 
 * @author jinchunzhao
 * @version 1.0
 * @date 2021-07-31 15:14
 */
@Setter
@Getter
public class IdFieldEntity extends BaseEntity {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    Long id;
}
