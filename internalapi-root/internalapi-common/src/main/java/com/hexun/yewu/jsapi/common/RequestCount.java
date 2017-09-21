package com.hexun.yewu.jsapi.common;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hexun.framework.core.redis.RedisUtils;
import com.hexun.framework.core.utils.DateUtils;
import com.hexun.framework.core.utils.StringUtils;
/**
 * 统计请求
 * @author zhoudong
 *
 */
public class RequestCount {
	private static Logger log = LoggerFactory.getLogger(RequestCount.class);
	private static int requestCount = 800;
	/**
	 * 请求计数
	 */
	public static void requestCount(){
		++requestCount;
		
		if(requestCount == 1000){
			Integer count = Integer.parseInt(
					StringUtils.isBlank(
							RedisUtils.get(Constants.DPLUSAPI_REQ_COUNT_KEY + DateUtils.DateToString(new Date(), "yyyyMMdd"))) 
							? "0" 
							: RedisUtils.get(Constants.DPLUSAPI_REQ_COUNT_KEY + DateUtils.DateToString(new Date(), "yyyyMMdd"))
					);
			RedisUtils.set(Constants.DPLUSAPI_REQ_COUNT_KEY + DateUtils.DateToString(new Date(), "yyyyMMdd"), String.valueOf(count + requestCount));
			log.info("请求量统计，请求量写入到redis，时间：{}",DateUtils.getDefaultDate());
			requestCount = 0;
		}
	}
}
