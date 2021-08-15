package com.jy.common.pojo.goods;

import com.baomidou.mybatisplus.annotation.TableField;
import com.jy.common.pojo.base.IdFieldEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * sku实体类
 *
 * @author jinchunzhao
 * @version 1.0
 * @date 2021-08-15 13:14
 */
@Data
@Table(name="tb_sku")
public class Sku extends IdFieldEntity {

	@ApiModelProperty(value = "商品条码")
	@TableField(value = "sn")
	private String sn;

	@ApiModelProperty(value = "SKU名称")
	@TableField(value = "name")
	private String name;

	@ApiModelProperty(value = "价格（元）")
	@TableField(value = "price")
	private BigDecimal price;

	@ApiModelProperty(value = "库存数量")
	@TableField(value = "num")
	private Integer num;

	@ApiModelProperty(value = "库存预警数量")
	@TableField(value = "alert_num")
	private Integer alertNum;

	@ApiModelProperty(value = "商品图片")
	@TableField(value = "image")
	private String image;

	@ApiModelProperty(value = "商品图片列表")
	@TableField(value = "images")
	private String images;

	@ApiModelProperty(value = "重量（克）")
	@TableField(value = "weight")
	private Integer weight;

	@ApiModelProperty(value = "商品状态 1-正常，2-下架，3-删除")
	@TableField(value = "status")
	private String status;




}
