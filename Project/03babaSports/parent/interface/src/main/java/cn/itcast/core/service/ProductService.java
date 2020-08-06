package cn.itcast.core.service;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.pojo.product.Product;

public interface ProductService {

	public Pagination findProductPage(String name, Long brandId, Boolean isShow, Integer pageNo);
	
	public void insertProduct(Product product);
	
	public void isShow(Long[] ids) throws Exception;
}
