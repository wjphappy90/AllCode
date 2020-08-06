package cn.itcast.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.AreaDao;
import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.base.AreaService;

@Service
@Transactional
public class AreaServiceImpl implements AreaService {

	@Autowired
	private AreaDao dao;

	@Override
	public void save(List<Area> list) {
		for (Area area : list) {
			dao.save(area);
		}
	}

	@Override
	public Page<Area> findByPage(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	public List<Area> findAll() {
		return dao.findAll();
	}

	@Override
	public List<Area> findByCitycodeLikeOrShortcodeLike(String string, String string2) {
		return dao.findByCitycodeLikeOrShortcodeLike(string,string2);
	}
}
