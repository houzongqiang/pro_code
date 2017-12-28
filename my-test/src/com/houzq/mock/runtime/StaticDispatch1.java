package com.houzq.mock.runtime;

import com.houzq.mock.runtime.StaticDispatch.Human;
import com.houzq.mock.runtime.StaticDispatch.Man;
import com.houzq.mock.runtime.StaticDispatch.Woman;

public class StaticDispatch1 {
	static abstract class Human {
		protected abstract void sayHello();
	}

	static abstract class Man extends Human {
		@Override
		public void sayHello() {
			System.out.println("man say hell");
		}
	}

	static abstract class Woman extends Human {
		@Override
		public void sayHello() {
			System.out.println("woman say hello");
		}
	}

	public static void main(String[] args) {
		// 虚拟机根据实际类型进行重写方法选择；

		Human man = new Man() {
		};
		Human woman = new Woman() {
		};
		man.sayHello();
		woman.sayHello();
		man = new Woman() {
		};
		man.sayHello();
	}

}
