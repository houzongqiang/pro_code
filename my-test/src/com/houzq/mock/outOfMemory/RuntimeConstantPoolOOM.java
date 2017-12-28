package com.houzq.mock.outOfMemory;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author JohnD
 *-XX:PermSize=10M -XX:MaxPermSize=10M
 *Java HotSpot(TM) 64-Bit Server VM warning: ignoring option PermSize=10M; support was removed in 8.0
  Java HotSpot(TM) 64-Bit Server VM warning: ignoring option MaxPermSize=10M; support was removed in 8.0
 *JDK 1.6 及以前版本常量池放在方法区 
 *1.7 后逐步去掉‘去永久代’，常量池存的不是对象，引是对象的引用，真正的对象在堆中；intern() 不再是复制对象而是引用；
 *
 * 修改为 -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * java.lang.OutOfMemoryError: Java heap space   表示写在堆上堆内存不足了；
 *    
 */
public class RuntimeConstantPoolOOM {

	public static void main(String[] args) {
		// 使用List保持着常量池引用，避免FULL GC回收常量池行为
		List<String> list = new ArrayList<String>();
		int i = 0;
		// 10M 的permsize 在integer 范围内足够产生OOM
		while (true) {
			list.add(String.valueOf(i).intern()); // 使用intern
													// 动态放入常量池；一般在运行前加入常量池的；
							//JDK6 是数据复制，JDK7以后就是复制的引用加入常量池，故现在是堆溢出而不是方法区溢出；
		}
	}

}
