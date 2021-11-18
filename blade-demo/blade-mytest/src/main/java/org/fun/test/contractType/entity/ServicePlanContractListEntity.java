package org.fun.test.contractType.entity;

import lombok.Data;

/**
 * @author ldk
 */
@Data
public class ServicePlanContractListEntity {
    /** 服务计划类型ID */
    private String contractTypeId;
    /** 服务计划子类ID */
    private String contractTypeChildId;
    /** 服务计划分类Id */
    private String contractTypeClassId;
    /** 是否预览,默认为N */
    private String isPreview = "N";
    /** 预览时间 */
    private String previewDateTime;
    /** 服务计划id */
    private String id;
}
