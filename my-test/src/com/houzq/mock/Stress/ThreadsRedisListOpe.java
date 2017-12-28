package com.houzq.mock.Stress;

import java.util.HashMap;
import java.util.Map;

import com.hexun.framework.core.utils.HTTPUtils;

public class ThreadsRedisListOpe {
	public static int [] planids={36,37,38,39,40};
	
	public static void main(String[] args) {
		
		for(int i=0;i<planids.length ;i++){
			int planid=planids[i];
			SendReq send=new ThreadsRedisListOpe().new SendReq(planid,"8888882","0","web","EXPERIENCE_COUPON"); 
			send.start();
		}
		
	}
	
	class SendReq extends Thread{
		private int plantid;
		private String userid;
		private String publishUserType;
		private String platform;
		private String couponType;
		
		SendReq(int plantid,String userid,String publishUserType,String platform,String couponType){
			this.plantid=plantid;
			this.userid=userid;
			this.publishUserType=publishUserType;
			this.platform=platform;
			this.couponType=couponType;
		}
		
		public void run(){
			Map<String, Object> reqMap = new HashMap<String, Object>();
			reqMap.put("couponType", couponType);
			reqMap.put("planid", plantid);
			reqMap.put("userid", userid);
			reqMap.put("platform", platform);
			reqMap.put("publishUserType", publishUserType);
			
			String result = HTTPUtils.sendPost("http://localhost:8080/jsapi/coupon/receive", reqMap, 10000);
			System.out.println(result);
		}
	}
}
