package cn.itcast.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.core.common.JsonUtils;
import cn.itcast.core.dao.ad.AdDao;
import cn.itcast.core.dao.ad.PositionDao;
import cn.itcast.core.pojo.ad.Ad;
import cn.itcast.core.pojo.ad.AdQuery;
import cn.itcast.core.pojo.ad.AdQuery.Criteria;
import redis.clients.jedis.Jedis;

@Service("adServiceImpl")
@Transactional
public class AdServiceImpl implements AdService {
	
	@Autowired
	private AdDao adDao;
	
	@Autowired
	private PositionDao positonDao;
	
	@Autowired
	private Jedis jedis;

	@Override
	public List<Ad> findAdByPositionId(Long positionId) {
		AdQuery adQuery = new AdQuery();
		Criteria createCriteria = adQuery.createCriteria();
		createCriteria.andPositionIdEqualTo(positionId);
		List<Ad> adList = adDao.selectByExample(adQuery);
		if(adList != null){
			for(Ad ad : adList){
				ad.setPosition(positonDao.selectByPrimaryKey(ad.getPositionId()));
			}
		}
		return adList;
	}

	@Override
	public void insertAd(Ad ad) {
		adDao.insertSelective(ad);
		
	}

	@Override
	public String findAdListFromRedis() throws Exception{
		//从redis中获取json格式的大广告数据
		String str = jedis.get("ads");
		if(str != null && !"".equals(str)){
			return str;
		} else {
			//从数据库中获取大广告数据
			List<Ad> adList = findAdByPositionId(89L);
			//将大广告数据转换成json格式字符串
			str = JsonUtils.objectToJsonStr(adList);
			//将转换后的数据放入redis中存储, 下回直接从redis中就拿得到就不走数据库
			jedis.set("ads", str);
			return str;
		}
	}

}
