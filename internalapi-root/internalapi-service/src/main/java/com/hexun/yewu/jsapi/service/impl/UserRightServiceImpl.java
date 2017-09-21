package com.hexun.yewu.jsapi.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hexun.framework.core.baseutils.MethodResult;
import com.hexun.framework.core.mogodb.MongoInit;
import com.hexun.framework.core.utils.BaseResponse;
import com.hexun.framework.core.utils.RespEnum;
import com.hexun.framework.core.utils.StringUtils;
import com.hexun.hwcommon.model.CommonLoginInfo;
import com.hexun.hwcommon.service.UserAuth;
import com.hexun.yewu.jsapi.entity.UserRightAllResponse;
import com.hexun.yewu.consts.MongoDBConst;
import com.hexun.yewu.entity.feedback.FeedBackJoinData;
import com.hexun.yewu.entity.price.PriceModel;
import com.hexun.yewu.entity.product.Product;
import com.hexun.yewu.jsapi.common.Constants;
import com.hexun.yewu.jsapi.service.UserRightService;
import com.hexun.yewu.vo.account.HexunAccountNewRequestVo;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

/**
 * 权限接口 实现类
 * @author gpy
 *
 */
@Service
public class UserRightServiceImpl implements UserRightService{
	private Logger log = LoggerFactory.getLogger(getClass());
	
	
	//获取用户所有权限列表
	@Override
	public BaseResponse getUserRightList(HttpServletRequest req) throws Exception {
		
		BaseResponse resp = new BaseResponse();
		resp.setCharset(Constants.charset_utf_8);
//		if(StringUtils.isBlank(userId)){
//			resp.setRespCode(String.valueOf(RespEnum.RESP_FAIL.getCode()));
//			resp.setErrorData("user_id不能为空");
//			return resp;
//		} //取消传值,改为cookie取值		
		
		CommonLoginInfo userLoginInfo = UserAuth.GetUserInfoByRequest(req);
		String userId = userLoginInfo.getUserid();
		if (userLoginInfo.getIslogin().equals("False")){
			resp.setRespCode(String.valueOf(RespEnum.RESP_FAIL.getCode()));
			resp.setErrorData("用户未登录");
			return resp;
		}			
		if(StringUtils.isBlank(userId)){
			resp.setRespCode(String.valueOf(RespEnum.RESP_FAIL.getCode()));
			resp.setErrorData("用户id为空");
			return resp;
		}
		
		//获取用户权限列表
		long time1 = System.currentTimeMillis();
		List<UserRightAllResponse> userRightList = getUserRightListFromMongo(Long.parseLong(userId),-1,1);
		log.info("right all 1-所有权限取mongo：{} 毫秒", System.currentTimeMillis() - time1);
		if (userRightList == null) {
			resp.setRespCode(String.valueOf(RespEnum.RESP_FAIL.getCode()));
			resp.setErrorData("用户没有任何权限");
			return resp;
		}			
		
		//产品表 in (权限列表中的productid),取产品名称, 可以缩小范围 加快提取速度.
		long time2 = System.currentTimeMillis();		
		List<Product> productList = getProductListByRightList(userRightList, -1);
		log.info("right all 2-用户权限对应的产品名称list mongo：{} 毫秒", System.currentTimeMillis() - time2);
		if (productList == null){
			resp.setRespCode(String.valueOf(RespEnum.RESP_FAIL.getCode()));
			resp.setErrorData("取产品名称异常");
			return resp;
		}
		
		//价格表 in (权限列表中的priceid),取价格名称, 可以缩小范围 加快提取速度.
		long time3 = System.currentTimeMillis();		
		List<PriceModel> priceList = getPriceListByRightList(userRightList);
		log.info("right all 3-用户权限对应的价格名称list mongo：{} 毫秒", System.currentTimeMillis() - time3);
		if (priceList == null){
			resp.setRespCode(String.valueOf(RespEnum.RESP_FAIL.getCode()));
			resp.setErrorData("取价格策略名称异常");
			return resp;
		}
		
		//List<UserRightPageResponse> 转化 List<UserRightALLResponse>
//		List<UserRightAllResponse> userRightListAll = new ArrayList<UserRightAllResponse>(); 
//		for (UserRightPageResponse urpage : userRightList) {
//			UserRightAllResponse urAll = new UserRightAllResponse();
//			urAll.setUserID(urpage.getUserID());
//			urAll.setUserName(urpage.getUserName());
//			urAll.setProductID(urpage.getProductID());
//			urAll.setPriceID(urpage.getPriceID());
//			urAll.setBeginTime(urpage.getBeginTime());
//			urAll.setEndTime(urpage.getEndTime());
//			urAll.setToUserID(urpage.getToUserID());
//			urAll.setIsSus(urpage.getIsSus());
//			userRightListAll.add(urAll);
//		}
		
		//循环计算剩余天数
		Timestamp nowTime= new Timestamp((new Date()).getTime());
		for (UserRightAllResponse ur : userRightList) {
			try{
				if (ur.getEndTime().getTime() > nowTime.getTime()){
					long lastday = (ur.getEndTime().getTime() - nowTime.getTime())/(24*60*60*1000);
					ur.setServerDay(String.valueOf(lastday + 1));
				}
				else {
					ur.setServerDay(String.valueOf("0"));
				}
			}
			catch (Exception e){
				log.info("right all 计算剩余天数异常:priceid={},userid={}",ur.getPriceID(), ur.getUserID());
			}	
		}
		
		//产品名称循环插入userRightListAll
		for (UserRightAllResponse ur : userRightList) {
			for (Product product : productList) {
				if (product.getProductID() == ur.getProductID()){
					ur.setProductName(product.getProductName());
					ur.setProductType(product.getProductType());
					continue;
				}
			}
		}
		
		//价格策略名称循环插入userRightListAll
		for (UserRightAllResponse ur : userRightList) {
			for (PriceModel price : priceList) {
				if (price.getPriceID() == ur.getPriceID()){
					ur.setPriceName(price.getPriceName());
					continue;
				}
			}
		}
		
		resp.setRespCode(String.valueOf(RespEnum.RESP_SUCCESS.getCode()));
		resp.setResult(userRightList);		
		return resp;
	}

	
	// 从mongodb中获取用户所有权限
	public List<UserRightAllResponse> getUserRightListFromMongo(long userid, long sid, int rtype) throws Exception {
		BasicDBObject query = new BasicDBObject();
		query.put("UserID", userid);
		if (sid > 0){
			query.put("ToUserID", sid);
		}		
		if (rtype == 1)			
			query.put("EndTime", new BasicDBObject("$gte", new Date())); //>=  EndTime大于当前时间 在期
		else if (rtype == 2)
			query.put("EndTime", new BasicDBObject("$lt", new Date()));  //<   EndTime大于当前时间 过期		
		try {
			// 获取权限
			List<UserRightAllResponse> userRight = MongoInit.mongoDBUtils.find("ProductRight", query,new BasicDBObject("EndTime", 1), 2000, UserRightAllResponse.class);
			return userRight;
		} catch (Exception e) {
			throw new Exception(String.format("从Mongo读取换权限并转换实体发生异常(userid=%d)", userid));
		}
	}
	
