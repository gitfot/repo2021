/*
 * Project: cmhk-admin
 *
 * File Created at 2018-12-25
 *
 * Copyright 2016 CMCC Corporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * ZYHY Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license.
 */
package com.fun.ds.mapper;

import com.fun.ds.annotations.SelectDataSource;
import com.fun.ds.dsenum.DataSourceEnum;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Desc StockMapper
 * @author luolei
 * @date 2019-03-25 10:01
 * @version
 */
@Mapper
@SelectDataSource(value = DataSourceEnum.DS1)
public interface StockMapper {

    List<Map<String,String>> getAllOutLetAddress(Map map);

    List<Map<String,String>> getOutLetDistrict(Map map);

}
