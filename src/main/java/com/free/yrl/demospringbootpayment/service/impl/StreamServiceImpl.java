package com.free.yrl.demospringbootpayment.service.impl;

import com.free.yrl.demospringbootpayment.base.ResponseMessage;
import com.free.yrl.demospringbootpayment.base.SnowFlake;
import com.free.yrl.demospringbootpayment.constant.ResponseMessageConstants;
import com.free.yrl.demospringbootpayment.constant.StreamStatusConstants;
import com.free.yrl.demospringbootpayment.dao.StreamDao;
import com.free.yrl.demospringbootpayment.entity.StreamEntity;
import com.free.yrl.demospringbootpayment.service.StreamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

import static com.free.yrl.demospringbootpayment.base.ResponseMessage.*;

@Slf4j
@Service
public class StreamServiceImpl implements StreamService {

	@Resource
	private SnowFlake snowFlake;

	@Resource
	private StreamDao streamDao;

	@Override
	public Integer insert(StreamEntity streamEntity) {

		streamEntity.setInsertTime(new Date());
		streamEntity.setUpdateTime(new Date());
		streamEntity.setStatus(StreamStatusConstants.noOk.getKey());
		int successRow = streamDao.insert(streamEntity);
		return cudReturn(successRow);

	}

	@Override
	public Integer updateById(StreamEntity streamEntity) {

		if (streamEntity == null) {
			return 0;
		}
		streamEntity.setUpdateTime(new Date());
		int successRow = streamDao.updateById(streamEntity);
		return cudReturn(successRow);

	}

	@Override
	public Integer close(Long id) {

		int successRow = streamDao.updateById(StreamEntity.builder()
				.id(id)
				.status(StreamStatusConstants.cancel.getKey())
				.build());
		return cudReturn(successRow);

	}

	@Override
	public ResponseMessage<StreamEntity> selectById(Long id) {

		if (id == null) {
			return error(ResponseMessageConstants.SERVICEEXCEPTION);
		}
		return success(streamDao.selectById(id));

	}

}
