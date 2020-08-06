package cn.itcast.core.service;

import java.util.List;

import cn.itcast.core.pojo.product.Sku;

public interface SkuService {

	public List<Sku> findSkuListByProductId(Long productId);
	
	public void updateSku(Sku sku);
}
