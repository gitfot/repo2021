package org.fun.test.contractType.base;

/**
 * 返回枚举
 *
 * 构造返回的ResultMap,指定通用的返回码及默认描述
 *
 * 返回描述可以根据具体原因修改,如：
 * ResultEnum.ERROR_INPUT.getResultMap().addDescDetail(errorMsg)
 */
public enum ResultEnum {
	SUCCESS("0","success"),
	ERROR_SERVICE("-500","service层异常"),
	//  通用 -50开头 5位数返回码
	ERROR_INVOKE_BOSS("-50001","调用boss3接口异常"),
	ERROR_INPUT("-50002","入参不合法"),
	ERROR_INSERT("-50003","插入数据库失败"),
	ERROR_UPDATE("-50004","更新数据库失败"),
	ERROR_INVOKE_HKBN("-50005","调用HKBN接口异常"),
	//  家居宽频 -10开头 5位数返回码
	ERROR_XML_FORMAT("-10001","xml格式错误"),
	INVALID_CREDIT("-10002","无效信用卡号"),
	ERROR_APPLY("-10003","您的账号未能申请家居宽屏服务"),
//  在线开台 -11开头 5位数返回码

	//  登录模块 -12开头 5位数返回码
	INVALID_MOBILE_NUMBER_OTP("-12001","INVALID_MOBILE_NUMBER_OTP"),
	BLOCK_SERVICE_IN_PERSON_OTPWD_LOGIN("-12002","BLOCK_SERVICE_IN_PERSON_OTPWD_LOGIN"),
	MESSAGE_SEND_FAILED("-12003","短信发送失败"),
	MESSAGE_NOT_EXPIRESS("-12004","OTPWD_LESS_TIME"),
	INVALID_ACCESS_TOKEN("-12005","无效accessToken"),

	//  下单模块 -13开头 5位数返回码
	INVALID_REFERRAL("-13001","无效推荐编码"),
	EXCEED_REFERRAL_QUOTA("-13002","已超出推荐配额"),
	INVALID_CODE("-13003","地区编码、街道编码、屋苑编码不能同时为空"),
	INVALID_AREA_CODE("-13004","区域编码不能为空"),
	INVALID_DISTRICT_CODE("-13005","地区编码不能为空"),
	INVAILD_BUSSINESSTYPE("-13006", "業務類型businessType不能为空。"),
	INVALID_CUSTOMER("-13009","用户实例标识不存在！"),
	INVALID_INPUT("-13010","訊息的頁面編碼pageCode,訊息的詳情編碼msgCode不能为空！"),
	INVALID_INPUT2("-13011","订单查询:请至少输入一个查询条件"),
	INVALID_EMPTY("-13012","订单要素空缺!请检视入参字段名，格式等是否有误。"),
	INVALID_PaymentMehodType("-13013","查询不到该业务类型businessType对应的付款方式！"),
	DutilKey_ERROR("-13014","数据库表中已存在重复的数据！"),
	PREOCCUPY_QUOTA_ERROR("-13015","预占配额失败"),
	RELEASE_QUOTA_ERROR("-13016","释放配额失败"),
	EXCEED_USE_TIMES("-13017","优惠编码使用次数不足"),
	EXCEED_LIMIT_TIMES("-13018","超出限购配额"),
//  智能家居 -14开头 5位数返回码

	//  装置配件 -15开头 5位数返回码
	NOT_ENOUGH_QUOTA("-15007","产品固件配额不足"),

//  买机及上台 -16开头 5位数返回码

//  现客买机 -17开头 5位数返回码

	//  带机上台 -18开头 5位数返回码
	CONTRACT_NOT_EXIST("-18001", "the contract is not exist"),



//  净手机 -19开头 5位数返回码

	//短信网关
	SENDMESSAGE_FAILED("-20001", "send message failed"),
	MESSAGE_TEMPLATE_ERROR("-20002", "message template error"),
	//邮件网关
	SEND_EMAIL_FAILED("-21000", "send email failed");



	private String code;

	private String desc;

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	ResultEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public boolean isSuccess(){
		return  this.equals(ResultEnum.SUCCESS);
	}
}
