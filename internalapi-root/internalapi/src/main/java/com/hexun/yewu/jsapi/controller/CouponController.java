package com.hexun.yewu.jsapi.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.resource.spi.RetryableUnavailableException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hexun.framework.core.baseutils.ConvertUtil;
import com.hexun.framework.core.controller.DefaultBaseController;
import com.hexun.framework.core.utils.BaseResponse;
import com.hexun.framework.core.utils.IPUtils;
import com.hexun.framework.core.utils.ParameterUtils;
import com.hexun.framework.core.utils.RespEnum;
import com.hexun.framework.core.utils.StringUtils;
import com.hexun.hwcommon.model.CommonLoginInfo;
import com.hexun.hwcommon.service.UserAuth;
import com.hexun.yewu.enums.pay.EPlatform;
import com.hexun.yewu.jsapi.entity.coupon.CouponPlanListReq;
import com.hexun.yewu.jsapi.entity.coupon.ReceiveCouponReq;
import com.hexun.yewu.jsapi.service.OuterCouponService;
import com.hexun.yewu.order.consts.CCfg;

/**
 * 优惠券相关接口
 *
 */
@Scope("prototype")
@Controller
@RequestMapping(value = "coupon", method = { RequestMethod.GET, RequestMethod.POST })
public class CouponController extends DefaultBaseController {
	private Logger log = LoggerFactory.getLogger(getClass());
	@Resource
	private OuterCouponService outerCouponService;
	/** 正式可访问IP列表 **/
	private static final String LEGAL_IP[] = new String[] { "10.4.12.182","10.4.12.183","10.4.12.184" };
	
	/**
	 * iphone 抢券 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/iphone8Coupon")
	public @ResponseBody BaseResponse iphone8Coupon(HttpServletRequest request) {
		BaseResponse resp = new BaseResponse();
		try {
			long vaStartTime = System.currentTimeMillis();
			Map<String, String> dataMap = ParameterUtils.convertMap(request.getParameterMap(), "UTF-8");
			log.info("** 接收到的" + request.getMethod() + "请求信息:{}", JSON.toJSON(dataMap));
			String userid=dataMap.get("userid");
			String orderid=dataMap.get("orderid");
			String productName=dataMap.get("productName");
			
			if (StringUtils.isBlank(orderid)||StringUtils.isBlank(productName)) {
				return errorMgs(resp, "参数错误！");
			}
			if (StringUtils.isBlank(userid)) {
				return errorMgs(resp, "用户未登录");
			}
			resp = outerCouponService.getIphone8Coupon(userid,orderid,productName);
			log.info(" **iphone 抢券接口返回数据：{}", JSON.toJSON(resp));
			log.info("调用结束，执行时间：{}", (System.currentTimeMillis() - vaStartTime));
		}catch(Exception e){
			log.error(e.getMessage());
			return errorMgs(resp, "接口异常！");
		}
		return resp;
		
	}
   /**
    * 剩余数量
    * @param request
    * @return
    */
	@RequestMapping(value = "/unusedAmount")
	public @ResponseBody BaseResponse unusedAmount(HttpServletRequest request) {
		BaseResponse resp = new BaseResponse();
		try {
			long vaStartTime = System.currentTimeMillis();
			//Map<String, String> dataMap = setRequestMap(request);
			//String userid=dataMap.get("userid");
			resp =outerCouponService.getLastAmout();
			log.info(" **查询iphone券剩余数量接口返回数据：{}", JSON.toJSON(resp));
			log.info("调用结束，执行时间：{}", (System.currentTimeMillis() - vaStartTime));
		}catch(Exception e){
			log.error(e.getMessage());
			return errorMgs(resp, "接口异常！");
		}
		return resp;
	}
	/**
	 * 是否包括订单信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/hasOrder")
	public @ResponseBody BaseResponse hasOrder(HttpServletRequest request) {
		BaseResponse resp = new BaseResponse();
		try {
			long vaStartTime = System.currentTimeMillis();
			Map<String, String> dataMap = setRequestMap(request);
			String orderid=dataMap.get("orderid");
			resp =outerCouponService.ishasOrderid(orderid);
			log.info(" **查询是否包括订单信息接口返回数据：{}", JSON.toJSON(resp));
			log.info("调用结束，执行时间：{}", (System.currentTimeMillis() - vaStartTime));
		}catch(Exception e){
			log.error(e.getMessage());
			return errorMgs(resp, "接口异常！");
		}
		return resp;
	}
	
		
	/**
	 * 获取所有请求参数；所有userid取自cookie ，上线时去掉原来请求参数中 userid 参数
	 * @param request
	 * @return
	 */
	private Map<String, String> setRequestMap(HttpServletRequest request) {

		Map<String, String> dataMap = ParameterUtils.convertMap(request.getParameterMap(), "UTF-8");
		log.info("** 接收到的" + request.getMethod() + "请求信息:{}", JSON.toJSON(dataMap));

		CommonLoginInfo userLoginInfo = UserAuth.GetUserInfoByRequest(request);

		if (StringUtils.isNotBlank(userLoginInfo.getUserid())) {
			dataMap.put("userid", String.valueOf(userLoginInfo.getUserid()));
		} else {
			if(!ConvertUtil.Str2B(CCfg.IS_TEST_ENV))//上线时去掉原来 userid 参数 
				dataMap.remove("userid"); 
		}
		return dataMap;
	}

	public boolean isLegalPlantform(String platform) {
		if (StringUtils.isBlank(platform))
			return false;
		if (!platform.equalsIgnoreCase(EPlatform.WEB.toString()) && !platform.equalsIgnoreCase(EPlatform.H5.toString()))
			return false;
		return true;
	}

	private BaseResponse errorMgs(BaseResponse resp, String mgs) {
		resp.setCharset("UTF-8");
		resp.setRespCode(String.valueOf(RespEnum.RESP_FAIL.getCode()));
		resp.setErrorData(mgs);
		return resp;
	}
	
	public static void main(String []args){
		
	System.out.println(ArrayUtils.contains(LEGAL_IP, "10.4.12.182"));
	}
}
