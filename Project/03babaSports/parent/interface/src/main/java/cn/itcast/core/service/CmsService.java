package cn.itcast.core.service;

import java.util.List;

import cn.itcast.core.pojo.product.Product;
import cn.itcast.core.pojo.product.Sku;

public interface CmsService {

	public Product findProductById(Long id);
	
	public List<Sku> findSkuListByProductId(Long id);
}
