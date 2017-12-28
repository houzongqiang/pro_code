package com.houzq.mock.GC;
/**
 * 
 * @author JohnD
 * 死锁测试
 * 原因  【-128~127】 会被 缓存内存地址一致；当多个线程互相使用1，2时，互相等待死锁；
 */
public class DeadLockTest {
	static class SynAddRunable implements Runnable {
		int a, b;

		public SynAddRunable(int a, int b) {
			this.a = a;
			this.b = b;
		}

		@Override
		public void run() {
			synchronized (Integer.valueOf(a)) {
				synchronized (Integer.valueOf(b)) {
					System.out.println(a + b);
				}
			}
		}

	}

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			new Thread(new SynAddRunable(1, 2)).start();
			new Thread(new SynAddRunable(2, 1)).start();
		}
	}

}
