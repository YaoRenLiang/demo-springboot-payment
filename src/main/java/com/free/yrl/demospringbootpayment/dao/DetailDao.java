package com.free.yrl.demospringbootpayment.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.free.yrl.demospringbootpayment.entity.DetailEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 流水单
 *
 * @author 姚壬亮
 **/
@Mapper
public interface DetailDao extends BaseMapper<DetailEntity> {

	@Insert({"<script>",
			" INSERT INTO Detail VALUES ",
			" <foreach collection='list' item='item' separator=','> ",
			" 	(#{item.id},NOW(),NOW(),#{item.streamId},#{item.sn},#{item.myOrderId},#{item.captureId},#{item.currencyCode},#{item.amount}) ",
			" </foreach> ",
			"</script>"})
	Integer batchInsert(@Param("list") List<DetailEntity> detailEntityList);

	@Update({"<script>",
			" <foreach collection='list' item='item' separator=';'> ",
			" 	UPDATE Detail SET updateTime=NOW() ",
			" 	<if test='item.streamId != null'> AND streamId=#{item.streamId} </if> ",
			" 	<if test='item.myOrderId != null'> AND myOrderId=#{item.myOrderId} </if> ",
			" 	<if test='item.captureId != null'> AND captureId=#{item.captureId} </if> ",
			" 	<if test='item.sn != null'> AND sn=#{item.sn} </if> ",
			" 	<if test='item.currencyCode != null'> AND currencyCode=#{item.currencyCode} </if> ",
			" 	<if test='item.amount != null'> AND amount=#{item.amount} </if> ",
			" 	WHERE 1=1 AND id=#{item.id} ",
			" </foreach> ",
			"</script>"})
	Integer batchUpdateById(@Param("list") List<DetailEntity> detailEntityList);

	@Update({"<script>",
			" <foreach collection='list' item='item' separator=';'> ",
			" 	UPDATE Detail SET updateTime=NOW() AND captureId=#{item.captureId} ",
			" 	WHERE 1=1 AND myOrderId=#{item.myOrderId} ",
			" </foreach> ",
			"</script>"})
	Integer batchUpdateCaptureIdByMyOrderId(@Param("list") List<DetailEntity> detailEntityList);

}
