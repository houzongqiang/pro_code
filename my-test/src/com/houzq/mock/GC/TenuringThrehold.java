package com.houzq.mock.GC;
/**
 * 
 * @author JohnD
 *-Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8  -XX:MaxTenuringThreshold=1
 *  设置XX:MaxTenuringThreshold 次数来从年轻代进入年老代
 *  JDK8  运行修改此参数并没有变化，可能是GC 的模式已经变化了；
 *  
 *  Heap
 PSYoungGen      total 9216K, used 5664K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
  eden space 8192K, 69% used [0x00000000ff600000,0x00000000ffb88098,0x00000000ffe00000)
  from space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
  to   space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
 ParOldGen       total 10240K, used 4096K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
  object space 10240K, 40% used [0x00000000fec00000,0x00000000ff000010,0x00000000ff600000)
 Metaspace       used 2604K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 288K, capacity 386K, committed 512K, reserved 1048576K
    感觉如果设置新生代不足直接放入了年老代；
 */
public class TenuringThrehold {
	private static final int _1MB = 1024 * 1024;

	public static void testTenuringThrehold() {
		byte[] allocation1, allocation2, allocation3,allocation4;
		allocation1 = new byte[_1MB / 4];
		allocation2 = new byte[_1MB / 4];
		allocation3 = null;
		allocation3 = new byte[_1MB * 4];
		allocation4 = new byte[_1MB * 4];
		allocation4 = new byte[_1MB * 4];
	}

	public static void main(String[] args) {
		testTenuringThrehold();
	}

}
