package org.fun.test.order.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.util.List;

@Data
public class GetOutletAddressEntity {
	@NotBlank(message = "业务类型不能为空")
	@Pattern(regexp = "MAIN_DISTRICT|OUTLET|OUTLET_DISTRICT|OUTLET_DETAIL", message = "业务类型:MAIN_DISTRICT、OUTLET、OUTLET_DISTRICT、OUTLET_DETAIL,默认为MAIN_DISTRICT")
	private String businessType = "MAIN_DISTRICT";//业务类型,默认MAIN_DISTRICT
	@NotBlank(message = "语言不能为空")
	@Pattern(regexp = "E|T", message = "语言不能为空,E：英文 T：繁体中文")
	private String lang;//语言
	private String mainDistrict;//区域
	private String outletDistrict;//地区
	private String outletCode;//门市编码
	private List<ProductCodeCount> productCodeCount;
}
