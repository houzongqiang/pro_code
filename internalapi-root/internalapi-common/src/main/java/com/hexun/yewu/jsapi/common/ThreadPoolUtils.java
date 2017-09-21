package com.hexun.yewu.jsapi.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * 线程池工具类
 * @author zhoudong
 *
 */
public class ThreadPoolUtils {
	public static ExecutorService fixedThreadPool = null;
	static{
		fixedThreadPool = Executors.newFixedThreadPool(1);
	}
	
	public static void main(String[] args) {
		fixedThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				try {
					//需要执行的内容
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
