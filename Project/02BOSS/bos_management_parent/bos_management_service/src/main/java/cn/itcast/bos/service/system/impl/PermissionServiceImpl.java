package cn.itcast.bos.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.system.PermissionDao;
import cn.itcast.bos.domain.system.Permission;
import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.system.PermissionService;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
	
	@Autowired
	private PermissionDao dao;
	
	public List<Permission> findAll(){
		return dao.findAll();
	}
	
	public void save(Permission model){
		dao.save(model);
	}

	@Override
	public List<Permission> findByUser(User user) {
		if(user.getUsername().equals("admin")){
			return dao.findAll();
		}else{
			return dao.findByUser(user.getId());
		}
	}

}
