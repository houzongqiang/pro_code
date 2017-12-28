package com.houzq.mock.outOfMemory;

import java.util.ArrayList;
import java.util.List;
/**
 * 堆  内存溢出测试
 * @author JohnD
 * -Xms20M -Xmx20M -XX:+HeapDumpOnOutOfMemoryError
 * 
 * 
 * java.lang.OutOfMemoryError: Java heap space
 */
public class HeapOOM {

	static class OOMObject {

	}

	public static void main(String[] args) {
		List<OOMObject> list =new ArrayList<OOMObject>();
		while (true) {
			list.add(new OOMObject());
			//System.out.println("size : " + i);  
//			Runtime runtime = Runtime.getRuntime();  
//			System.out.printf("maxMemory : %.2fM\n", runtime.maxMemory()*1.0/1024/1024);  
//			System.out.printf("totalMemory : %.2fM\n", runtime.totalMemory()*1.0/1024/1024);  
//			System.out.printf("freeMemory : %.2fM\n", runtime.freeMemory()*1.0/1024/1024);  
//			
		}
	}

}
