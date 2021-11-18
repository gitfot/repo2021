package org.fun.test.contractType.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * (miap_onlineshop_contract_type_class)表实体类
 *
 * @author zz
 * @since 2021-10-28 22:21:49
 */
@Data
@Table(name = "miap_onlineshop_contract_type_class")
public class ContractTypeClass implements Serializable {

    @Id
    @GeneratedValue(generator="JDBC")
    private Integer id;

    private String classNameTc;

    private String classNameEn;

    private Integer orderby;

    private Date createDate;

    private Date modifiedDate;

    private String createdBy;

    private String modifiedBy;
}
