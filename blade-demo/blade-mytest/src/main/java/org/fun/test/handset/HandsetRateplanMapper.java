package org.fun.test.handset;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author wanwan 2021/11/8
 */
@Mapper
public interface HandsetRateplanMapper extends BaseMapper<GetHandsetDetailOfCustResponse> {

	GetHandsetRateplanDetailResponse getHandsetRateplanDetail();

	GetHandsetDetailOfCustResponse getHandsetDetailOfCust();
}
