package cn.itcast.core.service;

import cn.itcast.core.pojo.BuyerCart;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.product.Sku;

public interface BuyerCartService {

	public Sku findSkuById(Long skuId);
	
	public void setBuyerCartToRedis(BuyerCart buyerCart, String userName);
	
	public BuyerCart getBuyerCartFromRedis(String userName);
	
	public void insertOrder(Order order, String userName) throws Exception;
}
