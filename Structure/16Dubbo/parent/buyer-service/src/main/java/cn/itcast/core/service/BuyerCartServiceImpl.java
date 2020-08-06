package cn.itcast.core.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.core.common.Constants;
import cn.itcast.core.dao.order.DetailDao;
import cn.itcast.core.dao.order.OrderDao;
import cn.itcast.core.dao.product.ColorDao;
import cn.itcast.core.dao.product.ProductDao;
import cn.itcast.core.dao.product.SkuDao;
import cn.itcast.core.pojo.BuyerCart;
import cn.itcast.core.pojo.BuyerItems;
import cn.itcast.core.pojo.order.Detail;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.product.Color;
import cn.itcast.core.pojo.product.Product;
import cn.itcast.core.pojo.product.Sku;
import redis.clients.jedis.Jedis;

@Service("buyerCartServiceImpl")
@Transactional
public class BuyerCartServiceImpl implements BuyerCartService {
	
	@Autowired
	private SkuDao skuDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private ColorDao colorDao;
	
	@Autowired
	private Jedis jedis;
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private DetailDao detailDao;

	@Override
	public Sku findSkuById(Long skuId) {
		Sku sku = skuDao.selectByPrimaryKey(skuId);
		if(sku != null){
			//获取颜色对象
			Color color = colorDao.selectByPrimaryKey(sku.getColorId());
			sku.setColor(color);
			
			//获取商品对象
			Product product = productDao.selectByPrimaryKey(sku.getProductId());
			sku.setProduct(product);
			
		}
		return sku;
	}

	//保存数据格式例如:
	// buery_cart_zhangsan:
	//						001    30
	//					    002    1
	//					    003    10
	@Override
	public void setBuyerCartToRedis(BuyerCart buyerCart, String userName) {
		if(buyerCart != null){
			List<BuyerItems> items = buyerCart.getItems();
			if(items.size() > 0){
				for(BuyerItems item : items){
					//库存id
					Long skuId = item.getSku().getId();
					//购买数量
					Integer amount = item.getAmount();
					
					//判断redis中是否有这个商品数据
					if(jedis.hexists(Constants.BUYER_CART+"_"+userName, String.valueOf(skuId))){
						//有, 数量相加
						jedis.hincrBy(Constants.BUYER_CART+"_"+userName, String.valueOf(skuId), amount);
					} else {
						//没有, 将商品加入到redis中
						jedis.hset(Constants.BUYER_CART+"_"+userName, String.valueOf(skuId), String.valueOf(amount));
					}
				}
			}
		}
	}

	@Override
	public BuyerCart getBuyerCartFromRedis(String userName) {
		BuyerCart cart = new BuyerCart();
		Map<String, String> hgetAll = jedis.hgetAll(Constants.BUYER_CART+"_"+userName);
		if(hgetAll != null){
			Set<Entry<String, String>> entrySet = hgetAll.entrySet();
			for(Entry<String, String> entry : entrySet){
				//创建购物项对象
				BuyerItems item  = new BuyerItems();
				//创建库存对象
				Sku sku = new Sku();
				sku.setId(Long.parseLong(entry.getKey()));
				//将库存对象放入购物项中
				item.setSku(sku);
				//购买数量
				item.setAmount(Integer.parseInt(entry.getValue()));
				//将购物项放入购物车中
				cart.addItems(item);
			}
		}
		return cart;
	}

	@Override
	public void insertOrder(Order order, String userName) throws Exception {
		/**
		 * 保存订单数据
		 */
		//从redis中根据用户名获取用户id
		String userId = jedis.get(userName);
		//购买人的id
		order.setBuyerId(Long.parseLong(userId));
		//由redis统一生成订单号
		Long oid = jedis.incr("oid");
		order.setId(oid);
		//创建时间
		order.setCreateDate(new Date());
		//订单状态 0:提交订单
		order.setOrderState(0);
		//支付方式 0:到付 1:在线 2:邮局 3:公司转帐
		if(order.getPaymentWay() == 0){
			//支付状态 :0到付1待付款,2已付款,3待退款,4退款成功,5退款失败
			order.setIsPaiy(0);
		}else {
			//支付状态 :0到付1待付款,2已付款,3待退款,4退款成功,5退款失败
			order.setIsPaiy(1);
		}
		//获取购物车
		BuyerCart buyerCart = getBuyerCartFromRedis(userName);
		for(BuyerItems item : buyerCart.getItems()){
			item.setSku(findSkuById(item.getSku().getId()));
		}
		//运费
		order.setDeliverFee(buyerCart.getFee());
		//订单价格
		order.setOrderPrice(buyerCart.getProductPrice());
		//总价格
		order.setTotalPrice(buyerCart.getTotalPrice());
		orderDao.insertSelective(order);
		
		/**
		 * 保存订单详情数据
		 */
		if(buyerCart.getItems().size() > 0){
			for(BuyerItems item : buyerCart.getItems()){
				//创建订单详情对象
				Detail detail = new Detail();
				//购买数量
				detail.setAmount(item.getAmount());
				//颜色
				detail.setColor(item.getSku().getColor().getName());
				//订单id
				detail.setOrderId(order.getId());
				//商品售价
				detail.setPrice(item.getSku().getPrice());
				//商品id
				detail.setProductId(item.getSku().getProductId());
				//商品名称
				detail.setProductName(item.getSku().getProduct().getName());
				//尺码
				detail.setSize(item.getSku().getSize());
				detailDao.insertSelective(detail);
			}
		}
		
		/**
		 * 订单结算完清空购物车
		 */
		jedis.del(Constants.BUYER_CART+"_"+userName);
	}

}
