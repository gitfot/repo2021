package org.fun.test.contractType.dao;

import org.fun.test.contractType.entity.ContractTypeClass;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;


/**
 * (miap_onlineshop_contract_type_class)Mapper接口
 *
 * @author zz
 * @since 2021-10-28 22:22:06
 */
@org.apache.ibatis.annotations.Mapper
@Repository
public interface IServicePlanContractTypeClassDao extends Mapper<ContractTypeClass> {

}
