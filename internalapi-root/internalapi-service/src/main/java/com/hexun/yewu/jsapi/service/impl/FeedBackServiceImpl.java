package com.hexun.yewu.jsapi.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hexun.framework.core.mogodb.MongoInit;
import com.hexun.framework.core.utils.BaseResponse;
import com.hexun.framework.core.utils.RespEnum;
import com.hexun.framework.core.utils.StringUtils;
import com.hexun.yewu.consts.MongoDBConst;
import com.hexun.yewu.entity.feedback.FeedBackJoinData;
import com.hexun.yewu.jsapi.common.Constants;
import com.hexun.yewu.jsapi.service.FeedBackService;
import com.mongodb.BasicDBObject;

/**
 * 反馈接口 实现类
 * @author zhoudong
 *
 */
@Service
public class FeedBackServiceImpl implements FeedBackService{

	@Override
	public BaseResponse getFeedBackList(String userId) {
		
		BaseResponse resp = new BaseResponse();
		resp.setCharset(Constants.charset_utf_8);
		if(StringUtils.isBlank(userId)){
			resp.setRespCode(String.valueOf(RespEnum.RESP_FAIL.getCode()));
			resp.setErrorData("user_id不能为空");
			return resp;
		}
		
		
		BasicDBObject query = new BasicDBObject();
		query.put("MO_fld_userID", Long.parseLong(userId));
					
		List<FeedBackJoinData> listResult = null;
		try {
			listResult = MongoInit.getMongoDBUtils("DB_VP", "", "").find(MongoDBConst.COLNAME_FEEDBACKJOINDATA, query, FeedBackJoinData.class);
		} catch (Exception e) {
			resp.setRespCode(String.valueOf(RespEnum.RESP_FAIL.getCode()));
			resp.setErrorData("查询失败，请稍后再试");
			e.printStackTrace();
			return resp;
		} 		
		resp.setRespCode(String.valueOf(RespEnum.RESP_SUCCESS.getCode()));
		resp.setResult(listResult);
		
		return resp;
	}

}
