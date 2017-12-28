package com.houzq.mock.thread.volatileTest;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @author JohnD
 *
 *         采用JUC 包的数据类型保证原子 性操作，得到多线程累加期望值 ；
 *
 */
public class AtomicIntegerTest {
	public AtomicInteger inc = new AtomicInteger();

	public void increase() {
		inc.getAndIncrement();
	}

	public static void main(String[] args) {
		final AtomicIntegerTest test = new AtomicIntegerTest();
		for (int i = 0; i < 10; i++) {
			new Thread() {
				public void run() {
					for (int j = 0; j < 1000; j++)
						test.increase();
				};
			}.start();
		}

		while (Thread.activeCount() > 1) // 保证前面的线程都执行完
			Thread.yield();
		System.out.println(test.inc);
	}
}
