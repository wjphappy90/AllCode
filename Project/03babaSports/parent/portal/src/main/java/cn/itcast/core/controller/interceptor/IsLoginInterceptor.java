package cn.itcast.core.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.itcast.core.common.RequestUtils;
import cn.itcast.core.service.LoginService;

/**
 * 拦截去结算的请求, 判断用户是否登录
 * @author ZJ
 *
 */
public class IsLoginInterceptor implements HandlerInterceptor {
	
	@Autowired
	private LoginService loginService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//获取令牌
		String token = RequestUtils.getToken(request, response);
		//根据令牌获取redis中是否有用户登录信息
		String userName = loginService.getAttributeFromRedis(token);
		//判断用户是否登录
		if(userName != null && !"".equals(userName)){
			//用户已经登录, 放行
			return true;
		}
		//没有登录跳转到登录系统进行登录, 登录后跳转回购物车
		response.sendRedirect("http://localhost:8083/shopping/login.aspx?returnUrl=http://localhost:8082/shopping/toCart");
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
