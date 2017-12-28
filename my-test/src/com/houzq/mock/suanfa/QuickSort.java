package com.houzq.mock.suanfa;

/**
 * 
 * @author JohnD
 *
 *         快速排序--- 
 *         1)从最开始位置选择一个值 做为“基准”最划分两段 的中间值，
 *         2)然后从队列最右面向前找，直到找到比中间值小的值赋值给最左边值（第一次最左边为中间值本身）
 *         3)然后再从左向右查找，直到找到大于中间值然后把此值赋值给 最右边值（也是刚才向左边赋值的值的位置），
 *         4)如此之后再从右开始重复并替换，直到 最左边指针与最右边位置相同；
 *         5)这样把中间值放在中间，保证左边是队列都小于，右边队列都大于中间值；然后再递归左右各个子队列；
 *
 */
// http://blog.csdn.net/pzhtpf/article/details/7560294
public class QuickSort {
	int a[] = { 49, 38, 65, 97, 76, 13, 27, 49, 78, 34, 12, 64, 5, 4, 62, 99, 98, 54, 56, 17, 18, 23, 34, 15, 35, 25,
			53, 51 };

	public QuickSort() {

		quick(a);

		for (int i = 0; i < a.length; i++)

			System.out.println(a[i]);

	}

	public int getMiddle(int[] list, int low, int high) {

		int tmp = list[low]; // 数组的第一个作为中轴

		while (low < high) {

			while (low < high && list[high] >= tmp) {

				high--;

			}

			list[low] = list[high]; // 比中轴小的记录移到低端

			while (low < high && list[low] <= tmp) {

				low++;

			}

			list[high] = list[low]; // 比中轴大的记录移到高端

		}

		list[low] = tmp; // 中轴记录到尾

		return low; // 返回中轴的位置

	}

	public void _quickSort(int[] list, int low, int high) {

		if (low < high) {

			int middle = getMiddle(list, low, high); // 将list数组进行一分为二

			_quickSort(list, low, middle - 1); // 对低字表进行递归排序

			_quickSort(list, middle + 1, high); // 对高字表进行递归排序

		}

	}

	public void quick(int[] a2) {

		if (a2.length > 0) { // 查看数组是否为空

			_quickSort(a2, 0, a2.length - 1);

		}

	}

	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
		QuickSort sort = new QuickSort();
		System.out.println(System.currentTimeMillis());
	}

}