package com.houzq.mock.outOfMemory;

import java.lang.reflect.Field;
import sun.misc.Unsafe;

/**
 * -Xmx20M -XX:MaxDirectMemorySize=10m
 * @author JohnD 直接内存溢出
 * 
 * java.lang.OutOfMemoryError
 *  dump 文件很小，程序间接使用NIO
 *  Exception in thread "main" java.lang.OutOfMemoryError
	at sun.misc.Unsafe.allocateMemory(Native Method)
	at com.houzq.mock.outOfMemory.DriectMemoryOOM.main(DriectMemoryOOM.java:21)
 */
public class DriectMemoryOOM {
	private static final int _1MB = 1024 * 1024;

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		Field unsafeField = Unsafe.class.getDeclaredFields()[0];//使用Unsafe 直接 操作内存进行分配；
		unsafeField.setAccessible(true);
		Unsafe unsafe = (Unsafe) unsafeField.get(null);
		while (true) {
			unsafe.allocateMemory(_1MB);
		}
	}

}
