package cn.itcast.core.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.Jedis;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
public class TestJedis {
	
	@Autowired
	private Jedis jedis;

	@Test
	public void testJedis() throws Exception {
		//连接redis服务器
		//Jedis jedis = new Jedis("192.168.200.128", 6379);
		
		jedis.set("key3", "1");
		System.out.println("======" + jedis.get("key3"));
	}
}
