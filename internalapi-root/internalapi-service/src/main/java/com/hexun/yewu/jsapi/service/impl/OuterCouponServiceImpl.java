package com.hexun.yewu.jsapi.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hexun.framework.core.baseutils.MethodResult;
import com.hexun.framework.core.properties.PropertiesUtils;
import com.hexun.framework.core.redis.RedisUtils;
import com.hexun.framework.core.utils.BaseResponse;
import com.hexun.framework.core.utils.RespEnum;
import com.hexun.framework.core.utils.StringUtils;
import com.hexun.yewu.dto.coupon.BindActionInfoDto;
import com.hexun.yewu.dto.coupon.BindInfo;
import com.hexun.yewu.dto.coupon.GetCouponListDto;
import com.hexun.yewu.dto.pay.CheckWalletOpenDto;
import com.hexun.yewu.entity.coupon.CouponInfo;
import com.hexun.yewu.entity.coupon.IponeBuyCode;
import com.hexun.yewu.entity.coupon.PlanInfo;
import com.hexun.yewu.enums.common.ECouponType;
import com.hexun.yewu.enums.common.ESMSCannelType;
import com.hexun.yewu.enums.coupon.ECouDimension;
import com.hexun.yewu.enums.coupon.ECouInitiateType;
import com.hexun.yewu.enums.coupon.ECouPublishUserType;
import com.hexun.yewu.enums.coupon.ECouStatus;
import com.hexun.yewu.enums.coupon.EuserHoldCouStatus;
import com.hexun.yewu.enums.pay.EPlatform;
import com.hexun.yewu.jsapi.common.Constants;
import com.hexun.yewu.jsapi.entity.coupon.CouponPlanListReq;
import com.hexun.yewu.jsapi.entity.coupon.CouponPlanListRes;
import com.hexun.yewu.jsapi.entity.coupon.IponeBuyCodeReq;
import com.hexun.yewu.jsapi.entity.coupon.ReceiveCouponReq;
import com.hexun.yewu.jsapi.service.OuterCouponService;
import com.hexun.yewu.order.mysql.cache.mapper.coupon.IponeBuyCodeMapper;
import com.hexun.yewu.order.service.common.SMSService;
import com.hexun.yewu.order.service.pay.CouponService;
import com.hexun.yewu.order.service.pay.WalletService;

@Service
public class OuterCouponServiceImpl implements OuterCouponService {
	private Logger log = LoggerFactory.getLogger(getClass());

	@Resource
	private WalletService walletService;
	@Resource
	SMSService sMSService;
	@Resource
	IponeBuyCodeMapper IponeBuyCodeMapper;
	
	
	

