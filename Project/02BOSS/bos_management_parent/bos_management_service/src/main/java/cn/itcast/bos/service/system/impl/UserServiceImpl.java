package cn.itcast.bos.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.system.RoleDao;
import cn.itcast.bos.dao.system.UserDao;
import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.system.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao dao;
	@Autowired
	private RoleDao rdao;

	@Override
	public User findByUsernameAndPassword(String username, String password) {
		return dao.findByUsernameAndPassword(username,password);
	}

	@Override
	public List<User> findAll() {
		return dao.findAll();
	}

	@Override
	public void save(User model, String roleIds) {
		model = dao.save(model);
		if(!roleIds.equals("")){
			String[] rIds = roleIds.split(",");
			for (String rid : rIds) {
				Role role = rdao.findOne(Integer.parseInt(rid));
				model.getRoles().add(role);
			}
		}
	}

}
