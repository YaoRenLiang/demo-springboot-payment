package com.free.yrl.demospringbootpayment.service.impl;

import com.free.yrl.demospringbootpayment.base.SnowFlake;
import com.free.yrl.demospringbootpayment.constant.ResponseMessageConstants;
import com.free.yrl.demospringbootpayment.dao.DetailDao;
import com.free.yrl.demospringbootpayment.entity.DetailEntity;
import com.free.yrl.demospringbootpayment.service.DetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.free.yrl.demospringbootpayment.base.ResponseMessage.cudReturn;

/**
 * 支付服务
 *
 * @author 姚壬亮
 **/
@Slf4j
@Service
public class DetailServiceImpl implements DetailService {

	@Resource
	private SnowFlake snowFlake;

	@Resource
	private DetailDao detailDao;

	@Override
	public Integer insert(DetailEntity detailEntity) {

		if (detailEntity.getId() == null) {
			detailEntity.setId(snowFlake.nextId());
		}
		detailEntity.setInsertTime(new Date());
		detailEntity.setUpdateTime(new Date());
		int successRow = detailDao.insert(detailEntity);
		return cudReturn(successRow);

	}

	@Override
	public Integer batchInsert(List<DetailEntity> detailEntityList) {

		detailEntityList.forEach(detailEntity -> {
			if (detailEntity.getId() == null) {
				detailEntity.setId(snowFlake.nextId());
			}
		});
		int successRow = detailDao.batchInsert(detailEntityList);
		return cudReturn(successRow);

	}

	@Override
	public Integer batchUpdateById(List<DetailEntity> detailEntityList) {

		int successRow = detailDao.batchUpdateById(detailEntityList);
		return cudReturn(successRow);

	}

	@Override
	public Integer batchUpdateCaptureIdByMyOrderId(List<DetailEntity> detailEntityList) {

		int successRow = detailDao.batchUpdateCaptureIdByMyOrderId(detailEntityList);
		return cudReturn(successRow);

	}

}