	@Override
	@Transactional
	public BaseResponse getIphone8Coupon(String userid, String orderid,String productName) {
		BaseResponse response = new BaseResponse();
		//验证是否已存在用户领取信息
		if(this.hasReceiveCoupon(userid)){
			response.setRespCode(String.valueOf(RespEnum.RESP_FAIL.getCode()));
			response.setErrorMgs("用户已经领取！");
			log.info("用户{}已经领取!",userid);
			return response;
		}
		//验证领取数量已超过最大可领取数；
		if(!isHaveCanUsedCoupon()){
			response.setRespCode(String.valueOf(RespEnum.RESP_FAIL.getCode()));
			response.setErrorMgs("无可领取券！");
			log.info("用户{}无可领取券!",userid);
			return response;
		}
		//查询返回一个可用码并更新库
		long curCount=RedisUtils.incr(Constants.CUR_USED_IPONE8_CODE_COUNT);
		int issueCount=PropertiesUtils.getInt("ISSUE_IPONE_CODE_COUNT"); 
		if(curCount<=issueCount){
			log.info("当前已领数量，可领进入：{}",curCount);
			IponeBuyCode entity=this.IponeBuyCodeMapper.selectMinCodes();		
			String buy_code=entity.getBuy_code();
			String validate_start=entity.getValidate_start();
			String validate_end=entity.getValidate_end();
			log.info(System.currentTimeMillis()+" 用户{}的订单号{} 获得购买券{}",userid,orderid,buy_code); 
			entity.setOrderid(orderid);
			entity.setUserid(userid);
			this.IponeBuyCodeMapper.updateToCodes(entity);
			
			sendMsgForIphoneCode(userid, productName, buy_code);
			RedisUtils.set(Constants.USER_RECEIVE_IPONE8_CODE+"USER:"+userid, buy_code);
			RedisUtils.set(Constants.USER_RECEIVE_IPONE8_CODE+"ORDER:"+orderid, buy_code);
			IponeBuyCodeReq req=new IponeBuyCodeReq();
			
			req.setValidate_end(validate_end);
			req.setValidate_start(validate_start);
			req.setBuy_code(buy_code);
			req.setOrderid(orderid);
			response.setRespCode(String.valueOf(RespEnum.RESP_SUCCESS.getCode()));
			response.setResult(req);
		}else{
			response.setRespCode(String.valueOf(RespEnum.RESP_FAIL.getCode()));
			response.setErrorMgs("无可领取券！");
		}
		return response;
	}
	/**
	 * 验证是否已存在用户领取信息
	 * @param userid
	 * @return
	 */
	public boolean hasReceiveCoupon(String userid){
		String json = RedisUtils.get(Constants.USER_RECEIVE_IPONE8_CODE+"USER:"+userid);
		if(StringUtils.isNotBlank(json)){
			return true;
		}	
		return false;
	}
	/**
	 * 验证领取数量已超过最大可领取数；
	 * @return
	 */
	public boolean isHaveCanUsedCoupon(){
		int issueCount=PropertiesUtils.getInt("ISSUE_IPONE_CODE_COUNT"); 
		String countTep=RedisUtils.get(Constants.CUR_USED_IPONE8_CODE_COUNT);
		int curUsedCount=Integer.valueOf(countTep==null?"0":countTep);
		if(curUsedCount>=issueCount){
			return false;
		}
		log.info("当前已领数量：{}",curUsedCount);
		return true;
	}

	@Override
	public BaseResponse getLastAmout() {
		BaseResponse response = new BaseResponse();
		String countTep=RedisUtils.get(Constants.CUR_USED_IPONE8_CODE_COUNT);
		int curUsedCount=Integer.valueOf(countTep==null?"0":countTep);
		int issueCount=PropertiesUtils.getInt("ISSUE_IPONE_CODE_COUNT"); 
		int lastAmout=0;
		if(curUsedCount<issueCount){
			lastAmout=issueCount-curUsedCount;
		}
		response.setRespCode(String.valueOf(RespEnum.RESP_SUCCESS.getCode()));
		response.setResult(lastAmout);
		return response;
	}

	@Override
	public BaseResponse ishasOrderid(String orderid) {
		BaseResponse response = new BaseResponse();
		String json=RedisUtils.get(Constants.USER_RECEIVE_IPONE8_CODE+"ORDER:"+orderid);
		boolean isHas=false;
		if(StringUtils.isNotBlank(json)){
			isHas=true;
		}
		response.setRespCode(String.valueOf(RespEnum.RESP_SUCCESS.getCode()));
		response.setResult(isHas);
		return response;
	}
	/**
	 * iphone 抢购券码短信
	 * @param userid
	 */
	public void sendMsgForIphoneCode(String userid,String productName,String code){
		MethodResult mResult = walletService.GetWalletInfo(Long.valueOf(userid));
		if (mResult.Fail()){
			log.info("未获得用户 id {}的手机号",userid);
			return ;
		}
		CheckWalletOpenDto cwoDto = (CheckWalletOpenDto)mResult.getData();
		String phoneNum=cwoDto.getPhonenum();
		String content="尊敬的用户，您购买%s产品，已成功领取iphone8直购券，直购码：%s，请至一账通APP抢购！注意：请务必在有效期内进行使用（和讯网）";
		content=String.format(content, productName,code);
		sMSService.SendSMMessage(ESMSCannelType.EPAY, phoneNum, content, false, Executors.newFixedThreadPool(20));
		
		log.info("用户 id {}  发送短信内容:{}",userid,content);
	}

	
}
