package com.houzq.mock.outOfMemory;

import java.lang.reflect.Method;

import org.mockito.cglib.proxy.Enhancer;
import org.mockito.cglib.proxy.MethodInterceptor;
import org.mockito.cglib.proxy.MethodProxy;

import com.houzq.mock.outOfMemory.HeapOOM.OOMObject;
/**
 * -XX:PermSize=10M  -XX:MaxPerSize=10M
 * @author JohnD
 *   JDK 1.8  不再存在方法区（永久代）替换为元空间
 *   -XX:MaxMetaspaceSize=10m
 *   
 *   java.lang.OutOfMemoryError: Metaspace
	at java.lang.ClassLoader.defineClass1(Native Method)
	at java.lang.ClassLoader.defineClass(Unknown Source)
 */
public class JavaMethodAreaOOM {

	public static void main(String[] args) {
		while (true) {
			//Enhancer允许为非接口类型创建一个Java代理。Enhancer动态创建了给定类型的子类但是拦截了所有的方法。和Proxy不一样的是，不管是接口还是类他都能正常工作。
			Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(OOMObject.class);
			enhancer.setUseCache(false);
			enhancer.setCallback(new MethodInterceptor() {

				@Override
				public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
					return proxy.invokeSuper(obj, args);
				}
			});
			enhancer.create();
		}
	}

}
