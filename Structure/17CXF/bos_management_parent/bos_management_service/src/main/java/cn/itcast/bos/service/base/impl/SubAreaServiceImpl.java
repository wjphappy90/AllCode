package cn.itcast.bos.service.base.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.SubAreaDao;
import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.service.base.SubAreaService;

@Service
@Transactional
public class SubAreaServiceImpl implements SubAreaService {
	@Autowired
	private SubAreaDao dao;

	@Override
	public void save(SubArea model) {
		if(model.getId()==null){ //如果id为空表示是一个保存操作，赋ID
			model.setId(UUID.randomUUID().toString());
		}
		dao.save(model);
	}

	@Override
	public Page<SubArea> findByPage(Pageable pageable) {
		return dao.findAll(pageable);
	}

}
