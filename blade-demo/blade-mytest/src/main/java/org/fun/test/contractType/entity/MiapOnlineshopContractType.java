package org.fun.test.contractType.entity;

import java.util.Date;

public class MiapOnlineshopContractType {
    private Integer typeId;

    private String contractTypeTc;

    private String contractTypeSc;

    private String contractTypeEn;

    private Integer orderby;

    private Integer display;

    private Date createDate;

    private Date modifiedDate;

    private String createdBy;

    private String modifiedBy;

    private String contractImage;

    private String labelTc;

    private String labelEn;

    private String contractDesc;

    private String contractFeeDesc;

    private String contractGb;

    private String contractFeeRules;

    private String discountMsgTc;

    private String discountMsgEn;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getContractTypeTc() {
        return contractTypeTc;
    }

    public void setContractTypeTc(String contractTypeTc) {
        this.contractTypeTc = contractTypeTc == null ? null : contractTypeTc.trim();
    }

    public String getContractTypeSc() {
        return contractTypeSc;
    }

    public void setContractTypeSc(String contractTypeSc) {
        this.contractTypeSc = contractTypeSc == null ? null : contractTypeSc.trim();
    }

    public String getContractTypeEn() {
        return contractTypeEn;
    }

    public void setContractTypeEn(String contractTypeEn) {
        this.contractTypeEn = contractTypeEn == null ? null : contractTypeEn.trim();
    }

    public Integer getOrderby() {
        return orderby;
    }

    public void setOrderby(Integer orderby) {
        this.orderby = orderby;
    }

    public Integer getDisplay() {
        return display;
    }

    public void setDisplay(Integer display) {
        this.display = display;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy == null ? null : modifiedBy.trim();
    }

    public String getContractImage() {
        return contractImage;
    }

    public void setContractImage(String contractImage) {
        this.contractImage = contractImage == null ? null : contractImage.trim();
    }

    public String getLabelTc() {
        return labelTc;
    }

    public void setLabelTc(String labelTc) {
        this.labelTc = labelTc == null ? null : labelTc.trim();
    }

    public String getLabelEn() {
        return labelEn;
    }

    public void setLabelEn(String labelEn) {
        this.labelEn = labelEn == null ? null : labelEn.trim();
    }

    public String getContractDesc() {
        return contractDesc;
    }

    public void setContractDesc(String contractDesc) {
        this.contractDesc = contractDesc == null ? null : contractDesc.trim();
    }

    public String getContractFeeDesc() {
        return contractFeeDesc;
    }

    public void setContractFeeDesc(String contractFeeDesc) {
        this.contractFeeDesc = contractFeeDesc == null ? null : contractFeeDesc.trim();
    }

    public String getContractGb() {
        return contractGb;
    }

    public void setContractGb(String contractGb) {
        this.contractGb = contractGb == null ? null : contractGb.trim();
    }

    public String getContractFeeRules() {
        return contractFeeRules;
    }

    public void setContractFeeRules(String contractFeeRules) {
        this.contractFeeRules = contractFeeRules == null ? null : contractFeeRules.trim();
    }

    public String getDiscountMsgTc() {
        return discountMsgTc;
    }

    public void setDiscountMsgTc(String discountMsgTc) {
        this.discountMsgTc = discountMsgTc == null ? null : discountMsgTc.trim();
    }

    public String getDiscountMsgEn() {
        return discountMsgEn;
    }

    public void setDiscountMsgEn(String discountMsgEn) {
        this.discountMsgEn = discountMsgEn == null ? null : discountMsgEn.trim();
    }
}
