package cn.itcast.core.common;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 获取令牌
 * @author ZJ
 *
 */
public class RequestUtils {

	public static String getToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1. 从request域中获取cookie
		Cookie[] cookies = request.getCookies();
		//2. 判断cookie中是否已经存在令牌, 如果存在则返回直接使用
		if(cookies != null && cookies.length > 0){
			for(Cookie cookie : cookies){
				//判断cookie中是否有令牌
				if(Constants.COOKIE_TOKEN.equals(cookie.getName())){
					return cookie.getValue();
				}
			}
		}
		//3. 如果不存在令牌, 则使用一个随机字符串作为一个新令牌
		String token = UUID.randomUUID().toString().replaceAll("-", "");
		//4. 将新令牌存入cookie中
		Cookie cookie = new Cookie(Constants.COOKIE_TOKEN, token);
		//设置cookie中令牌的生存时间, -1为关闭浏览器后消失
		cookie.setMaxAge(-1);
		//设置可以访问这个cookie的路径, 这里是所有项目都可以访问这个cookie
		cookie.setPath("/");
		//将cookie写回到浏览器
		response.addCookie(cookie);
		//5. 返回新生成的令牌
		return token;
	}
}
