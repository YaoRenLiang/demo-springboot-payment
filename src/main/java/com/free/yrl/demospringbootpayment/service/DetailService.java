package com.free.yrl.demospringbootpayment.service;

import com.free.yrl.demospringbootpayment.entity.DetailEntity;
import com.free.yrl.demospringbootpayment.entity.StreamEntity;
import com.paypal.orders.Order;
import com.paypal.payments.Refund;

import java.util.List;

/**
 * @author 姚壬亮
 **/
public interface DetailService {

	Integer insert(DetailEntity detailEntity);

	Integer batchInsert(List<DetailEntity> detailEntityList);

	Integer batchUpdateById(List<DetailEntity> detailEntityList);

	Integer batchUpdateCaptureIdByMyOrderId(List<DetailEntity> detailEntityList);

}
