package org.fun.test.contractType.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * @author ldk
 */
@Data
@Table(name =  "miap_onlineshop_contract")
public class Contract implements Serializable {

    private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(generator="JDBC")
    private Integer contractId;

    private String planId;

    private String tmcode;

    private String planNameTc;

    private String adminFee;

    private String mnpPrice;

    private String newPrice;

    private String planNameEn;

    private String contractPeriod;

    private String planDesTc;

    private String planDesEn;

    private Integer accessFee;

    private String freeMinutesDesTc;

    private String freeMinutesDesEn;

    private String dataTc;

    private String dataEn;

    private String extraDataTc;


    private String extraDataEn;

    private String termsAndConditionsTc;


    private String termsAndConditionsEn;

    @JsonIgnore
    private String isShowInListing;

    @JsonIgnore
    private String isStudentPlan;

    private String purchaseGuideTc;

    private String purchaseGuideEn;

    private String paymentMethod;

    private String morePlanDesTc;

    private String morePlanDesEn;

    private String contractRemarkTc;

    private String contractRemarkEn;

    private String otherDataTc;

    private String otherDataEn;

    private String adminFeePrice;

    private String adminFeeOfferId;

    private String payInfoEn;

    private String payInfoTc;

    private String contractDataTc;

    private String contractDataEn;

    @JsonIgnore
    private String contractTypeId;

    @JsonIgnore
    private String contractChildTypeId;

    //@Column(name = "is_show_buy_now")
    private String isShowBuyNow;

    //@Column(name = "is_show_gouw")
    private String isShowGouw;

    private List offerList;

    private String displayGroup;


    //tmcode 黑、白名单
    private String tmcodeWhitelist;
    private String tmcodeBlacklist;
    //sncode 黑、白名单
    private String sncodeWhitelist;
    private String sncodeBlacklist;

    //是否登录购买
    private String isLoginBuy;

    private String numberChoose;
    private String appliedScenario;




}
