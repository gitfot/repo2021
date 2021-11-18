package org.fun.test.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.fun.test.order.entity.Product;
import org.fun.test.order.entity.ProductCodeCount;
import org.fun.test.order.entity.WareHouseEntity;

import java.util.List;
import java.util.Map;

/**
 * (outlets)Mapper接口
 *
 * @author zz
 * @since 2021-10-15 00:43:58
 */
@Mapper
public interface OutletsMapper extends BaseMapper<ProductCodeCount> {

	List<Map<String,String>> getAllOutLetAddress(Map map);

	List<Product> getRetrieveMIAPProductList(@Param("wareHouse") WareHouseEntity wareHouse, @Param("array") String[] wareHouseCodeList);
}
