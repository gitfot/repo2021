package com.fun.ds.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fun.ds.annotations.SelectDataSource;
import com.fun.ds.dsenum.DataSourceEnum;
import com.fun.ds.entity.Sales;
import org.apache.ibatis.annotations.Mapper;

/**
 * (sales)Mapper接口
 *
 * @author zz
 * @since 2021-10-18 18:01:24
 */
@Mapper
@SelectDataSource(value = DataSourceEnum.DS1)
public interface SalesMapper extends BaseMapper<Sales> {
	/**
	 * 自定义分页
	 */
	//List<SalesVO> selectSalesPage(IPage<SalesVO> page, @Param("ew") Wrapper<?> wrapper);
}
