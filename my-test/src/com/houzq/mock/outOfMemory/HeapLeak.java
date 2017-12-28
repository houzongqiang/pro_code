package com.houzq.mock.outOfMemory;

import java.util.ArrayList;
import java.util.List;
/**
 * 堆  内存泄露
 * @author JohnD
 * -Xms20M -Xmx20M -XX:+HeapDumpOnOutOfMemoryError
 * 
 * 
 *  java.lang.OutOfMemoryError.<init>()V (Unknown Source)
 */
public class HeapLeak {
	static class Person {

	}

	public static void main(String[] agrgs) {
		List<Person> list = new ArrayList<Person>();
		while (1 < 2) {
			Person person = new Person();
			list.add(person);
			person = null;
			// 此时，所有的person对象都没有被释放，因为变量list引用这些对象。
			// 对象加入到list后，还必须从list中删除，最简单释放方法就是将list对象设置为null。
		}

	}
}
