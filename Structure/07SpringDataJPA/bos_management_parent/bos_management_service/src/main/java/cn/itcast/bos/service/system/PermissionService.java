package cn.itcast.bos.service.system;

import java.util.List;

import cn.itcast.bos.domain.system.Permission;
import cn.itcast.bos.domain.system.User;

public interface PermissionService {
	public List<Permission> findAll();
	public void save(Permission model);
	public List<Permission> findByUser(User user);
}
