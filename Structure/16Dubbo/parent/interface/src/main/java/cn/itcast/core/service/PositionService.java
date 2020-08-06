package cn.itcast.core.service;

import java.util.List;

import cn.itcast.core.pojo.ad.Position;

public interface PositionService {

	public List<Position> findPositionListByParentId(Long parentId);
	
	public Position findPositionById(Long id);
}
