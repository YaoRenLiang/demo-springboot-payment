package com.free.yrl.demospringbootpayment.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.free.yrl.demospringbootpayment.entity.StreamEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 订单流水表
 *
 * @author 姚壬亮
 **/
@Mapper
public interface StreamDao extends BaseMapper<StreamEntity> {


}