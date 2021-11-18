package org.fun.test.contractType.controller;

import lombok.RequiredArgsConstructor;
import org.fun.test.contractType.base.ResultMap;
import org.fun.test.contractType.entity.ContractTypeClassListEntity;
import org.fun.test.contractType.entity.ContractTypeEntity;
import org.fun.test.contractType.entity.ServicePlanContractListEntity;
import org.fun.test.contractType.service.IServicePlanService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author wanwan 2021/11/1
 */
@RequestMapping("/servicePlan")
@RequiredArgsConstructor
@RestController
public class ContractTypeController {

	private final IServicePlanService servicePlanService;

	@PostMapping("/getContractTypeList")
	public Map getServicePlanTypeList(@RequestBody ContractTypeEntity contractTypeEntity){
		Map resultMap = ResultMap.creatResultMap(3)
			.successBuilder()
			.putAndReturn("contractTypeList", servicePlanService.getServicePlanContractTypeList(contractTypeEntity))
			.buildWithResult();
		return  resultMap;
	}

	@PostMapping("/getServicePlanContractList")
	public Map getServicePlanContractList(@RequestBody ServicePlanContractListEntity servicePlanContractListEntity){
		Map resultMap = ResultMap.creatResultMap(5)
			.successBuilder()
			.putAllAndReturn(servicePlanService.getServicePlanContractList(servicePlanContractListEntity))
			.buildWithResult();
		return  resultMap;
	}

	/**
	 * 获取服务计划分类列表
	 */
	@PostMapping("/getContractTypeClassList")
	public Map getContractTypeClassList(@RequestBody(required = false) ContractTypeClassListEntity contractTypeClassListEntity){
		Map resultMap = ResultMap.creatResultMap(3)
			.successBuilder()
			.putAndReturn("list", servicePlanService.getServicePlanContractTypeClassList(contractTypeClassListEntity))
			.buildWithResult();
		return  resultMap;
	}
}
