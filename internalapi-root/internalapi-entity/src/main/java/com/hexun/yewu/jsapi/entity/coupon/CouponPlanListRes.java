package com.hexun.yewu.jsapi.entity.coupon;

import java.io.Serializable;

import org.bson.util.StringRangeSet;

public class CouponPlanListRes implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 513072004157894984L;
	private String plan_id;
	private String plan_name;
	private String publish_user_type;
	private String publish_user;
	private String coupon_type;
	private String coupon_start_date;
	private String coupon_end_date;
	private String plan_summary;  //摘要描述
	private String useScope; // 优惠券适用平台                 ALL_PLATFORM：全平台  COMMUNITY_PLATFORM： 社区平台
	private Integer experienceDays;// 体验天数
	private Integer everyFullLimitAmount;// 每满金额
	private Integer everyFullReductionAmount;// 每满优惠金额
	private String receive_status;// 领券状态，未领取：nohold 可用：have  过期：outofdate 已用：used
	private String forward_url; // 跳转URL
	private String productids;    //纬度产品 ids
	private String groupids;       //套餐 ids
	private String couponid;

	public String getPlan_id() {
		return plan_id;
	}

	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}

	public String getPlan_name() {
		return plan_name;
	}

	public void setPlan_name(String plan_name) {
		this.plan_name = plan_name;
	}

	public String getPublish_user_type() {
		return publish_user_type;
	}

	public void setPublish_user_type(String publish_user_type) {
		this.publish_user_type = publish_user_type;
	}

	public String getPublish_user() {
		return publish_user;
	}

	public void setPublish_user(String publish_user) {
		this.publish_user = publish_user;
	}

	public String getPlan_summary() {
		return plan_summary;
	}

	public void setPlan_summary(String plan_summary) {
		this.plan_summary = plan_summary;
	}

	public String getCoupon_start_date() {
		return coupon_start_date;
	}

	public void setCoupon_start_date(String coupon_start_date) {
		this.coupon_start_date = coupon_start_date;
	}

	public String getCoupon_end_date() {
		return coupon_end_date;
	}

	public void setCoupon_end_date(String coupon_end_date) {
		this.coupon_end_date = coupon_end_date;
	}

	public String getUseScope() {
		return useScope;
	}

	public void setUseScope(String useScope) {
		this.useScope = useScope;
	}

	public Integer getExperienceDays() {
		return experienceDays;
	}

	public void setExperienceDays(Integer experienceDays) {
		this.experienceDays = experienceDays;
	}

	public Integer getEveryFullLimitAmount() {
		return everyFullLimitAmount;
	}

	public void setEveryFullLimitAmount(Integer everyFullLimitAmount) {
		this.everyFullLimitAmount = everyFullLimitAmount;
	}

	public Integer getEveryFullReductionAmount() {
		return everyFullReductionAmount;
	}

	public void setEveryFullReductionAmount(Integer everyFullReductionAmount) {
		this.everyFullReductionAmount = everyFullReductionAmount;
	}

	public String getReceive_status() {
		return receive_status;
	}

	public void setReceive_status(String receive_status) {
		this.receive_status = receive_status;
	}

	public String getForward_url() {
		return forward_url;
	}

	public void setForward_url(String forward_url) {
		this.forward_url = forward_url;
	}

	public String getCoupon_type() {
		return coupon_type;
	}

	public void setCoupon_type(String coupon_type) {
		this.coupon_type = coupon_type;
	}

	public String getProductids() {
		return productids;
	}

	public void setProductids(String productids) {
		this.productids = productids;
	}

	public String getGroupids() {
		return groupids;
	}

	public void setGroupids(String groupids) {
		this.groupids = groupids;
	}

	public String getCouponid() {
		return couponid;
	}

	public void setCouponid(String couponid) {
		this.couponid = couponid;
	}

}