	//通过用户权限取产品列表
	public List<Product> getProductListByRightList(List<UserRightAllResponse> userRightList, int ptype) throws Exception {
		BasicDBObject query = new BasicDBObject();
		
		//ptype>0 是王华接口,需要区分产品类型, ptype=-1 是微信项目,用户所有权限接口,不区分产品类型
		if (ptype > 0){
			//20170407 ptype=2 需要in(2,28)
			if (ptype == 2){
				BasicDBList pvalues = new BasicDBList();
				pvalues.add(2);
				pvalues.add(28);
				query.put("MO_ProductType", new BasicDBObject("$in", pvalues));
			}
			else {
				query.put("MO_ProductType", ptype);			
			}		
			query.put("MO_Status", "0");
		}
		
		BasicDBList values = new BasicDBList();
		for (UserRightAllResponse userRight:userRightList){
			values.add(userRight.getProductID());
		}
        query.put("MO_ProductID", new BasicDBObject("$in", values));
		
		BasicDBObject keys = new BasicDBObject();
		keys.put("MO_ProductID", 1);
		keys.put("MO_ProductName", 1);
		keys.put("MO_ProductType", 1);
		return MongoInit.mongoDBUtils.find("TieBa_ProductList", query, keys, Product.class);
	}
	
	//通过用户权限取价格策略列表
	public List<PriceModel> getPriceListByRightList(List<UserRightAllResponse> userRightList) throws Exception {
		BasicDBObject query = new BasicDBObject();		
		
		BasicDBList values = new BasicDBList();
		for (UserRightAllResponse userRight:userRightList){
			values.add(userRight.getPriceID());
		}
        query.put("MO_PriceID", new BasicDBObject("$in", values));
		
		BasicDBObject keys = new BasicDBObject();
		keys.put("MO_PriceID", 1);
		keys.put("MO_PriceName", 1);		
		return MongoInit.mongoDBUtils.find("TieBa_ProductPrice", query, keys, PriceModel.class);
	}
}
