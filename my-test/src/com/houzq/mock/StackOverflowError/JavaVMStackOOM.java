package com.houzq.mock.StackOverflowError;
/**
 * 出现死机
 * @author JohnD
 * 线程数太多，内存被栈耗尽；
 */
public class JavaVMStackOOM {
	private void dontstop() {
		while (true) {

		}
	}

	public void statckLeakByThread() {
		while (true) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					dontstop();
				}
			});
			thread.start();
		}
	}

	public static void main(String[] args) {
		JavaVMStackOOM oom = new JavaVMStackOOM();
		oom.statckLeakByThread();
	}

}
