package cn.itcast.bos.test;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.Jedis;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class JedisTest {
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Test
	public void testJedis(){
		redisTemplate.opsForValue().set("name", "wangwu", 15, TimeUnit.SECONDS);
	}

}
