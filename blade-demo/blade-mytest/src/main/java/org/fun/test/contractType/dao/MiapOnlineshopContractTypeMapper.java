package org.fun.test.contractType.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface MiapOnlineshopContractTypeMapper {

    Map<String, Object> getContractTypeAndClassById(Integer contractTypeId, Integer contractTypeClassId);

}
