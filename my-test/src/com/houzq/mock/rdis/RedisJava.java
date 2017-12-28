package com.houzq.mock.rdis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.exceptions.JedisAskDataException;

/***
 * redis 所有类型操作的方法
 * 
 * @author JohnD
 *
 */

public class RedisJava {

	public static void main(String[] args) {
		// testString();
		// testMap();
		// testList();
		// testSet();
		// testMget();
		// testPipline();

		// RedisJava re=new RedisJava();
		// re.testSetex();
		sortSet();
	}

	/**
	 * 测试redis 分布锁 key 为需要抢的item 的id value 为抢到者的用户id等信息； 保证一个商品ID 被 一个用户抢的场景；
	 */
	public void testSetex() {
		String key = "itemid222";
		String value = "user1001001";
		for (int i = 0; i < 10; i++) {
			threadTest thread = new threadTest(key, value, "thread" + i);
			thread.start();
		}
	}

	public static void conn() {
		// ���ӱ��ص� Redis ����
		Jedis jedis = new Jedis("localhost");
		jedis.auth("admin");
		System.out.println("Connection to server sucessfully");
		// �鿴�����Ƿ�����
		System.out.println("Server is running: " + jedis.ping());
	}

	/**
	 * String 操作
	 */
	public static void testString() {
		Jedis jedis = new Jedis("localhost");
		jedis.auth("admin");
		// -----�������----------
		jedis.set("name", "xinxin");// ��key-->name�з�����value-->xinxin
		System.out.println(jedis.get("name"));// ִ�н����xinxin

		jedis.append("name", " is my lover"); // ƴ��
		System.out.println(jedis.get("name"));

		jedis.del("name"); // ɾ��ĳ����
		System.out.println(jedis.get("name"));
		// ���ö����ֵ��
		jedis.mset("name", "liuling", "age", "23", "qq", "476777XXX");
		jedis.incr("age"); // ���м�1����
		System.out.println(jedis.get("name") + "-" + jedis.get("age") + "-" + jedis.get("qq"));

	}

	public static void StringOpe() {
		// ���ӱ��ص� Redis ����
		Jedis jedis = new Jedis("localhost");
		jedis.auth("admin");
		System.out.println("Connection to server sucessfully");
		// ���� redis �ַ�������
		jedis.set("runoobkey", "Redis tutorial");
		// ��ȡ�洢�����ݲ����
		System.out.println("Stored string in redis:: " + jedis.get("runoobkey"));
	}

	public static void List() {
		// ���ӱ��ص� Redis ����
		Jedis jedis = new Jedis("localhost");
		jedis.auth("admin");
		System.out.println("Connection to server sucessfully");

		// �洢���ݵ��б���
		jedis.lpush("tutorial-list", "Redis");
		jedis.lpush("tutorial-list", "Mongodb");
		jedis.lpush("tutorial-list", "Mysql");
		// ��ȡ�洢�����ݲ����
		List<String> list = jedis.lrange("tutorial-list", 0, 5);
		for (int i = 0; i < list.size(); i++) {
			System.out.println("Stored string in redis:: " + list.get(i));
		}
	}

	/**
	 * hash 操作
	 */
	public static void testMap() {
		Jedis jedis = new Jedis("localhost");
		// jedis.auth("admin");
		// -----�������----------
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "xinxin");
		map.put("age", "22");
		map.put("qq", "123456");
		jedis.hmset("user", map);
		// ȡ��user�е�name��ִ�н��:[minxr]-->ע������һ�����͵�List
		// ��һ�������Ǵ���redis��map�����key����������Ƿ���map�еĶ����key�������key���Ը�������ǿɱ����
		List<String> rsmap = jedis.hmget("user", "name", "age", "qq");
		System.out.println(rsmap);

		// ɾ��map�е�ĳ����ֵ
		jedis.hdel("user", "age");
		jedis.hset("user", "age", "12");

		System.out.println(jedis.hgetAll("user"));
		System.out.println(jedis.hmget("user", "age")); // ��Ϊɾ���ˣ����Է��ص���null
		System.out.println(jedis.hlen("user")); // ����keyΪuser�ļ��д�ŵ�ֵ�ĸ���2
		System.out.println(jedis.exists("user"));// �Ƿ����keyΪuser�ļ�¼ ����true
		System.out.println(jedis.hkeys("user"));// ����map�����е�����key
		System.out.println(jedis.hvals("user"));// ����map�����е�����value

