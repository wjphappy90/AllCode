package cn.itcast.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.core.dao.TbTestDao;
import cn.itcast.core.pojo.TbTest;

@Service("tbTestServiceImpl")
@Transactional
public class TbTestServiceImpl implements TbTestService {
	
	@Autowired
	private TbTestDao tbDao;

	@Override
	public void insertTest(TbTest tb) {
		tbDao.insertTest(tb);
		//throw new RuntimeException();
	}

}
