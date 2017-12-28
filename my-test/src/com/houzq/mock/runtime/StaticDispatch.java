package com.houzq.mock.runtime;

public class StaticDispatch {
	static abstract class Human {

	}

	static abstract class Man extends Human {

	}

	static abstract class Woman extends Human {

	}

	public void sayHello(Human guy) {
		System.out.println("hell,guy!");
	}

	public void sayHello(Man guy) {
		System.out.println("hell,gentleman!");
	}

	public void sayHello(Woman guy) {
		System.out.println("hell,lady!");
	}

	public static void main(String[] args) {
		Human man = new Man() {//静态类型不变，实际类型发生变化

		};
		Human woman = new Woman() {//静态类型不变，实际类型发生变化
		};
		//使用哪个重载版本由于静态类型决定而不是实际类型决定；在编译阶段决定使用哪个重载方法；
		StaticDispatch str = new StaticDispatch();
		str.sayHello(man);
		str.sayHello(woman);
		str.sayHello((Man)man);//静态类型变化，实际类型不变
		str.sayHello((Woman)woman);//静态类型不变，实际类型不变
	}

}
