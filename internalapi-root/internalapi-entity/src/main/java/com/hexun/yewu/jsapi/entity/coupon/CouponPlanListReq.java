package com.hexun.yewu.jsapi.entity.coupon;


public class CouponPlanListReq extends CouponBaseReq {
	private String plans;
	private String teacher;
	private String userid;

	public String getPlans() {
		return plans;
	}

	public void setPlans(String plans) {
		this.plans = plans;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

}
