package cn.itcast.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.core.common.Constants;
import cn.itcast.core.dao.user.BuyerDao;
import cn.itcast.core.pojo.user.Buyer;
import cn.itcast.core.pojo.user.BuyerQuery;
import cn.itcast.core.pojo.user.BuyerQuery.Criteria;
import redis.clients.jedis.Jedis;

@Service("loginServiceImpl")
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	private BuyerDao buyerDao;
	
	@Autowired
	private Jedis jedis;

	@Override
	public Buyer findBuyerByUserName(String userName) {
		BuyerQuery buyerQuery = new BuyerQuery();
		Criteria createCriteria = buyerQuery.createCriteria();
		createCriteria.andUsernameEqualTo(userName);
		List<Buyer> buyerList = buyerDao.selectByExample(buyerQuery);
		if(buyerList != null && buyerList.size()  == 1){
			return buyerList.get(0);
		}
		return null;
	}

	@Override
	public void setAttributeForRedis(String token, String userName) {
		jedis.set(Constants.REDIS_USERNAME + token, userName);
		//设置用户登录信息在redis中的生存时间为30分钟
		jedis.expire(Constants.REDIS_USERNAME + token, 60 * 30);
	}

	@Override
	public String getAttributeFromRedis(String token) {
		String userName = jedis.get(Constants.REDIS_USERNAME + token);
		//如果redis中有登录信息, 则调用此方法的时候重新刷新登录超时时间
		if(userName != null && !"".equals(userName)){
			jedis.expire(Constants.REDIS_USERNAME + token, 60 * 30);
		}
		return userName;
	}

	
}
