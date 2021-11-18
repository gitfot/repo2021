package org.fun.test.handset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wanwan 2021/11/8
 */
@Service
public class HandsetRateplanService {

	@Autowired
	private HandsetRateplanMapper iHandsetRateplanDao;

	public GetHandsetRateplanDetailResponse getHandsetRateplanDetail() {

		//查询productNode
		GetHandsetRateplanDetailResponse handsetRateplanDetail = iHandsetRateplanDao.getHandsetRateplanDetail();
		return handsetRateplanDetail;
	}

	public GetHandsetDetailOfCustResponse getHandsetDetailOfCust() throws Exception {
		GetHandsetDetailOfCustResponse handsetDetailOfCust = iHandsetRateplanDao.getHandsetDetailOfCust();
		return handsetDetailOfCust;
	}
}
