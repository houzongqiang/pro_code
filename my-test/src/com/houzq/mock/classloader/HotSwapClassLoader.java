package com.houzq.mock.classloader;

/**
 * 
 * @author JohnD 
 * 
 * 定义类加载器    --查找与默认类加载路径一致；
 *
 */

public class HotSwapClassLoader extends ClassLoader {
	public HotSwapClassLoader() {
		super(HotSwapClassLoader.class.getClassLoader());
	}

	public Class loadByte(byte[] classByte) {
		return defineClass(null, classByte, 0, classByte.length);
	}
}
