package org.fun.test.handset;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class GetHandsetRateplanDetailResponse {
    @JsonIgnore
    private String mcpId;
    private String shoppingNeedEn;
    private String shoppingNeedTc;
    private String paymentWayTc;
    private String paymentWayEn;
    private String productNameEn;
    private String productNameTc;
    private String referralType;
    private String productRemarksTc;
    private String productRemarksEn;
    private String tmcodeWhitelist;
    private String tmcodeBlacklist;
    private String sncodeWhitelist;
    private String sncodeBlacklist;
    private String isLoginBuy;
}
