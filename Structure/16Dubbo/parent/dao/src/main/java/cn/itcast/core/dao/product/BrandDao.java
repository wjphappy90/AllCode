package cn.itcast.core.dao.product;

import java.util.List;

import cn.itcast.core.pojo.product.Brand;
import cn.itcast.core.pojo.product.BrandQuery;

public interface BrandDao {

	public List<Brand> findBrandList(BrandQuery brandQuery);
	
	public List<Brand> findBrandPage(BrandQuery brandQuery);
	
	public Integer findBrandPageCount(BrandQuery brandQuery);
	
	public Brand findBrandById(Long id);
	
	public void updateBrand(Brand brand);
	
	public void delBrandByIds(Long[] ids);
}
