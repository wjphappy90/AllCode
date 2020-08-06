package cn.itcast.core.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
public class TestJedisCluster {
	
	@Autowired
	private JedisCluster jedis;

	@Test
	public void testJedisCluster() throws Exception {
		//设置redis集群的ip和端口
//		Set<HostAndPort> nodes = new HashSet<>();
//		nodes.add(new HostAndPort("192.168.200.128", 7001));
//		nodes.add(new HostAndPort("192.168.200.128", 7002));
//		nodes.add(new HostAndPort("192.168.200.128", 7003));
//		nodes.add(new HostAndPort("192.168.200.128", 7004));
//		nodes.add(new HostAndPort("192.168.200.128", 7005));
//		nodes.add(new HostAndPort("192.168.200.128", 7006));
//		//创建redis集群对象
//		JedisCluster jedis = new JedisCluster(nodes);
		jedis.set("key4", "xxxxxxxxxxxx");
		System.out.println("========" + jedis.get("key4"));
	}
}
