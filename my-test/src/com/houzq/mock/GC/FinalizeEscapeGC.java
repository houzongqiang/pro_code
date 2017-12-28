package com.houzq.mock.GC;

/**
 * 一次对象自我拯救的演示
 * 
 * @author JohnD 些代码演示两点 1、对象可以GC时自我拯救；
 *         2、这种自救的机会只有一次，因为对象的finalize()方法最多只会被系统调用一次
 * 
 *         finalize method executed! 
 *         yes,i am still alive!! 
 *         no ,i am dead!!
 * 
 */
public class FinalizeEscapeGC {
	public static FinalizeEscapeGC SAVE_HOOK = null;

	public void isAlive() {
		System.out.println("yes,i am still alive!!");
	}

	@Override
	public void finalize() throws Throwable {
		super.finalize();
		System.out.println("finalize method executed!");
		FinalizeEscapeGC.SAVE_HOOK = this;
	}

	public static void main(String[] args) throws InterruptedException {
		SAVE_HOOK = new FinalizeEscapeGC();
		// 对象每次成功拯救自己
		SAVE_HOOK = null;
		System.gc();
		// 因为finalize 方法优先级很低所以主线程暂停0.5秒
		Thread.sleep(500);

		if (SAVE_HOOK != null) {
			SAVE_HOOK.isAlive();
		} else {
			System.out.println("no ,i am dead!!");
		}

		// 下面这段代码与上面一样，但是自救却失败了；
		SAVE_HOOK = null;
		System.gc();
		// 因为finalize 方法优先级很低所以主线程暂停0.5秒
		Thread.sleep(500);

		if (SAVE_HOOK != null) {
			SAVE_HOOK.isAlive();
		} else {
			System.out.println("no ,i am dead!!");
		}
	}

}
