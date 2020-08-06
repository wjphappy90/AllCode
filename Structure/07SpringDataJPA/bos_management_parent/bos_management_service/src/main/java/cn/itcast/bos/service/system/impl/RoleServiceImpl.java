package cn.itcast.bos.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.system.MenuDao;
import cn.itcast.bos.dao.system.PermissionDao;
import cn.itcast.bos.dao.system.RoleDao;
import cn.itcast.bos.domain.system.Menu;
import cn.itcast.bos.domain.system.Permission;
import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.system.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao dao;
	@Autowired
	private MenuDao mdao;
	@Autowired
	private PermissionDao pdao;

	@Override
	public List<Role> findAll() {
		return dao.findAll();
	}

	@Override
	public void save(Role model, String permissionIds, String menuIds) {
//		1、role表中保存数据
		model = dao.save(model);
//		2、role_menu关联表
		if(!menuIds.equals("")){
			String[] mIds = menuIds.split(",");
			for (String mId : mIds) {
				Menu menu = mdao.findOne(Integer.parseInt(mId));
				model.getMenus().add(menu);
			}
		}
//		3、role_permission关联表
		if(!permissionIds.equals("")){
			String[] pIds = permissionIds.split(",");
			for (String pId : pIds) {
				 Permission permission = pdao.findOne(Integer.parseInt(pId));
				 model.getPermissions().add(permission);
			}
		}
		
	}

	@Override
	public List<Role> findByUser(User user) {
//		如果user是admin查询所有的角色
	   if(user.getUsername().equals("admin")){
		   return dao.findAll();
	   }else{
		   return dao.findByUser(user.getId());
	   }

		
		
	}
	
	
}
