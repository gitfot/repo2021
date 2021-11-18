package org.fun.test.order.service;

import org.fun.test.order.entity.GetOutletAddressEntity;
import org.fun.test.order.entity.WareHouseEntity;

import java.util.Map;

public interface OrderService {

	Map getOutletAddressList(GetOutletAddressEntity baseInputModel);

	Map getRetrieveMIAPProductList(WareHouseEntity baseInputModel) throws Exception;

}
