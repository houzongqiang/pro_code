package com.houzq.mock.GC;

import java.util.Map;

/**
 * -XX:+PrintGCDetails
 * 
 * @author JohnD ---相互引用的方法GC回收的问题
 */
public class ReferenceCountingGC {

	public Object instance = null;
	private static final int _1MB = 1024 * 1024;
	/**
	 * 唯一目的占用内存，以便在GC日志中看清楚是否被回收过
	 */
	private byte[] bigSize = new byte[2 * _1MB];

	public static void testGC() {
		ReferenceCountingGC objA = new ReferenceCountingGC();
		ReferenceCountingGC objB = new ReferenceCountingGC();
		objA.instance = objB;
		objB.instance = objA;

		// 假设在这行进行GC objA 和objB能否被回收？
		System.gc();

	}

	/**
	 * 测试对象内存分配时的GC
	 * 
	 * -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
	 *   allocation1~3 被 放入eden 区；
	 *  发现大对象 allocation4 被放入到parOldGen 老年代；并不像JDK6时进行一步minor GC
	 *  
	 */
	public static void testAllocation() {
		byte[] allocation1, allocation2, allocation3, allocation4;
		allocation1 = new byte[2 * _1MB];
		allocation2 = new byte[2 * _1MB];
		allocation3 = new byte[2 * _1MB];
		allocation4 = new byte[4 * _1MB];// 出现一次minor GC
	}

	public static void main(String[] args) {
		//testGC();
		testAllocation();
	}
	
	public void test(){
		for(Map.Entry<Thread, StackTraceElement[]>statckTrace :Thread.getAllStackTraces().entrySet()){
			Thread thread =(Thread) statckTrace.getKey();
			StackTraceElement [] stack=(StackTraceElement [])statckTrace.getValue();
			if(thread.equals(Thread.currentThread()))
				continue;
		
			System.out.println("\n 线程："+thread.getName()+" \n");
			for(StackTraceElement element:stack){
				System.out.println("\t"+element+" \n");	
			}
			
		}
	}

}
