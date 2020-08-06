package cn.itcast.bos.action.test;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.bos.dao.base.StandardDao;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.base.StandardService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class JPATest {
	@Autowired
	private StandardDao dao;
	@Autowired
	private StandardService service;
	
	@Test
	public void testSave(){
		Standard s = new Standard();
		s.setMaxLength(10);
		s.setMaxWeight(10);
		s.setMinLength(1);
		s.setMinWeight(1);
		s.setName("1-10");
		s.setOperatingCompany("昌平分公司");
		s.setOperatingTime(new Date());
		s.setOperator("admin");
		dao.save(s);
	}
	
	@Test
	public void testFindAll(){
		List<Standard> list = dao.findAll();
		System.out.println(list);
	}
	@Test
	public void testFindOne(){
		Standard standard = dao.findOne(1);
		System.out.println(standard);
	}
	
	@Test
	public void testFindByName(){
		Standard standard = dao.findByName("1-10");
		System.out.println(standard);
	}
	
	@Test
	public void testFindByOperator(){
		List<Standard> list = dao.findByOperator("admin");
		System.out.println(list);
	}
	
	@Test
	public void testFindByOperatorLike(){
		List<Standard> list = dao.findByOperatorLike("%a%");
		System.out.println(list);
	}
	
	@Test
	public void testFindByOperatorAndName(){
		List<Standard> list = dao.findByOperatorAndName("admin","1-10");
		System.out.println(list);
	}
	@Test
	public void testFindByOperatorLikeOrNameLike(){
		List<Standard> list = dao.findByOperatorLikeOrNameLike("admin","1-10");
		System.out.println(list);
	}
	
	@Test
	public void testFindByXXXXX(){
		List<Standard> list = dao.findByXXXXX("admin");
		System.out.println(list);
	}
	@Test
	public void testUpdateOperator(){
		service.updateOperator("AAAA",1);
	}

}
