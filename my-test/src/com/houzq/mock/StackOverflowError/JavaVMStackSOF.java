package com.houzq.mock.StackOverflowError;
/**
 * statckOverFlowError 返回栈允许深度
 * @author JohnD
 *
 */
public class JavaVMStackSOF {
	private int statckLength = 1;

	public void statckleak() {
		statckLength++;
		statckleak();
	}

	public static void main(String[] args) {
		JavaVMStackSOF oom = new JavaVMStackSOF();
		try {
			oom.statckleak();
		} catch (Throwable e) {
			System.out.println("length:" + oom.statckLength);//length:18863 不是固定会变化；
			throw e;
		}
	}

}
