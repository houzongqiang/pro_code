package com.houzq.mock.GC;

import java.util.ArrayList;
import java.util.List;
/**
 * 测试JConsole 可视化工具中GC 情况；
 * @author JohnD
 * 只配置堆100M 大小未配置年轻代大小， 看图得到年轻代应该40+S1 5M S2 5M 年老代50M 
 */

public class JconsoleTest {
	static class OOMObject {
		public byte[] placeholder = new byte[64 * 1024];

	}

	public static void filHeap(int num) throws InterruptedException {
		List<OOMObject> list = new ArrayList<OOMObject>();
		for (int i = 0; i < num; i++) {
			Thread.sleep(50);
			list.add(new OOMObject());
		}
		list=null;//这样老年代中才能回收对象；
		System.gc();
	}

	public static void main(String[] args) throws InterruptedException {
		filHeap(1000);
	}

}
