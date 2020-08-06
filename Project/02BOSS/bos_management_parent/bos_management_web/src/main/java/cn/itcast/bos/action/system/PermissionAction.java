package cn.itcast.bos.action.system;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.action.base.BaseAction;
import cn.itcast.bos.domain.system.Permission;
import cn.itcast.bos.service.system.PermissionService;
@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
public class PermissionAction extends BaseAction<Permission> {

	@Autowired
	private PermissionService service;
	
	@Action("permissionAction_findAll")
	public void findAll(){
		List<Permission> list = service.findAll();
		javaToJson(list, new String[]{"roles"});
	}
	@Action("permissionAction_save")
	public void save(){
		try {
			service.save(model);
			javaToJson(ajaxReturn(true, ""), null);
		} catch (Exception e) {
			javaToJson(ajaxReturn(false, "操作失败"), null);
			e.printStackTrace();
		}
	}
	
}
