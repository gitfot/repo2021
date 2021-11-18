package org.fun.test.contractType.dao;

import org.fun.test.contractType.entity.ContractType;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author ldk
 */
@org.apache.ibatis.annotations.Mapper
@Repository
public interface IServicePlanContractTypeDao extends Mapper<ContractType> {

}
