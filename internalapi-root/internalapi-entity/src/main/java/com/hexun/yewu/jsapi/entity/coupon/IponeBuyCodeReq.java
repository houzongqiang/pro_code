package com.hexun.yewu.jsapi.entity.coupon;

public class IponeBuyCodeReq {
	private String validate_start;
	private String validate_end;
	private String buy_code;
	private String orderid;

	public String getValidate_start() {
		return validate_start;
	}

	public void setValidate_start(String validate_start) {
		this.validate_start = validate_start;
	}

	public String getValidate_end() {
		return validate_end;
	}

	public void setValidate_end(String validate_end) {
		this.validate_end = validate_end;
	}

	public String getBuy_code() {
		return buy_code;
	}

	public void setBuy_code(String buy_code) {
		this.buy_code = buy_code;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

}
