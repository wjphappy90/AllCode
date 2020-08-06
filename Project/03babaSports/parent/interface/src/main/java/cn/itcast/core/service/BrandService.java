package cn.itcast.core.service;

import java.util.List;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.pojo.product.Brand;

public interface BrandService {

	public List<Brand> findBrandList(String name, Integer isDisplay);
	
	public Pagination findBrandPagination(String name, Integer isDisplay, Integer pageNo);
	
	public Brand findBrandById(Long id);
	
	public void updateBrand(Brand brand);
	
	public void delBrandAll(Long[] ids);
	
	public List<Brand> findBrandFromRedis();
}
