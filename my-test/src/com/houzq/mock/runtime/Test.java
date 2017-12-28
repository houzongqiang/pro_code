package com.houzq.mock.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
/**
 * 
 * @author JohnD
 *  通过 methodHandle  实现子类访问 上上层爷爷类的方法；
 */
public class Test {
	
	class Grandfather {
		void thinking() {
			System.out.println("i am Grandfather bbbb ");
		}
	}

	class Father extends Grandfather {
		void thinking1() {
			System.out.println("i am father aaa");
		}
	}

	class Son extends Father {
		void thinking() {
			try {
				MethodType mt = MethodType.methodType(void.class);//注意：这里是void 不是Void ；Void是一种类型,只能表示返回null
				MethodHandle mh = MethodHandles.lookup().findSpecial(Grandfather.class, "thinking", mt, getClass());
				mh.invoke(this);
				//执行father  的 thinking 方法而不是 Grandfather 类的thinking 方法？？如果不重名时则执行Grandfather 类的thinking 方法；
			} catch (Throwable e) {
				e.printStackTrace();
			}

		}
	}

	public static void main(String[] args) {
		(new Test().new Son()).thinking();
	}

}
