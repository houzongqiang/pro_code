package com.hexun.yewu.jsapi.entity.coupon;


public class ReceiveCouponReq extends CouponBaseReq {
	private String userid;
	private String planid;
	private String publishUser;
	private String publishUserType;
	private String couponType;
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPlanid() {
		return planid;
	}

	public void setPlanid(String planid) {
		this.planid = planid;
	}

	public String getPublishUser() {
		return publishUser;
	}

	public void setPublishUser(String publishUser) {
		this.publishUser = publishUser;
	}

	public String getPublishUserType() {
		return publishUserType;
	}

	public void setPublishUserType(String publishUserType) {
		this.publishUserType = publishUserType;
	}

	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

}
