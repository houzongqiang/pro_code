package com.houzq.mock.StackOverflowError;
/**
 * 测试栈最大深度
 * @author JohnD
 *  -Xss128K
 *  -Xss256K
 *  count 最大深度增加
 */
public class StackOverflowErrorTest {
	private static int count = 0;

	public static void recursion() {
		count++;
		recursion();
		System.out.println("deep of calling=" + count);
	}

	public static void main(String[] args) {
		try {
			recursion();
		} catch (Exception e) {
			System.out.println("deep of calling=" + count);
			//e.printStackTrace();
		}finally {
			System.out.println("deep of calling=" + count);
		}
	}
}
