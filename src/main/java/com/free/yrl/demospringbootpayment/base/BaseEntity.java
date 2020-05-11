package com.free.yrl.demospringbootpayment.base;

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
	Long id;

	@ApiModelProperty(value = "数据插入时间")
	Date insertTime;

	@ApiModelProperty(value = "最近一次更新时间")
	Date updateTime;

}