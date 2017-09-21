package com.hexun.yewu.jsapi.service;

import com.hexun.framework.core.utils.BaseResponse;
import com.hexun.yewu.jsapi.entity.coupon.CouponPlanListReq;
import com.hexun.yewu.jsapi.entity.coupon.ReceiveCouponReq;

public interface OuterCouponService {

	
	
	/**
	 * iphon8 优惠券
	 * 
	 * @param userid
	 * @param couponStatus
	 * @return
	 */
	public BaseResponse getIphone8Coupon(String userid, String orderid,String productName);
	
	/**
	 * 取得剩余数量
	 * @return
	 */
	public BaseResponse getLastAmout();
	/**
	 * iphone抢券是否存在此订单id
	 * @return
	 */
	public BaseResponse ishasOrderid(String orderid);
}
