package cn.itcast.core.service;

import java.util.Date;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.solr.client.solrj.SolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.dao.product.ProductDao;
import cn.itcast.core.dao.product.SkuDao;
import cn.itcast.core.pojo.product.Product;
import cn.itcast.core.pojo.product.ProductQuery;
import cn.itcast.core.pojo.product.ProductQuery.Criteria;
import cn.itcast.core.pojo.product.Sku;
import redis.clients.jedis.Jedis;

@Service("productServiceImpl")
@Transactional
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private SkuDao skuDao;
	
	@Autowired
	private Jedis jedis;
	
	@Autowired
	private SolrServer solrServer;
	
	@Autowired
	private JmsTemplate jmsTemplate;

	@Override
	public Pagination findProductPage(String name, Long brandId, Boolean isShow, Integer pageNo) {
		StringBuilder params = new StringBuilder();
		ProductQuery productQuery = new ProductQuery();
		//设置分页
		productQuery.setPageNo(Pagination.cpn(pageNo));
		productQuery.setPageSize(10);
		//创建where条件对象
		Criteria createCriteria = productQuery.createCriteria();
		if(name != null){
			createCriteria.andNameLike("%"+name+"%");
			params.append("&name=").append(name);
		}
		if(brandId != null){
			createCriteria.andBrandIdEqualTo(brandId);
			params.append("&brandId=").append(brandId);
		}
		if(isShow != null){
			createCriteria.andIsShowEqualTo(isShow);
			params.append("&isShow=").append(isShow);
		} else {
			createCriteria.andIsShowEqualTo(false);
			params.append("&isShow=0");
		}
		
		//去数据库查询
		List<Product> list = productDao.selectByExample(productQuery);
		int count = productDao.countByExample(productQuery);
		
		//构建分页对象
		Pagination pagination = new Pagination(
				productQuery.getPageNo(), 
				productQuery.getPageSize(), 
				count,
				list);
		//封装翻页跳转的路径和翻页所需要带着的参数
		String url = "/product/list.action";
		pagination.pageView(url, params.toString());
		return pagination;
	}

	@Override
	public void insertProduct(Product product) {
		/**
		 * 保存商品数据到商品表
		 */
		//由redis统一生成唯一的商品编号
		Long pno = jedis.incr("pno");
		product.setId(pno);
		//创建时间
		product.setCreateTime(new Date());
		//设置删除标记为未删除状态
		product.setIsDel(true);
		//新添加的商品设置成下架状态
		product.setIsShow(false);
		productDao.insertSelective(product);
		
		/**
		 * 初始化库存数据到sku表
		 * 同一款商品有不同颜色, 不同尺码的物品, 价钱都不一样
		 */
		//遍历颜色
		for(String colorId : product.getColors().split(",")){
			//遍历尺码
			for(String size : product.getSizes().split(",")){
				Sku sku = new Sku();
				//颜色
				sku.setColorId(Long.parseLong(colorId));
				//创建时间
				sku.setCreateTime(new Date());
				//运费
				sku.setDeliveFee(10f);
				//市场价
				sku.setMarketPrice(0f);
				//售价
				sku.setPrice(0f);
				//商品id
				sku.setProductId(product.getId());
				//尺码
				sku.setSize(size);
				//库存
				sku.setStock(0);
				//购买限制
				sku.setUpperLimit(200);
				skuDao.insertSelective(sku);
			}
		}
	}

	@Override
	public void isShow(Long[] ids) throws Exception{
		
		if(ids != null){
			for(final Long id : ids){
				/**
				 * 1. 根据商品id更新商品的上架状态
				 */
				Product product = new Product();
				//设置id
				product.setId(id);
				//设置状态为上架状态
				product.setIsShow(true);
				productDao.updateByPrimaryKeySelective(product);
				
				/**
				 * 2. 将商品id作为消息发送给消息服务器
				 */
				jmsTemplate.send(new MessageCreator() {
					
					@Override
					public Message createMessage(Session session) throws JMSException {
						//创建一个文本消息对象, 将要发送的商品id放入这个对象中
						return session.createTextMessage(String.valueOf(id));
					}
				});
				
			}
		}
	}

}
