package cn.itcast.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.core.dao.ad.PositionDao;
import cn.itcast.core.pojo.ad.Position;
import cn.itcast.core.pojo.ad.PositionQuery;
import cn.itcast.core.pojo.ad.PositionQuery.Criteria;

@Service("positionServiceImpl")
public class PositionServiceImpl implements PositionService {
	
	@Autowired
	private PositionDao positionDao;

	@Override
	public List<Position> findPositionListByParentId(Long parentId) {
		PositionQuery positionQuery = new PositionQuery();
		Criteria createCriteria = positionQuery.createCriteria();
		createCriteria.andParentIdEqualTo(parentId);
		List<Position> list = positionDao.selectByExample(positionQuery);
		return list;
	}

	@Override
	public Position findPositionById(Long id) {
		Position position = positionDao.selectByPrimaryKey(id);
		return position;
	}

}
