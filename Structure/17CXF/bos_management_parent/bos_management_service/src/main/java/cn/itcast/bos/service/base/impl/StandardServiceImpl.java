package cn.itcast.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.StandardDao;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.base.StandardService;
@Service
@Transactional
public class StandardServiceImpl implements StandardService{
	@Autowired
	private StandardDao dao;

	@Override
	public void updateOperator(String string, int i) {
		dao.updateOperator(string, i);
		
	}

	@Override
	public void save(Standard model) {
		dao.save(model);
	}

	@Override
	public Page<Standard> findByPage(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	public List<Standard> findAll() {
		return dao.findAll();
	}

}
