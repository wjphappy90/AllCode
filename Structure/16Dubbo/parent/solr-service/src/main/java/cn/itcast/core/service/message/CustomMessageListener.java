package cn.itcast.core.service.message;

import java.util.List;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.core.dao.product.ProductDao;
import cn.itcast.core.dao.product.SkuDao;
import cn.itcast.core.pojo.product.Product;
import cn.itcast.core.pojo.product.Sku;
import cn.itcast.core.pojo.product.SkuQuery;

/**
 * 自定义消息监听器
 * 监听消息服务器发送过来的商品id, 根据商品id去数据库中查询数据, 并放到solr服务器中
 * @author ZJ
 *
 */
public class CustomMessageListener implements MessageListener {
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private SkuDao skuDao;
	
	@Autowired
	private SolrServer solrServer;

	@Override
	public void onMessage(Message message) {
		//因为message对象是jdk底层的, 所以处理起来不方便, 为了方便的拿到消息, 强转成ActiveMQTextMessage对象, 方便处理
		ActiveMQTextMessage atm = (ActiveMQTextMessage)message;
		try {
			//获取消息, 商品id
			String productId = atm.getText();
			
			/**
			 * 2. 根据商品id获取商品数据, 放入solr索引库中
			 */
			//去数据库中获取商品数据
			Product product = productDao.selectByPrimaryKey(Long.parseLong(productId));
			//创建文档对象
			SolrInputDocument doc = new SolrInputDocument();
			//商品id
			doc.addField("id", product.getId());
			//商品名称
			doc.addField("name_ik", product.getName());
			//品牌id
			doc.addField("brandId", product.getBrandId());
			//商品图片
			doc.addField("url", product.getImgUrl());
			//商品价格
			SkuQuery skuQuery = new SkuQuery();
			//根据价格升序排列
			skuQuery.setOrderByClause("price asc");
			//查询一页数据
			skuQuery.setPageNo(1);
			//每页显示一条
			skuQuery.setPageSize(1);
			//创建where条件对象
			cn.itcast.core.pojo.product.SkuQuery.Criteria createCriteria = skuQuery.createCriteria();
			//根据商品id查询
			createCriteria.andProductIdEqualTo(Long.parseLong(productId));
			List<Sku> skuList = skuDao.selectByExample(skuQuery);
			if(skuList != null && skuList.size() > 0){
				doc.addField("price", skuList.get(0).getPrice());
			} else {
				doc.addField("price", 0);
			}
			//将文档加入到solr服务中
			solrServer.add(doc);
			//提交
			solrServer.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
