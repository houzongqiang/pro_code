package com.houzq.mock.suanfa;

/**
 * 冒泡排序 
 * 1）从数组头部选取数字与后面数据进行一一比较，如此数字大于后面数则交换；
 * 2）循环后一个与再一下一个数字比较直到与最后面数据比较，之后得到最大数字放在最后面；注： j只需要进行到倒数第二个，j+1 为最后一个，所以a.length -2 的位置与a.length-1 比较； 
 * 3）外层再选择循环除最大值外的列表，并执行上述；
 * 
 */
public class BubbleSort {
	public BubbleSort() {
		int a[] = { 49, 38, 65, 97, 76, 13, 27, 49, 78, 34, 12, 64, 5, 4, 62, 99, 98, 54, 56, 17, 18, 23, 34, 15, 35,
				25, 53, 51 };
		int temp = 0;
		for (int i = 0; i < a.length - 1; i++) {
			for (int j = 0; j < a.length - 1 - i; j++) {
				//System.out.println("j="+j+" a[j + 1]:"+a[j + 1]);
				if (a[j] > a[j + 1]) {
					temp = a[j];
					a[j] = a[j + 1];
					a[j + 1] = temp;
				}
			}
		}
		for (int i = 0; i < a.length; i++)
			System.out.println(a[i]);
	}

	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
		BubbleSort sort = new BubbleSort();
		System.out.println(System.currentTimeMillis());
	}
}
