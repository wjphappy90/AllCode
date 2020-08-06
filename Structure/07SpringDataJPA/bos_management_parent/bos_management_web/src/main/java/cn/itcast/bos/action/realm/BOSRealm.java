package cn.itcast.bos.action.realm;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.bos.domain.system.Permission;
import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.system.PermissionService;
import cn.itcast.bos.service.system.RoleService;
import cn.itcast.bos.service.system.UserService;

public class BOSRealm extends AuthorizingRealm {
	
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;
	
	/**
	 * 认证
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) arg0;
		String username = token.getUsername();
		String password = new String(token.getPassword());
		User user = userService.findByUsernameAndPassword(username, password);
		if(user==null){
			return null;
		}
//		principal 主角, credentials 密码, realmName 类名
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
		return info;
	}
	
	/**
	 * 授权
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		 System.out.println("执行了授权方法");
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		User user = (User) arg0.getPrimaryPrincipal();
		
//		根据当前登录人获取角色
		List<Role> roles = roleService.findByUser(user);
		for (Role role : roles) {
			info.addRole(role.getKeyword());
		}
//		根据当前登录人获取权限
		List<Permission> permissions = permissionService.findByUser(user);
		for (Permission permission : permissions) {
			info.addStringPermission(permission.getKeyword());
		}
		return info;
	}


}
