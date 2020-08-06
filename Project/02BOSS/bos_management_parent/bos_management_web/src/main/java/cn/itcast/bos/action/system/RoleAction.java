package cn.itcast.bos.action.system;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.action.base.BaseAction;
import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.service.system.RoleService;
@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
public class RoleAction extends BaseAction<Role> {

	@Autowired
	private RoleService service;
	
	@Action("roleAction_findAll")
	public void findAll(){
		List<Role> list = service.findAll();
		javaToJson(list, new String[]{"users","permissions","menus"});
	}
	
	private String permissionIds;
	private String menuIds;
	public void setPermissionIds(String permissionIds) {
		this.permissionIds = permissionIds;
	}

	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}

	@Action("roleAction_save")
	public void save(){
		try {
			service.save(model,permissionIds,menuIds);
			javaToJson(ajaxReturn(true, ""), null);
		} catch (Exception e) {
			javaToJson(ajaxReturn(false, "操作失败"), null);
			e.printStackTrace();
		}
	}
	
	
}
