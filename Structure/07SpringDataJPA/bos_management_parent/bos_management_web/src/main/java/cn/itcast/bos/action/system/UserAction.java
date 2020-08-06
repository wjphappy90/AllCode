package cn.itcast.bos.action.system;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.action.base.BaseAction;
import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.system.UserService;
@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
public class UserAction extends BaseAction<User> {
	
	@Autowired
	private UserService service;
	
	@Action("userAction_login")
	public void login(){
//		1、获取令牌
		UsernamePasswordToken token = new UsernamePasswordToken(model.getUsername(),model.getPassword());
//		2、获取主题subject
		Subject subject = SecurityUtils.getSubject();
//		3、开始认证
		try {
			subject.login(token);
			javaToJson(ajaxReturn(true, ""), null);
		} catch (AuthenticationException e) {
			javaToJson(ajaxReturn(false, "用户名或密码不正确"), null);
			e.printStackTrace();
		}
//		User user = service.findByUsernameAndPassword(model.getUsername(),model.getPassword());
//		if(user==null){
//			javaToJson(ajaxReturn(false, "用户名或密码不正确"), null);
//		}else{
//			ServletActionContext.getRequest().getSession().setAttribute("user", user);
//			javaToJson(ajaxReturn(true, ""), null);
//		}
	}

	@Action("userAction_getName")
	public void getName(){
		User user = (User)  SecurityUtils.getSubject().getPrincipal();
//		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		if(user==null){
			javaToJson(ajaxReturn(false, ""), null);
		}else{
			javaToJson(ajaxReturn(true, user.getUsername()), null);
		}
	}
	
	@Action(value="userAction_logout",results={@Result(name="success",type="redirect",location="/login.html")})
	public String logout(){
		 SecurityUtils.getSubject().logout();
//		ServletActionContext.getRequest().getSession().removeAttribute("user");
		return "success";
	}
	@Action("userAction_findAll")
	public void findAll(){
		List<User> list = service.findAll();
		javaToJson(list, new String[]{"roles"});
		
	}
	
	private String roleIds;
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	@Action("userAction_save")
	public void save(){
		try {
			service.save(model,roleIds);
			javaToJson(ajaxReturn(true, ""), null);
		} catch (Exception e) {
			javaToJson(ajaxReturn(false,"操作失败"), null);
			e.printStackTrace();
		}
		
	}
	
}
