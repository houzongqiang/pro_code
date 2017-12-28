package com.houzq.mock.thread.volatileTest;

/**
 * volatile 关键字----- 测试不保证原子性，多线程做累加时不是期望值1000；
 * 
 * @author JohnD
 *
 */
public class VolatileAtomicTest {

	public volatile int inc = 0;

	public void increase() {
		inc++;
	}

	public static void main(String[] args) {
		final VolatileAtomicTest test = new VolatileAtomicTest();
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
