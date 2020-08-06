package cn.itcast.core.service.message;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.core.pojo.product.Color;
import cn.itcast.core.pojo.product.Product;
import cn.itcast.core.pojo.product.Sku;
import cn.itcast.core.service.CmsService;
import cn.itcast.core.service.StaticPageService;

/**
 * 自定义消息监听器, 监听来自于activeMq的消息
 * 根据消息商品id, 获取商品数据生成商品详情静态化页面
 * @author ZJ
 *
 */
public class CustomMessageListener implements MessageListener{
	
	//根据商品id获取需要的商品数据
	@Autowired
	private CmsService cmsService;
	
	//根据商品数据生成静态化商品详情页面
	@Autowired
	private StaticPageService staticPageService;

	@Override
	public void onMessage(Message message) {
		ActiveMQTextMessage atm = (ActiveMQTextMessage)message;
		try {
			//获取消息, 商品id
			String productId = atm.getText();
			
			/**
			 * 根据商品id获取商品数据
			 */
			//根据商品id获取商品对象
			Product product = cmsService.findProductById(Long.parseLong(productId));
			//根据商品id获取库存集合
			List<Sku> skuList = cmsService.findSkuListByProductId(Long.parseLong(productId));
			
			//过滤颜色重复数据
			Set<Color> colors = new HashSet<Color>();
			if(skuList != null){
				for(Sku sku : skuList){
					colors.add(sku.getColor());
				}
			}
			
			//封装生成静态页面需要的数据
			Map<String, Object> rootMap = new HashMap<String, Object>();
			rootMap.put("colors", colors);
			rootMap.put("skuList", skuList);
			rootMap.put("product", product);
			
			/**
			 * 根据商品数据生成商品详情静态化页面
			 */
			staticPageService.createStaticPage(rootMap, productId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


}
