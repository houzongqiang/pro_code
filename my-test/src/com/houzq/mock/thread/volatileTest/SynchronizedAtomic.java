package com.houzq.mock.thread.volatileTest;

/**
 * 
 * @author JohnD ----使用synchronized 加锁的方式 可以实现多线程访问时累加期望值；
 */
public class SynchronizedAtomic {
	public int inc = 0;

	public synchronized void increase() {
		inc++;
	}

	public static void main(String[] args) {
		final SynchronizedAtomic test = new SynchronizedAtomic();
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
