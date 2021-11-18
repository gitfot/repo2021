package org.fun.test.handset;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class GetHandsetDetailOfCustResponse {
    @JsonIgnore
    private String categoryId;
    private String productNameTc;
    private String productNameEn;
    private String paymentWayEn;
    private String paymentWayTc;
    private String shoppingNeedTc;
    private String shoppingNeedEn;
    private String maxUpfrontQuota;
    private String maxPrivilegeQuota;
    private String remainingPrivilegeQuota;
    private String remainingUpfrontQuota;
    private String referralType;
    private String productRemarksTc;
    private String productRemarksEn;
    private String tmcodeWhitelist;
    private String tmcodeBlacklist;
    private String sncodeWhitelist;
    private String sncodeBlacklist;
    private String hrId;
    private String marketingProductId;
}
