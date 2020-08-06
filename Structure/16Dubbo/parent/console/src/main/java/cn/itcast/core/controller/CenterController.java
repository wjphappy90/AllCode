package cn.itcast.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.core.service.TbTestService;

/**
 * 本类作为入口, 主要处理页面跳转
 * @author ZJ
 *
 */
@Controller
@RequestMapping("/console")
public class CenterController {
	

	/**
	 * 跳转到首页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/index")
	public String index() throws Exception {
		return "index";
	}
	
	/**
	 * 跳转到头部页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/top")
	public String top() throws Exception {
		return "top";
	}
	
	/**
	 * 跳转到主体页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/main")
	public String main() throws Exception {
		return "main";
	}
	
	/**
	 * 跳转到左侧页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/left")
	public String left() throws Exception {
		return "left";
	}
	
	/**
	 * 跳转到右侧页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/right")
	public String right() throws Exception {
		return "right";
	}
	
	/**
	 * 跳转到商品主页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/frame/productMain")
	public String productMain() throws Exception {
		return "frame/product_main";
	}
	
	/**
	 * 跳转到商品的左侧页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/frame/productLeft")
	public String productLeft() throws Exception {
		return "frame/product_left";
	}
	
	/**
	 * 跳转到大广告主页面
	 */
	@RequestMapping("/frame/adMain")
	public String adMain() throws Exception {
		return "frame/ad_main";
	}
	
	/**
	 * 跳转到大广告左侧页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/frame/adLeft")
	public String adLeft() throws Exception {
		return "frame/ad_left";
	}
}
