package com.houzq.mock.outOfMemory;

public class RuntimeConstantPoolOOM2 {

	public static void main(String[] args) {
		String str1 = new StringBuffer("计算机").append("软件").toString();// JDK7 str1 表示指向堆的地址 
		System.out.println(str1.intern() == str1);// JDK7 中intern 首次也是将变量在堆中地址放入常量池；

		String str2 = new StringBuffer("ja").append("va").toString();//str2  指向堆地址
		System.out.println(str2.intern() == str2);// intern  向常量池中写入str2 的堆地址是发现已经存在该字符串，则返回常量池中此字符串地址；常量池中地址与堆地址不一致；
	}

}
