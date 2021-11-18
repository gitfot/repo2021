package org.fun.test.contractType.service.impl;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.fun.test.contractType.base.ResultMap;
import org.fun.test.contractType.dao.IServicePlanContractTypeClassDao;
import org.fun.test.contractType.dao.IServicePlanContractTypeDao;
import org.fun.test.contractType.dao.IServicePlanDao;
import org.fun.test.contractType.dao.MiapOnlineshopContractTypeMapper;
import org.fun.test.contractType.entity.*;
import org.fun.test.contractType.service.IServicePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.*;


/**
 * @author ldk
 * @desc 带机上台服务实现类
 */
@Service
public class ServicePlanServiceImpl implements IServicePlanService {

    @Autowired
    private IServicePlanContractTypeDao servicePlanContractTypeDao;

    @Autowired
    private IServicePlanDao servicePlanDao;

    @Autowired
    private IServicePlanContractTypeClassDao iServicePlanContractTypeClassDao;
    @Autowired
    private MiapOnlineshopContractTypeMapper contractTypeMapper;


    @Override
    public  List<ContractType> getServicePlanContractTypeList(ContractTypeEntity contractTypeEntity) {
		Sqls sqls = Sqls.custom().andEqualTo("display",1)
			.andEqualTo("entryType",contractTypeEntity.getEntryType());
		if (!StringUtils.isBlank(contractTypeEntity.getClassId())) {
			sqls.andEqualTo("classId", Integer.valueOf(contractTypeEntity.getClassId()));
		}
		Example example = Example.builder(ContractType.class).where(sqls).orderByAsc("orderBy").build();
		List<ContractType> contractTypeList = servicePlanContractTypeDao.selectByExample(example);

        return contractTypeList;
    }

    @Override
    public List<ContractTypeClass> getServicePlanContractTypeClassList(ContractTypeClassListEntity contractTypeClassListEntity) {

    	Example example;
		if (!StringUtils.isBlank(contractTypeClassListEntity.getId())) {
			Sqls sqls = Sqls.custom().andEqualTo("id", Integer.valueOf(contractTypeClassListEntity.getId()));
			example = Example.builder(ContractTypeClass.class).where(sqls).orderByAsc("orderby").build();
		}
		else {
			example = Example.builder(ContractTypeClass.class).orderByAsc("orderby").build();
		}

		List<ContractTypeClass> contractTypeList = iServicePlanContractTypeClassDao.selectByExample(example);

		return contractTypeList;
    }

    @Override
    public Map getServicePlanContractList(ServicePlanContractListEntity servicePlanContractListEntity) {
		List<Contract> contractList = servicePlanDao.getContractList(servicePlanContractListEntity);
		ContractType contractType = null;
		if (StringUtils.equals("Y",servicePlanContractListEntity.getIsPreview())){
			String contractTypeId = Optional.of(contractList)
				.filter(e-> !CollectionUtils.isEmpty(e))
				.map(list -> list.get(0))
				.map(Contract::getContractTypeId)
				.orElse(null);
			if (Objects.nonNull(contractTypeId)){
				contractType = servicePlanContractTypeDao.selectByPrimaryKey(contractTypeId);
			}
		} else {
			contractType = servicePlanContractTypeDao.selectByPrimaryKey(servicePlanContractListEntity.getContractTypeId());
			if (Objects.isNull(contractType)) {
				return ResultMap.creatResultMap(2).errorBuilder("the contractType is not exist").build();
			}
		}

		HashMap<Object, Object> getServicePlanContractList = Maps.newHashMapWithExpectedSize(3);
		//设置服务计划描述信息
		getServicePlanContractList.put("discountMsgTc", Objects.nonNull(contractType) ? contractType.getDiscountMsgTc() : StringUtils.EMPTY);
		getServicePlanContractList.put("discountMsgEn", Objects.nonNull(contractType) ? contractType.getDiscountMsgEn() : StringUtils.EMPTY);

        getServicePlanContractList.put("list", contractList);
        return getServicePlanContractList;
    }

}
