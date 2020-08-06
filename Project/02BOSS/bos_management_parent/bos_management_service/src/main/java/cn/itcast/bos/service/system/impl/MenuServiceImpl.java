package cn.itcast.bos.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.system.MenuDao;
import cn.itcast.bos.domain.system.Menu;
import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.system.MenuService;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {
	@Autowired
	private MenuDao dao;

	@Override
	public List<Menu> findAll() {
		return dao.findAll();
	}

	@Override
	public List<Menu> findByParentMenuIsNull() {
		return dao.findByParentMenuIsNull();
	}

	@Override
	public void save(Menu model) {
		dao.save(model);
	}

	@Override
	public List<Menu> findByUser(User user) {
		if(user.getUsername().equals("admin")){
			return dao.findAll();
		}else{
			return dao.findByUser(user.getId());
		}
	}

}
