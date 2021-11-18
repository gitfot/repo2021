package org.fun.test.contractType.service;


import org.fun.test.contractType.entity.*;

import java.util.List;
import java.util.Map;

/**
 * @author ldk
 * @version 2019-11-26
 * @desc 带机上台服务
 */

public interface IServicePlanService {

    /**
     * 带机上台列表接口
     * @return ServicePlanContract
     */
    List<ContractType> getServicePlanContractTypeList(ContractTypeEntity contractTypeEntity);

    /**
     * 获取服务计划分类列表
     * @return ServicePlanContract
     */
    List<ContractTypeClass> getServicePlanContractTypeClassList(ContractTypeClassListEntity contractTypeClassListEntity);

    /**
     *
     * @param servicePlanContractListEntity
     * @return
     */
    Map getServicePlanContractList(ServicePlanContractListEntity servicePlanContractListEntity);

}
