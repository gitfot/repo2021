package org.fun.test.order.service.impl;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.fun.test.order.base.ResultEnum;
import org.fun.test.order.base.ResultMap;
import org.fun.test.order.entity.*;
import org.fun.test.order.mapper.OutletsMapper;
import org.fun.test.order.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wanwan 2021/10/15
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OutletsMapper outletsMapper;

	/**
	 * 获取门店三级地址
	 */
	@Override
	public Map getOutletAddressList(GetOutletAddressEntity baseInputModel) {
		if (CollectionUtils.isEmpty(baseInputModel.getProductCodeCount())) {
			return ResultMap.builder(ResultEnum.ERROR_INPUT,3).buildWithResult();
		}

		Map<String,Object> paramMap = new HashMap<>();

		List<ProductCodeCount> productCodeCount = baseInputModel.getProductCodeCount();

		List<String> shopIdList = null;
		List<Map<String,String>> outletAddressList = null;
		//循环校验全部商品的库存
		for (ProductCodeCount codeCount : productCodeCount) {
			paramMap.put("lang", baseInputModel.getLang());
			paramMap.put("productCode",codeCount.getProductCode());
			paramMap.put("count",codeCount.getCount());
			paramMap.put("shopIdList", shopIdList);
			outletAddressList = outletsMapper.getAllOutLetAddress(paramMap);
			if (CollectionUtils.isEmpty(outletAddressList)) {
				//如果有一个产品没库存，则返回null
				outletAddressList = null;
				break;
			}
			shopIdList = outletAddressList.stream().map( o -> o.get("code3")).collect(Collectors.toList());
		}


//		paramMap.put("lang", baseInputModel.getLang());
//		paramMap.put("productCode",baseInputModel.getProductCodeCount().get(0).getProductCode());
//		paramMap.put("count",baseInputModel.getProductCodeCount().get(0).getCount());
//		List<Map<String,String>> outletAddressList = outletsMapper.getAllOutLetAddress(paramMap);

		if (CollectionUtils.isEmpty(outletAddressList)) {
			return ResultMap.builder(ResultEnum.SUCCESS,3).buildWithResult();
		}
		//构造三级地址结构
		List<OutletAddressNode> outletAddressNodeList = new ArrayList<>();
		for (int i = 0; i < outletAddressList.size(); i++) {
			if (i==0 || !outletAddressList.get(i-1).get("code1").equals(outletAddressList.get(i).get("code1"))) {
				OutletAddressNode firstNode = new OutletAddressNode();
				firstNode.setCode(outletAddressList.get(i).get("code1"));
				firstNode.setLabel(outletAddressList.get(i).get("label1"));

				List<OutletAddressNode> secondNodeList = new ArrayList<>();
				OutletAddressNode secondNode = new OutletAddressNode();
				secondNode.setCode(outletAddressList.get(i).get("code2"));
				secondNode.setLabel(outletAddressList.get(i).get("label2"));
				secondNodeList.add(secondNode);
				firstNode.setNodeList(secondNodeList);

				List<OutletAddressNode> thirdNodeList = new ArrayList<>();
				OutletAddressNode thirdNode = new OutletAddressNode();
				thirdNode.setCode(outletAddressList.get(i).get("code3"));
				thirdNode.setLabel(outletAddressList.get(i).get("label3"));
				thirdNodeList.add(thirdNode);
				secondNode.setNodeList(thirdNodeList);

				outletAddressNodeList.add(firstNode);
			}

			else if (! outletAddressList.get(i-1).get("code2").equals(outletAddressList.get(i).get("code2"))) {
				//List<OutletAddressNode> secondNodeList = new ArrayList<>();
				OutletAddressNode secondNode = new OutletAddressNode();
				secondNode.setCode(outletAddressList.get(i).get("code2"));
				secondNode.setLabel(outletAddressList.get(i).get("label2"));
				//secondNodeList.add(secondNode);

				List<OutletAddressNode> thirdNodeList = new ArrayList<>();
				OutletAddressNode thirdNode = new OutletAddressNode();
				thirdNode.setCode(outletAddressList.get(i).get("code3"));
				thirdNode.setLabel(outletAddressList.get(i).get("label3"));
				thirdNodeList.add(thirdNode);
				secondNode.setNodeList(thirdNodeList);
				//追加二级节点
				OutletAddressNode outletAddressNode = Iterables.getLast(outletAddressNodeList);
				List<OutletAddressNode> oldSecondNodeList = outletAddressNode.getNodeList();
				oldSecondNodeList.add(secondNode);
			}

			else {
				OutletAddressNode thirdNode = new OutletAddressNode();
				thirdNode.setCode(outletAddressList.get(i).get("code3"));
				thirdNode.setLabel(outletAddressList.get(i).get("label3"));
				//追加三级节点
				List<OutletAddressNode> secondList = Iterables.getLast(outletAddressNodeList).getNodeList();
				OutletAddressNode secondNode = Iterables.getLast(secondList);
				List<OutletAddressNode> oldSecondNodeList = secondNode.getNodeList();
				oldSecondNodeList.add(thirdNode);
			}
		}
		HashMap<Object, Object> resultMap = new HashMap<>();
		resultMap.put("outletNode", outletAddressNodeList);
		return ResultMap.builder(ResultEnum.SUCCESS,3).putAllAndReturn(resultMap).buildWithResult();
	}

	/**
	 * 查询商品库存信息
	 */
	@Override
	public Map getRetrieveMIAPProductList(WareHouseEntity entity) throws Exception {
		String[] wareHouseCodeList = entity.getWarehouseCode().split(",");
		List<Product> pro = outletsMapper.getRetrieveMIAPProductList(entity, wareHouseCodeList);

		if (CollectionUtils.isEmpty(pro)) {
			return ResultMap.builder(ResultEnum.SUCCESS,1).buildWithResult();
		}

		StringBuilder outletQuantity = new StringBuilder();
		for (int i = 0; i < pro.size(); i++) {
			outletQuantity.append(pro.get(i).getOutletQuantity());
			if (i != pro.size() -1) {
				outletQuantity.append(",");
			}
		}

		Integer stockCount = 0;
		for (int i = 0; i < pro.size(); i++) {
			String[] split = pro.get(i).getOutletQuantity().split(":");
			stockCount += Integer.parseInt(split[1]);
		}

		Map<String,Object> map = Maps.newHashMapWithExpectedSize(1);
		map.put("hsId",pro.get(0).getHsId());
		map.put("outletQuantity", outletQuantity);
		map.put("productCode",pro.get(0).getProdcode());
		map.put("hasStock", stockCount >= entity.getProductQuantity() ? "Y" : "N");
		return ResultMap.builder(ResultEnum.SUCCESS,3).putAllAndReturn(map).buildWithResult();
	}
}
