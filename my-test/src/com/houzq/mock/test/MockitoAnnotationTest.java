package com.houzq.mock.test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
/**
 *  MOCK 方式进行单元测试的所有操作
 * @author JohnD
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class MockitoAnnotationTest {
	@Mock
	private List mockList;

	/**
	 * 使用注解定义模拟对象
	 */
	@Test
	public void shorthand() {
		mockList.add(1);
		Mockito.verify(mockList).add(1);
	}

	// public MockitoAnnotationTest() {
	// MockitoAnnotations.initMocks(this);
	// }
	/**
	 * 模拟连接调用的返回值 注意需要在一行里写thenReturn 否则只有最后一行规定有效；
	 */
	@Test(expected = RuntimeException.class)
	public void consecutive_calls() {
		// 模拟连续调用返回期望值，如果分开，则只有最后一个有效
		Mockito.when(mockList.get(0)).thenReturn(0);
		Mockito.when(mockList.get(0)).thenReturn(1);
		Mockito.when(mockList.get(0)).thenReturn(2);
		Mockito.when(mockList.get(1)).thenReturn(0).thenReturn(1).thenThrow(new RuntimeException());
		Assert.assertEquals(2, mockList.get(0));
		Assert.assertEquals(2, mockList.get(0));
		Assert.assertEquals(0, mockList.get(1));
		Assert.assertEquals(1, mockList.get(1));
		// 第三次或更多调用都会抛出异常
		mockList.get(1);
	}

	/**
	 * 使用参数生成期望值
	 */
	@Test
	public void answer_with_callback() {
		// 使用Answer来生成我们我们期望的返回
		Mockito.when(mockList.get(Mockito.anyInt())).thenAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();// 请求参数
				return "hello world:" + args[0];
			}
		});
		Assert.assertEquals("hello world:0", mockList.get(0));
		Assert.assertEquals("hello world:999", mockList.get(999));
	}

	/**
	 * 监控真实对象
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public void spy_on_real_objects() {
		List list = new LinkedList();
		List spy = Mockito.spy(list);
		// 下面预设的spy.get(0)会报错，因为会调用真实对象的get(0)，所以会抛出越界异常
		// when(spy.get(0)).thenReturn(3);

		// 使用doReturn-when可以避免when-thenReturn调用真实对象api
		Mockito.doReturn(999).when(spy).get(999);
		// 预设size()期望值
		Mockito.when(spy.size()).thenReturn(100);
		// 调用真实对象的api
		spy.add(1);
		spy.add(2);
		Assert.assertEquals(100, spy.size());
		Assert.assertEquals(1, spy.get(0));
		Assert.assertEquals(2, spy.get(1));
		Mockito.verify(spy).add(1);
		Mockito.verify(spy).add(2);
		Assert.assertEquals(999, spy.get(999));
		spy.get(2);
	}

	/**
	 * 使用anser 对模拟对象返回值进行设置
	 */
	@Test
	public void unstubbed_invocations() {
		// mock对象使用Answer来对未预设的调用返回默认期望值
		List mock = Mockito.mock(List.class, new Answer() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				return 999;
			}
		});
		// 下面的get(1)没有预设，通常情况下会返回NULL，但是使用了Answer改变了默认期望值
		Assert.assertEquals(999, mock.get(1));
		// 下面的size()没有预设，通常情况下会返回0，但是使用了Answer改变了默认期望值
		Assert.assertEquals(999, mock.size());
	}

	/**
	 * 捕获参数进一步断言
	 * 
	 */
	public void capturing_args() {
		PersonDao personDao = Mockito.mock(PersonDao.class);
		PersonService personService = new PersonService(personDao);

		ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);
		personService.update(1, "jack");
		Mockito.verify(personDao).update(argument.capture());
		Assert.assertEquals(1, argument.getValue().getId());
		Assert.assertEquals("jack", argument.getValue().getName());
	}

	class Person {
		private int id;
		private String name;

		Person(int id, String name) {
			this.id = id;
			this.name = name;
		}

		public int getId() {
			return id;
		}

		public String getName() {
			return name;
		}
	}

	interface PersonDao {
		public void update(Person person);
	}

	class PersonService {
		private PersonDao personDao;

		PersonService(PersonDao personDao) {
			this.personDao = personDao;
		}

		public void update(int id, String name) {
			personDao.update(new Person(id, name));
		}
	}

	/**
	 * 执行真实部分的mock
	 */
	@Test
	public void real_partial_mock() {
		// 通过spy来调用真实的api
		List list = Mockito.spy(new ArrayList());
		Assert.assertEquals(0, list.size());
		A a = Mockito.mock(A.class);
		// 通过thenCallRealMethod来调用真实的api
		Mockito.when(a.doSomething(Mockito.anyInt())).thenCallRealMethod();
		Assert.assertEquals(999, a.doSomething(999));
	}

	class A {
		public int doSomething(int i) {
			return i;
		}
	}

	/**
	 * 重置Mock
	 */
	@Test
	public void reset_mock() {
		List list = Mockito.mock(List.class);
		Mockito.when(list.size()).thenReturn(10);
		list.add(1);
		Assert.assertEquals(10, list.size());
		// 重置mock，清除所有的互动和预设
		Mockito.reset(list);
		Assert.assertEquals(0, list.size());
	}

}
