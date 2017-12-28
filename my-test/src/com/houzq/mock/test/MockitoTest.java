package com.houzq.mock.test;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.mockito.exceptions.verification.NoInteractionsWanted;
/**
 *  MOCK 方式进行单元测试的所有操作
 * @author JohnD
 *
 */
public class MockitoTest {
	/**
	 * 模拟对象行为进行验证
	 */
	@Test
	public void verify_behaviour() {
		// 模拟创建一个List对象
		List mock = Mockito.mock(List.class);
		// 使用mock的对象
		mock.add(1);
		mock.clear();
		// 验证add(1)和clear()行为是否发生
		Mockito.verify(mock).add(1);
		Mockito.verify(mock).clear();
	}

	/**
	 * 模拟方法返回，做断言
	 */
	@Test
	public void when_thenReturn() {
		// mock一个Iterator类
		Iterator iterator = Mockito.mock(Iterator.class);
		// 预设当iterator调用next()时第一次返回hello，第n次都返回world
		Mockito.when(iterator.next()).thenReturn("hello").thenReturn("world");
		// 使用mock的对象
		String result = iterator.next() + " " + iterator.next() + " " + iterator.next();
		// 验证结果
		Assert.assertEquals("hello world world", result);
	}

	/**
	 * 模拟不同入参返回结果
	 */
	@Test
	public void with_arguments() {
		Comparable comparable = Mockito.mock(Comparable.class);
		// 预设根据不同的参数返回不同的结果
		Mockito.when(comparable.compareTo("Test")).thenReturn(1);
		Mockito.when(comparable.compareTo("Omg")).thenReturn(2);
		Assert.assertEquals(1, comparable.compareTo("Test"));
		Assert.assertEquals(2, comparable.compareTo("Omg"));
		// 对于没有预设的情况会返回默认值
		Assert.assertEquals(0, comparable.compareTo("Not stub"));
	}

	/**
	 * 自定义参数对象的返回值 argThat 来实现 实现matches方法实现
	 */
	@Test
	public void with_unspecified_arguments() {
		List list = Mockito.mock(List.class);
		// 匹配任意参数
		Mockito.when(list.get(Mockito.anyInt())).thenReturn(1);
		Mockito.when(list.contains(Mockito.argThat(new IsValid()))).thenReturn(true);
		Assert.assertEquals(1, list.get(1));
		Assert.assertEquals(1, list.get(999));
		Assert.assertTrue(list.contains(1));
		Assert.assertTrue(!list.contains(3));
	}

	private class IsValid extends ArgumentMatcher<List> {
		@Override
		public boolean matches(Object o) {
			return (int) o == 1 || (int) o == 2;
		}
	}

	/**
	 * 使用参数匹配，必须都是通过matchers匹配;
	 */
	@Test
	public void all_arguments_provided_by_matchers() {
		@SuppressWarnings("rawtypes")
		Comparator comparator = Mockito.mock(Comparator.class);
		comparator.compare("nihao", "hello");
		// 如果你使用了参数匹配，那么所有的参数都必须通过matchers来匹配
		Mockito.verify(comparator).compare(Mockito.anyString(), Mockito.eq("hello"));
		Mockito.verify(comparator).compare(Mockito.anyString(), Mockito.anyString());
		// 下面的为无效的参数匹配使用
		// Mockito.verify(comparator).compare(Mockito.anyString(),"hello");
	}

	/**
	 * 模拟方法，并验证被调用次数
	 */
	@Test
	public void verifying_number_of_invocations() {
		List list = Mockito.mock(List.class);
		list.add(1);
		list.add(2);
		list.add(2);
		list.add(3);
		list.add(3);
		list.add(3);
		// 验证是否被调用一次，等效于下面的times(1)
		Mockito.verify(list).add(1);
		Mockito.verify(list, Mockito.times(1)).add(1);
		// 验证是否被调用2次
		Mockito.verify(list, Mockito.times(2)).add(2);
		// 验证是否被调用3次
		Mockito.verify(list, Mockito.times(3)).add(3);
		// 验证是否从未被调用过
		Mockito.verify(list, Mockito.never()).add(4);
		// 验证至少调用一次
		Mockito.verify(list, Mockito.atLeastOnce()).add(1);
		// 验证至少调用2次
		Mockito.verify(list, Mockito.atLeast(2)).add(2);
		// 验证至多调用3次
		Mockito.verify(list, Mockito.atMost(3)).add(3);
	}

	/**
	 * 模拟方法运行是抛异常的处理
	 */
	@Test(expected = RuntimeException.class)
	public void doThrow_when() {
		List list = Mockito.mock(List.class);
		Mockito.doThrow(new RuntimeException()).when(list).add(1);
		list.add(1);
	}

	/**
	 * 验证执行顺序 InOrder 对象
	 */
	@Test
	public void verification_in_order() {
		List list = Mockito.mock(List.class);
		List list2 = Mockito.mock(List.class);
		list.add(1);
		list2.add("hello");
		list.add(2);
		list2.add("world");
		// 将需要排序的mock对象放入InOrder
		InOrder inOrder = Mockito.inOrder(list, list2);
		// 下面的代码不能颠倒顺序，验证执行顺序
		inOrder.verify(list).add(1);
		inOrder.verify(list2).add("hello");
		inOrder.verify(list).add(2);
		inOrder.verify(list2).add("world");
	}

	/**
	 * 确保模拟对象无互动发生
	 */
	@Test
	public void verify_interaction() {
		List list = Mockito.mock(List.class);
		List list2 = Mockito.mock(List.class);
		List list3 = Mockito.mock(List.class);
		list.add(1);
		Mockito.verify(list).add(1);
		Mockito.verify(list, Mockito.never()).add(2);
		// 验证零互动行为
		Mockito.verifyZeroInteractions(list2, list3);
	}
	/**
	 * 检查模拟对象的所有行为都已被验证，有未验证抛异常
	 */
	@Test(expected = NoInteractionsWanted.class)  
	public void find_redundant_interaction(){  
	    List list = Mockito.mock(List.class);  
	    list.add(1);  
	    list.add(2);  
	    Mockito.verify(list,Mockito.times(2)).add(Mockito.anyInt());  
	    //检查是否有未被验证的互动行为，因为add(1)和add(2)都会被上面的anyInt()验证到，所以下面的代码会通过  
	    Mockito.verifyNoMoreInteractions(list);  
	  
	    List list2 = Mockito.mock(List.class);  
	    list2.add(1);  
	    list2.add(2);  
	    Mockito.verify(list2).add(1);  
	    //检查是否有未被验证的互动行为，因为add(2)没有被验证，所以下面的代码会失败抛出异常  
	    Mockito.verifyNoMoreInteractions(list2);  
	}  
}
