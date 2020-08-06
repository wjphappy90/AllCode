package cn.itcast.core.service;

import java.util.List;

import cn.itcast.core.pojo.ad.Ad;

public interface AdService {

	public List<Ad> findAdByPositionId(Long positionId);
	
	public void insertAd(Ad ad);
	
	public String findAdListFromRedis() throws Exception;
}
