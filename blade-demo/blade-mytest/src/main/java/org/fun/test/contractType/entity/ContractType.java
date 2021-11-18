package org.fun.test.contractType.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Table(name = "miap_onlineshop_contract_type")
public class ContractType implements Serializable {
 /**
  *  大类ID
  */
 @Id
 @GeneratedValue(generator="JDBC")
 private Integer typeId;

 /**
  * 服务计划分类Id
  */
 private Integer classId;

 /**
  * 流量（数据字符串以‘,’分割，英文）
  */
 @Column(name = "contract_gb_en")
 private String contractGbEn;

 /**
  * 流量（数据字符串以‘,’分割，繁体）
  */
 @Column(name = "contract_gb_tc")
 private String contractGbTc;

 /**
  * 服务计划费用描述
  */
 //@Column(name = "contract_fee_desc_tc")
 @Transient
 private String contractFeeDesc;
 /**
  *  服务计划描述(英文)
  */
 @Column(name = "contract_desc_en")
 private String contractDescEn;

 /**
  *  服务计划描述(繁体)
  */
 @Column(name = "contract_desc_tc")
 private String contractDescTc;

 /**
  * 标签（如：新品，热门等,英文）
  */
 @Column(name = "label_id")
 @JsonIgnore
 private String labelId;

 @Transient
 private String labelNameEn;

 @Transient
 private String labelNameTc;

 @Transient
 private String labelType;


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
 @Column(name = "contract_type_en")
 private String contractTypeEn;
 /**
  * 标题（繁体）
  */
 @Column(name = "contract_type_tc")
 private String contractTypeTc;

 /**
  * 图片(繁体)地址
  */
 @Column(name = "contract_image_tc")
 private String contractImageTc;

 /**
  * 图片(英文)地址
  */
 @Column(name = "contract_image_en")
 private String contractImageEn;

 /**
  * 图片描述(繁体)地址
  */
 @Column(name = "image_alt_tc")
 private String imageAltTc;

 /**
  * 图片描述(英文)地址
  */
 @Column(name = "image_alt_en")
 private String imageAltEn;


 @Column(name = "discount_msg_tc")
 private String discountMsgTc;

 @Column(name = "discount_msg_en")
 private String discountMsgEn;

 @Column(name = "is_show_in_tab")
 private String isShowInTab;

 /**
  * 链接
  */
 @Column(name ="link")
 private String linkTc;


 @Column(name="link_en")
 private String linkEn;
 /**
  * 类型价格，当typePrice不为空时返回
  */
 @JsonIgnore
 @Column(name = "type_price")
 private String typePrice;

 /**
  * 规则（英文）
  */
 private List contractFeeRulesListEn;

 /**
  * 规则（繁体）
  */
 private List contractFeeRulesListTc;

 @Column(name = "is_show_discount_msg")
 private String isShowDiscountMsg;

 private String seoKeyword;

 @Column(name = "entry_type")
 private Integer entryType;

 /**
  * 子类型信息
  */
 private List<ContractChildType> contractChildTypeList;


}
