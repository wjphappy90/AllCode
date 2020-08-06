package cn.itcast.core.service;

import cn.itcast.core.pojo.user.Buyer;

public interface LoginService {

	public Buyer findBuyerByUserName(String userName);
	
	public void setAttributeForRedis(String token, String userName);
	
	public String getAttributeFromRedis(String token);
}
