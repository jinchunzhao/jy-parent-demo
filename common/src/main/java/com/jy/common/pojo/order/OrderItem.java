package com.jy.common.pojo.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.jy.common.pojo.base.IdFieldEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * orderItem实体类
 *
 * @author jinchunzhao
 * @version 1.0
 * @date 2021-08-14 21:01
 */
@Data
@Table(name="tb_order_item")
public class OrderItem extends IdFieldEntity {


	@ApiModelProperty(value = "分类")
	@TableField(value = "category_id")
	private Long categoryId;

	@ApiModelProperty(value = "SPU_ID")
	@TableField(value = "spu_id")
	private Long spuId;

	@ApiModelProperty(value = "SKU_ID")
	@TableField(value = "sku_id")
	private Long skuId;

	@ApiModelProperty(value = "订单ID")
	@TableField(value = "order_id")
	private Long orderId;

	@ApiModelProperty(value = "商品名称")
	@TableField(value = "name")
	private String name;

	@ApiModelProperty(value = "单价")
	@TableField(value = "price")
	private BigDecimal price;

	@ApiModelProperty(value = "数量")
	@TableField(value = "num")
	private Integer num;

	@ApiModelProperty(value = "总金额")
	@TableField(value = "money")
	private BigDecimal money;

	@ApiModelProperty(value = "实付金额")
	@TableField(value = "payMoney")
	private BigDecimal payMoney;

	@ApiModelProperty(value = "图片地址")
	@TableField(value = "image")
	private String image;

	@ApiModelProperty(value = "重量")
	@TableField(value = "weight")
	private BigDecimal weight;

	@ApiModelProperty(value = "运费")
	@TableField(value = "post_fee")
	private BigDecimal postFee;

	@ApiModelProperty(value = "是否退货")
	@TableField(value = "is_return")
	private String isReturn;




}
