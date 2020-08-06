package cn.itcast.core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.common.Constants;
import cn.itcast.core.dao.product.BrandDao;
import cn.itcast.core.pojo.product.Brand;
import cn.itcast.core.pojo.product.BrandQuery;
import redis.clients.jedis.Jedis;

@Service("brandServiceImpl")
@Transactional
public class BrandServiceImpl implements BrandService {
	
	@Autowired
	private BrandDao brandDao;
	
	@Autowired
	private Jedis jedis;

	@Override
	public List<Brand> findBrandList(String name, Integer isDisplay) {
		BrandQuery brandQuery = new BrandQuery();
		if(name != null){
			brandQuery.setName(name);
		}
		if(isDisplay != null){
			brandQuery.setIsDisplay(isDisplay);
		}
		List<Brand> brandList = brandDao.findBrandList(brandQuery);
		return brandList;
	}

	@Override
	public Pagination findBrandPagination(String name, Integer isDisplay, Integer pageNo) {
		StringBuilder params = new StringBuilder();
		BrandQuery brandQuery = new BrandQuery();
		//当前页
		brandQuery.setPageNo(Pagination.cpn(pageNo));
		//设置每页显示5条
		brandQuery.setPageSize(5);
		
		if(name != null){
			brandQuery.setName(name);
			params.append("&name=").append(name);
		}
		if(isDisplay != null){
			brandQuery.setIsDisplay(isDisplay);
			params.append("&isDisplay=").append(isDisplay);
		}
		
		//查询数据
		List<Brand> brandList = brandDao.findBrandPage(brandQuery);
		Integer count = brandDao.findBrandPageCount(brandQuery);
		/**
		 * 创建分页对象
		 * 第一个参数:当前页数, 第二个参数:每页显示多少条, 第三个参数:查询出的数据的总数, 第四个参数: 结果集
		 */
		Pagination pagination = new Pagination(
				brandQuery.getPageNo(), 
				brandQuery.getPageSize(), 
				count, 
				brandList);
		//设置翻页跳转路径
		String url = "/brand/list.action";
		//设置翻页的路径和传的参数
		pagination.pageView(url, params.toString());
		return pagination;
	}

	@Override
	public Brand findBrandById(Long id) {
		Brand brand = brandDao.findBrandById(id);
		return brand;
	}

	@Override
	public void updateBrand(Brand brand) {
		//1. 修改数据库中品牌数据
		brandDao.updateBrand(brand);
		
		//redis中存入品牌map格式数据
		// brands  1  	易启莲
		//         2 	阿里达斯
		//2. 修改redis中品牌数据
		jedis.hset(Constants.REDIS_BRANDS, String.valueOf(brand.getId()), brand.getName());
		
		
	}

	@Override
	public void delBrandAll(Long[] ids) {
		brandDao.delBrandByIds(ids);
	}

	@Override
	public List<Brand> findBrandFromRedis() {
		List<Brand> brands = new ArrayList<Brand>();
		Map<String, String> hgetAll = jedis.hgetAll(Constants.REDIS_BRANDS);
		if(hgetAll != null && hgetAll.size() > 0){
			Set<Entry<String, String>> entrySet = hgetAll.entrySet();
			//遍历set集合
			for(Entry<String, String> entry : entrySet){
				//自己封装品牌对象, 并放入品牌集合中
				Brand brand = new Brand();
				brand.setId(Long.parseLong(entry.getKey()));
				brand.setName(entry.getValue());
				brands.add(brand);
			}
		}
		return brands;
	}

}
