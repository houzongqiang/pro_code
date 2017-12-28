package com.houzq.mock.thread;
/**
 * 
 * @author JohnD
 *
 * sleep 时并不释放资源；
 * join  线程变为顺序执行，执行后再向下执行；
 * interrupt    线程中断，抛出异常，下面线程继续执行；
 *
 */
public class Joining {
	public static void main(String args[]) {

		Sleeper sleeper = new Sleeper("sleepy", 15000),
				grumpy = new Sleeper("grumpy", 15000);
		Joiner joiner = new Joiner("Dopey", sleeper), 
				doc = new Joiner("Doc", grumpy);
		grumpy.interrupt();// 中途被中断，所以 doc join 可以很快执行完成；而dopey 一直在等sleepy 线程完成；
	}
}
