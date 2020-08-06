package cn.itcast.core.test;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.core.pojo.TbTest;
import cn.itcast.core.service.TbTestService;

//加载springJunit测试环境
@RunWith(value = SpringJUnit4ClassRunner.class)
//加载spring核心配置文件
@ContextConfiguration(locations = {"classpath:application-context.xml"})
public class TestTbTest {
	
	@Autowired
	private TbTestService tbService;

	@Test
	public void testInsertTb() throws Exception {
		TbTest tb = new TbTest();
		tb.setName("张三1");
		tb.setBirthday(new Date());
		tbService.insertTest(tb);
		
		TbTest tb1 = new TbTest();
		tb1.setName("李四");
		tb1.setBirthday(new Date());
		tbService.insertTest(tb1);
	}
}
