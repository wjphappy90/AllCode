package cn.itcast.core.controller;

import java.security.MessageDigest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.itcast.core.common.RequestUtils;
import cn.itcast.core.pojo.user.Buyer;
import cn.itcast.core.service.LoginService;

/**
 * 处理登录业务
 * @author ZJ
 *
 */
@Controller
public class LoginController {
	
	@Autowired
	private LoginService loginService;

	/**
	 * 跳转到登录页面
	 * returnUrl 登录成功后跳转的路径
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shopping/login", method = RequestMethod.GET)
	public String toLogin(String returnUrl, Model model) throws Exception {
		model.addAttribute("returnUrl", returnUrl);
		return "login";
	}
	
	/**
	 * 验证用户名密码, 登录
	 * @param username 用户名
	 * @param password 密码
	 * @param returnUrl 登录成功后跳转的路径
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shopping/login", method = RequestMethod.POST)
	public String login(String username, String password, String returnUrl, 
			HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		//判断用户名是否为空
		if(username != null){
			//验证密码不能为空
			if(password != null){
				//根据用户名获取用户对象
				Buyer buyer = loginService.findBuyerByUserName(username);
				//判断用户名是否正确
				if(buyer != null){
					//判断密码是否正确
					if(encodePassword(password).equals(buyer.getPassword())){
						//获取令牌
						String token = RequestUtils.getToken(request, response);
						//将令牌作为key, 用户名作为value存入redis中
						loginService.setAttributeForRedis(token, username);
						//跳转到指定的页面
						return "redirect:" + returnUrl;
					} else {
						model.addAttribute("error", "密码或错误!");
					}
				} else {
					model.addAttribute("error", "用户名错误!");
				}
			} else {
				model.addAttribute("error", "密码不能为空!");
			}
		} else {
			model.addAttribute("error", "用户名不能为空!");
		}
		model.addAttribute("returnUrl", returnUrl);
		return "login";
	}
	
	/**
	 * 封装加密算法
	 * 这里采用MD5摘要算法+16进制加密
	 * 加盐:原理就是鱼目混珠
	 * @param password
	 * @return
	 * @throws Exception
	 */
	private String encodePassword(String password) throws Exception {
		//通过MD5摘要算法加密
		MessageDigest instance = MessageDigest.getInstance("MD5");
		byte[] digest = instance.digest(password.getBytes());
		
		//再次使用16进制加密
		char[] encodeHex = Hex.encodeHex(digest);
		return new String(encodeHex);
	}
	
	/**
	 * 判断用户是否登录
	 * @throws Exception
	 */
	@RequestMapping("/shopping/isLogin")
	@ResponseBody
	public MappingJacksonValue isLogin(String callback, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取令牌
		String token = RequestUtils.getToken(request, response);
		//获取用户登录信息
		String userName = loginService.getAttributeFromRedis(token);
		
		//设置是否登录标记, 默认0为未登录, 1为登录
		Integer flag = 0;
		if(userName != null && !"".equals(userName)){
			flag = 1;
		}
		
		//因为通过portal项目的页面访问这个项目的controller方法, 浏览器厂商为了安全考虑不允许,
		//可以使用jquery+springMvc的MappingJacksonValue跨过浏览器跨域访问问题
		MappingJacksonValue mjv = new MappingJacksonValue(flag);
		mjv.setJsonpFunction(callback);
		return mjv;
	}
}
