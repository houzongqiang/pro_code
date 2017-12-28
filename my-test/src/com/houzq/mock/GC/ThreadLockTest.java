package com.houzq.mock.GC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ThreadLockTest {
	/**
	 * 线程死循环
	 */
	public static void createBusyThread() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {

				}

			}
		}, "testBusyThread");
		thread.start();
	}

	public static void createLockThread(final Object lock) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (lock) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		}, "testLockThread");
		thread.start();
	}

	public static void main(String[] args) throws IOException {
		BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
		bReader.readLine();
		createBusyThread();
		bReader.readLine();
		Object obj = new Object();
		createLockThread(obj);
	}

}
