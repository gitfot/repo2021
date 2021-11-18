package org.fun.test.contractType.dao;

import org.apache.ibatis.annotations.Mapper;
import org.fun.test.contractType.entity.Contract;
import org.fun.test.contractType.entity.ServicePlanContractListEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ldk
 * @desc 服务计划
 */
@Mapper
@Repository
public interface IServicePlanDao {

    /**
     * 获取对应类别下的服务计划
     */
    List<Contract> getContractList(ServicePlanContractListEntity entity);

	/**
	 * 根据服务计划分类id获取列表
	 */
	List<Contract> getContractByClassId(Integer classId);

}
