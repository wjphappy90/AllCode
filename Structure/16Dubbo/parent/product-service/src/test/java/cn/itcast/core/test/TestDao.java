package cn.itcast.core.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.core.dao.product.ProductDao;
import cn.itcast.core.pojo.product.Product;
import cn.itcast.core.pojo.product.ProductQuery;
import cn.itcast.core.pojo.product.ProductQuery.Criteria;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
public class TestDao {
	
	@Autowired
	private ProductDao productDao;

	@Test
	public void testFindProduct() throws Exception {
		ProductQuery productQuery = new ProductQuery();
		//分页
		//设置当前页
		productQuery.setPageNo(1);
		//设置每页查询多少条
		productQuery.setPageSize(20);
		//根据id倒序排列
		productQuery.setOrderByClause("id desc");
		//设置需要查询出的列
		productQuery.setFields("id,name");
		
		//创建where条件对象
		Criteria createCriteria = productQuery.createCriteria();
		//根据名称模糊查询
		createCriteria.andNameLike("%2016%");
		//查询并返回结果
		List<Product> list = productDao.selectByExample(productQuery);
		System.out.println("===================="+list);
	}
}