		Iterator<String> iter = jedis.hkeys("user").iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			System.out.println(key + ":" + jedis.hmget("user", key));
		}
	}

	/**
	 * List 操作
	 */
	public static void testList() {
		Jedis jedis = new Jedis("localhost");
		// jedis.auth("admin");
		// ��ʼǰ�����Ƴ����е�����
		jedis.del("java framework");
		System.out.println(jedis.lrange("java framework", 0, -1));
		// ����key java framework�д����������
		jedis.lpush("java framework", "spring");
		jedis.lpush("java framework", "struts");
		jedis.lpush("java framework", "hibernate");
		// ��ȡ����������jedis.lrange�ǰ���Χȡ����
		// ��һ����key���ڶ�������ʼλ�ã��������ǽ���λ�ã�jedis.llen��ȡ����
		// -1��ʾȡ������
		System.out.println(jedis.lrange("java framework", 0, -1));
		// jedis.zadd(key, scoreMembers);

		jedis.del("java framework");
		jedis.rpush("java framework", "spring");
		jedis.rpush("java framework", "struts");
		jedis.rpush("java framework", "hibernate");
		System.out.println(jedis.lrange("java framework", 0, -1));
		// jedis.linsert(key, where, pivot, value);
		// jedis.lset(key, index, value);// 指定下标元素值； 0正式从前向后，-1 从后面向前；
		// jedis.lrem(key, count, value);// 删除count 个 value相同值元素；count>0 从头到尾
		// ；count＜0从尾到头；count 0 删除全部
		// jedis.ltrim(key, start, end);// 保留指定key 的值范围内的数据; 其余删除；
		jedis.lpop("");// 从list 头部返回元素并删除；
		jedis.rpop("");// 从list的尾部删除元素，并返回删除元素：
		// jedis.rpoplpush(srckey, dstkey);//
		// 从第一个list的尾部移除元素并添加到第二个list的头部,最后返回被移除的元素值，整个操作是原子的.如果第一个list是空或者不存在返回nil：
		// jedis.lindex(key, index);// 返回名称为key的list中index位置的元素：
		jedis.llen("");// 返回key对应list的长度：
	}

	/**
	 * SET 操作
	 */
	public static void testSet() {
		Jedis jedis = new Jedis("localhost");
		// jedis.auth("admin");
		// ���
		jedis.sadd("user1", "liuling");
		jedis.sadd("user1", "xinxin");
		jedis.sadd("user1", "ling");
		jedis.sadd("user1", "zhangxinxin");
		jedis.sadd("user1", "who");
		// �Ƴ�noname
		jedis.srem("user1", "who");
		System.out.println(jedis.smembers("user1"));// 查询SET 元素；
		System.out.println(jedis.sismember("user1", "who"));// �ж� who
															// �Ƿ���user���ϵ�Ԫ��
		System.out.println(jedis.srandmember("user1"));
		System.out.println(jedis.scard("user1"));// ���ؼ��ϵ�Ԫ�ظ���

		// jedis.srem(key, member);// 删除名称为key的set中的元素member：
		/*
		 * jedis.spop("");// 随机返回并删除名称为key的set中一个元素： jedis.sdiff("key1", "key2",
		 * "key3");// 返回所有给定key的set 与第一个key的set差集： jedis.sdiffstore("storkey",
		 * "key1", "key2");// 返回所有给定key与第一个key的差集，并将结果存为另一个key：
		 * jedis.sinter("key1", "key2");// 返回所有给定key的set 交集：
		 * jedis.sinterstore("storkey", "key1", "key2");//
		 * 返回所有给定key的交集，并将结果存为另一个storkey jedis.sunion("key1", "key2");//
		 * 返回所有给定key的并集 jedis.sunionstore("storkey", "key1", "key2");//
		 * 返回所有给定key的并集，并将结果存为另一个storkey jedis.smove("key1", "key2", "three");//
		 * 从第一个key对应的set中移除member并添加到第二个对应set中 jedis.scard("key1");//
		 * 返回名称为key的set的元素个数 jedis.sismember("key1", "three");//
		 * 测试member是否是名称为key的set的元素 jedis.srandmember("key1");//
		 * 随机返回名称为key的set的一个元素，但是不删除元素
		 */ }

	public static void keys() {
		// ���ӱ��ص� Redis ����
		Jedis jedis = new Jedis("localhost");
		jedis.auth("admin");
		System.out.println("Connection to server sucessfully");

		// ��ȡ���ݲ����
		Set<String> set = jedis.keys("*");
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			System.out.println("List of stored keys:: " + key);
		}

	}

	public void test() throws InterruptedException {
		Jedis jedis = new Jedis("localhost");
		jedis.auth("admin");
		// jedis ����
		// ע�⣬�˴���rpush��lpush��List�Ĳ�������һ��˫���������ӱ��������ģ�
		jedis.del("a");// ��������ݣ��ټ������ݽ��в���
		jedis.rpush("a", "1");
		jedis.lpush("a", "6");
		jedis.lpush("a", "3");
		jedis.lpush("a", "9");

		System.out.println(jedis.lrange("a", 0, -1));// [9, 3, 6, 1]
		System.out.println(jedis.sort("a")); // [1, 3, 6, 9] //�����������
		System.out.println(jedis.lrange("a", 0, -1));
	}

	/**
	 * sorted set 操作 sorted set的实现是skip list和hash table的混合体
	 */
	public static void sortSet() {
		Jedis jedis = new Jedis("localhost");
		// jedis.auth("admin");

		jedis.zadd("myzset", 1, "one Mem1");// 新增member 带上scores
		jedis.zadd("myzset", 2, "two Mem1");
		System.out.println(jedis.zrange("myzset", 0, -1));
		jedis.zrem("myzset", "two Mem");// 删除名称为key的zset中的元素member
		jedis.zincrby("myzset", 2, "two Mem");// 如果在名称为key的zset中已经存在元素member，则该元素的score增加increment;否则向集合中添加该元素，其score的值为increment
		System.out.println(jedis.zrank("myzset", "two Mem"));// 返回名称为key的zset中member元素的排名(按score从小到大排序)即下标
		System.out.println(jedis.zrevrange("myzset", 0, 2));// 返回名称为key的zset(按score从大到小排序)中的index从start到end的所有元素,从0开始
		// jedis.zrangeByScore(key, min, max);//返回集合中score在给定区间的元素
		// jedis.zcount(key, min, max);//返回集合中score在给定区间的数量
		// jedis.zcard(key);//返回集合中元素个数
		System.out.println(jedis.zscore("myzset", "two Mem"));// 返回给定元素对应的score
		// jedis.zremrangeByRank(key, start, end);//删除集合中排名在给定区间的元素
		// jedis.zremrangeByScore(key, start, end);//删除集合中score在给定区间的元素
	}

	public static void testMget() {
		Jedis jedis = new Jedis("localhost");
		Set<String> keys = jedis.keys("fail*");
		List<String> result = new ArrayList();
		long t1 = System.currentTimeMillis();
		for (String key : keys) {
			result.add(jedis.get(key));
		}
		for (String src : result) {
			System.out.println(src);
		}
		System.out.println(System.currentTimeMillis() - t1);
	}

	/**
	 * pipelined 取数据效率比get 高10倍 syncAndReturnAll异步把多条命令处理结果一起返回
	 */
	public static void testPipline() {
		Jedis jedis = new Jedis("localhost");
		Set<String> keys = jedis.keys("fail*");
		List<Object> result = new ArrayList();
		Pipeline pipelined = jedis.pipelined();
		long t1 = System.currentTimeMillis();
		for (String key : keys) {
			pipelined.get(key);
		}
		result = pipelined.syncAndReturnAll();
		for (Object src : result) {
			System.out.println(src);
		}
		System.out.println(System.currentTimeMillis() - t1);
	}

	class threadTest extends Thread {
		private String key;
		private String value;
		private String threadName;

		threadTest(String key, String value, String threadName) {
			this.key = key;
			this.value = value;
			this.threadName = threadName;
		}

		public void start() {
			Jedis jedis = new Jedis("localhost");
			Long result = jedis.setnx(key, value);
			if (result == 1) {
				System.out.println(threadName + " get ...");
			} else {
				System.out.println(threadName + " lock...");
			}
		}
	}

}
