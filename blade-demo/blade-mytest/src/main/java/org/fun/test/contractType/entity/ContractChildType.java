package org.fun.test.contractType.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "miap_onlineshop_contract_type_child")
public class ContractChildType {


    @Id
    @GeneratedValue(generator="JDBC")
    private Integer id;
    /**
     * 是否显示：Y显示，N不显示；（类似上下架）
     */
    @Column(name = "display")
    private Integer display;
    /**
     * 排序
     */
    @Column(name = "orderby")
    private Integer orderBy;
    /**
     * 标题（英文）
     */
    @Column(name = "contract_type_child_en")
    private String contractTypeChildEn;
    /**
     * 标题（繁体）
     */
    @Column(name = "contract_type_child_tc")
    private String contractTypeChildTc;

    /**
     *  大类ID
     */
    @Column(name = "contract_type_id")
    private String contractTypeId;

    private String seoKeyword;


}
