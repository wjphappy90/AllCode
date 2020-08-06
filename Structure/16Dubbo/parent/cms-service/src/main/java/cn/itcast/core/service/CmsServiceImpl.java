package cn.itcast.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.core.dao.product.ColorDao;
import cn.itcast.core.dao.product.ProductDao;
import cn.itcast.core.dao.product.SkuDao;
import cn.itcast.core.pojo.product.Product;
import cn.itcast.core.pojo.product.Sku;
import cn.itcast.core.pojo.product.SkuQuery;
import cn.itcast.core.pojo.product.SkuQuery.Criteria;

@Service("cmsServiceImpl")
@Transactional
public class CmsServiceImpl implements CmsService {
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private SkuDao skuDao;
	
	@Autowired
	private ColorDao colorDao;

	@Override
	public Product findProductById(Long id) {
		Product product = productDao.selectByPrimaryKey(id);
		return product;
	}

	@Override
	public List<Sku> findSkuListByProductId(Long id) {
		SkuQuery skuQuery = new SkuQuery();
		//创建where条件对象
		Criteria createCriteria = skuQuery.createCriteria();
		//根据商品id查询
		createCriteria.andProductIdEqualTo(id);
		List<Sku> skuList = skuDao.selectByExample(skuQuery);
		if(skuList != null){
			for(Sku sku : skuList){
				//根据颜色id获取颜色对象, 放到sku对象中
				sku.setColor(colorDao.selectByPrimaryKey(sku.getColorId()));
			}
		}
		return skuList;
	}

}
