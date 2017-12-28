package com.houzq.mock.Stress;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.hexun.framework.core.utils.HTTPUtils;

/**
 * 模拟多线程并发访问接口，测试redis 和事务
 * iphone8Coupon 抢券并发测试；
 * @author JohnD
 *
 */
public class ThreadsDBReadLockTest {
	public int[] useridarray = { 8888881, 8888882, 8888883, 8888881, 8888881, 8888884, 8888885 };
	public int[] orderarray = { 1222222, 122122155, 554554523, 888888, 554544545, 18563555, 33446554 };

	public static void main(String[] args) {
		ThreadsDBReadLockTest dbTest = new ThreadsDBReadLockTest();
		// dbTest.test();
		dbTest.test1();
	}

	/**
	 * 同一用户并发访问出现的问题模拟
	 */
	public void test() {
		for (int i = 0; i < 50; i++) {
			int userid = 888888 + i;
			int order = new Random().nextInt();
			System.out.println(userid + "  " + order);
			IponeTestThread thread = new IponeTestThread(userid, order);
			thread.start();
		}
	}

	/**
	 * 同一用户并发访问出现的问题模拟
	 */

	public void test1() {
		for (int i = 0; i < useridarray.length; i++) {
			int userid = useridarray[i];
			int order = orderarray[i];
			System.out.println(userid + "  " + order);
			IponeTestThread thread = new IponeTestThread(userid, order);
			thread.start();
		}
	}

	class IponeTestThread extends Thread {
		private int userid;
		private int order;

		IponeTestThread(int userid, int order) {
			this.userid = userid;
			this.order = order;
		}

		public void run() {
			Map<String, Object> reqMap = new HashMap<String, Object>();
			reqMap.put("userid", userid);
			reqMap.put("orderid", order);
			reqMap.put("productName", "test");
			String result = HTTPUtils.sendPost("http://localhost:8080/jsapi/coupon/iphone8Coupon", reqMap, 10000);
			System.out.println(result);
		}
	}

}
