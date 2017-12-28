package com.houzq.mock.classloader;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 
 * @author JohnD
 *  动态代理生成
 */
public class DynamicProxyTest {
	interface Ihello {
		void sayHello();
	}

	static class Hello implements Ihello {

		@Override
		public void sayHello() {
			System.out.println("hello world!");
		}

	}

	static class DynamicProxy implements InvocationHandler {
		Object originalObj;

		Object bind(Object originalObj) {
			this.originalObj = originalObj;
			return Proxy.newProxyInstance(originalObj.getClass().getClassLoader(),
					originalObj.getClass().getInterfaces(), this);
		}

		@Override
		public Object invoke(Object proxy, Method paramMethod, Object[] paramArrayOfObject) throws Throwable {
			System.out.println("welcome");
			return paramMethod.invoke(originalObj, paramArrayOfObject);
		}

	}

	public static void main(String[] args) {
		Ihello hIhello=(Ihello)new DynamicProxy().bind(new Hello());
		hIhello.sayHello();
	}

}
