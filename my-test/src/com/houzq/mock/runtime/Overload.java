package com.houzq.mock.runtime;

public class Overload {
	public static void sayHello(Object arg) {
		System.out.println("hello object ");
	}

	public static void sayHello(int arg) {
		System.out.println("hello int ");
	}

	/*
	 * public static void sayHello(char arg) {
	 * System.out.println("hello char "); }
	 */

	public static void sayHello(long arg) {
		System.out.println("hello long ");
	}

	public static void sayHello(Character arg) {
		System.out.println("hello character ");
	}

	public static void sayHello(char... arg) {
		System.out.println("hello char ... ");
	}

	public static void main(String[] args) {
		// 虚拟机找一个更适合的类型进行方法调用；
		sayHello('a');// char 或 int 97 char--int --long --double 最后转为封装类型Charactor 
	}

}
