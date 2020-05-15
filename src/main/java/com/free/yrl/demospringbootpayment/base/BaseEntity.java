package com.free.yrl.demospringbootpayment.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * 基础的实体
 *
 * @author 姚壬亮
 **/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {

	@ApiModelProperty(value = "主键")
	@TableId
	Long id;

	@ApiModelProperty(value = "数据插入时间")
	@TableField
	Date insertTime;

	@ApiModelProperty(value = "最近一次更新时间")
	@TableField
	Date updateTime;

}