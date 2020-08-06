package cn.itcast.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.core.dao.product.ColorDao;
import cn.itcast.core.dao.product.SkuDao;
import cn.itcast.core.pojo.product.Color;
import cn.itcast.core.pojo.product.Sku;
import cn.itcast.core.pojo.product.SkuQuery;
import cn.itcast.core.pojo.product.SkuQuery.Criteria;

@Service("skuServiceImpl")
@Transactional
public class SkuServiceImpl implements SkuService {
	
	@Autowired
	private SkuDao skuDao;
	
	@Autowired
	private ColorDao colorDao;

	@Override
	public List<Sku> findSkuListByProductId(Long productId) {
		SkuQuery skuQuery = new SkuQuery();
		//创建where条件对象
		Criteria createCriteria = skuQuery.createCriteria();
		createCriteria.andProductIdEqualTo(productId);
		List<Sku> skuList = skuDao.selectByExample(skuQuery);
		if(skuList != null){
			for(Sku sku : skuList){
				//根据颜色id到数据库中获取颜色对象数据
				Color color = colorDao.selectByPrimaryKey(sku.getColorId());
				sku.setColor(color);
			}
		}
		return skuList;
	}

	@Override
	public void updateSku(Sku sku) {
		skuDao.updateByPrimaryKeySelective(sku);
		
	}

}
