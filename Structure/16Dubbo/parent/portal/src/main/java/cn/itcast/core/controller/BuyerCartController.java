package cn.itcast.core.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.itcast.core.common.Constants;
import cn.itcast.core.common.RequestUtils;
import cn.itcast.core.pojo.BuyerCart;
import cn.itcast.core.pojo.BuyerItems;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.product.Sku;
import cn.itcast.core.service.BuyerCartService;
import cn.itcast.core.service.LoginService;

/**
 * 
 * 购物车业务
 * @author ZJ
 *
 */
@Controller
public class BuyerCartController {
	
	@Autowired
	private BuyerCartService cartService;
	
	@Autowired
	private LoginService loginService;

	/**
	 * 有bug版本的购物车
	 * 问题: 
	 * 		每次进入这个controller方法, 都是新创建购物车对象, 将之前的购物车覆盖了.
	 * 		同款商品应该数量追加, 而不是覆盖
	 * 		追加不同款商品应该是在购物车中追加购物项, 而不是覆盖
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping("/shopping/buyerCart")
//	public String buyerCart(Long skuId, Integer amount, Model model) throws Exception {
//		BuyerCart buyerCart = new BuyerCart();
//		if(skuId != null && amount != null){
//			//获取库存对象
//			Sku sku = cartService.findSkuById(skuId);
//			//创建购物项对象
//			BuyerItems item = new BuyerItems();
//			item.setSku(sku);
//			//购买数量
//			item.setAmount(amount);
//			//将购物项添加到购物车中
//			buyerCart.addItems(item);
//		}
//		model.addAttribute("buyerCart", buyerCart);
//		return "cart";
//	}
	
	/**
	 * 将商品加入到购物车
	 * @param skuId     库存id
	 * @param amount	购买数量
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/shopping/buyerCart")
	public String buyerCart(Long skuId, Integer amount, 
			HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		BuyerCart buyerCart = null;
		ObjectMapper om = new ObjectMapper();
		//设置属性中的空值不参与转换
		om.setSerializationInclusion(Include.NON_NULL);
		
//		1、	从Request当中获取Cookies
		Cookie[] cookies = request.getCookies();
		if(cookies != null && cookies.length > 0){
//		2、	遍历Cookie 找购物车
			for(Cookie cookie : cookies){
				if(Constants.BUYER_CART.equals(cookie.getName())){
					//将cookie中存的购物车json格式字符串转换成购物车对象
					buyerCart = om.readValue(cookie.getValue(), BuyerCart.class);
				}
			}
		}
		
//		3、	没有 创建购物车 
		if(buyerCart == null){
			buyerCart = new BuyerCart();
		}
		
//		4、	追加当前款商品
		if(skuId != null && amount != null){
			//创建购物项对象
			BuyerItems item = new BuyerItems();
			//购买数量
			item.setAmount(amount);
			//创建库存对象
			Sku sku = new Sku();
			sku.setId(skuId);
			//将库存对象放入购物项中
			item.setSku(sku);
			//将购物项放入购物车中
			buyerCart.addItems(item);
		}
		
		//判断当前是否登录
		//获取令牌
		String token = RequestUtils.getToken(request, response);
		//根据令牌去redis中获取用户登录信息
		String userName = loginService.getAttributeFromRedis(token);
		if(userName != null && !"".equals(userName)){
//			已登陆
//			5、	遍历购物车中购物项追加到Redis中
			cartService.setBuyerCartToRedis(buyerCart, userName);
//			6、	清空Cookie
			Cookie cookie = new Cookie(Constants.BUYER_CART, null);
			//cookie立即销毁
			cookie.setMaxAge(0);
			//设置所有项目都可以访问这个cookie
			cookie.setPath("/");
			//将cookie写回到用户浏览器
			response.addCookie(cookie);

		} else {
//			5、	创建Cookie （待完成）
			Cookie cookie = new Cookie(Constants.BUYER_CART, om.writeValueAsString(buyerCart));
			//购物车在浏览器的cookie中可以保存一周
			cookie.setMaxAge(60 * 60 * 24 * 7);
			//设置所有项目都可以访问这个cookie
			cookie.setPath("/");
//			6、	把最新购物车保存到Cookie中,回写到浏览器中
			response.addCookie(cookie);
		}

//		7、	转发（重定向、内部转发）
		return "redirect:/shopping/toCart";
	}
	
	/**
	 * 跳转到购物车展示页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/shopping/toCart")
	public String toCart(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		BuyerCart buyerCart = null;
		ObjectMapper om = new ObjectMapper();
		//设置属性中的空值不参与转换
		om.setSerializationInclusion(Include.NON_NULL);
		
//		1、	从Request当中获取Cookies
		Cookie[] cookies = request.getCookies();
		if(cookies != null && cookies.length > 0){
//		2、	遍历Cookie 找购物车
			for(Cookie cookie : cookies){
				if(Constants.BUYER_CART.equals(cookie.getName())){
					//将cookie中存的购物车json格式字符串转换成购物车对象
					buyerCart = om.readValue(cookie.getValue(), BuyerCart.class);
				}
			}
		}
		
		//判断是否登录
		//获取令牌
		String token = RequestUtils.getToken(request, response);
		//根据令牌去redis中获取用户登录信息
		String userName = loginService.getAttributeFromRedis(token);
		if(userName != null && !"".equals(userName)){
			//已登录
//			4、	将购物车添加到Redis中 清空Cookie
			if(buyerCart != null){
				cartService.setBuyerCartToRedis(buyerCart, userName);
//			清空Cookie
				Cookie cookie = new Cookie(Constants.BUYER_CART, null);
				//cookie立即销毁
				cookie.setMaxAge(0);
				//设置所有项目都可以访问这个cookie
				cookie.setPath("/");
				//将cookie写回到用户浏览器
				response.addCookie(cookie);
			}
			
//			5、	从Redis中取出所有购物车 
			buyerCart = cartService.getBuyerCartFromRedis(userName);

		} 
		
//		3、	判断是否有购物车, 有进行下面操作, 无则不进行任何操作
		if(buyerCart != null){
//		4、	将购物车中装满
			List<BuyerItems> items = buyerCart.getItems();
			if(items.size() > 0){
				for(BuyerItems item : items){
					Sku sku = cartService.findSkuById(item.getSku().getId());
					item.setSku(sku);
				}
			}
		}
		
//		5、	回显购物车（model）
		model.addAttribute("buyerCart", buyerCart);
//		6、	跳转到购物车页面
		return "cart";
	}
	
	/**
	 * 跳转到去结算页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/buyer/trueBuy")
	public String trueBuy(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		//获取令牌
		String token = RequestUtils.getToken(request, response);
		//根据令牌到redis中获取用户登录信息, 也就是用户名
		String userName = loginService.getAttributeFromRedis(token);
		//根据用户名到redis中获取购物车
		BuyerCart buyerCart = cartService.getBuyerCartFromRedis(userName);
		//从购物车中获取购物项集合
		List<BuyerItems> items = buyerCart.getItems();
//		判断购物车中是否有购物项
		if(items.size() > 0){
			//是否有货标记, 默认true有货, 只要有一个商品无货, 那么就视为无货 
			Boolean flag = true;
//			有:继续判断
//			判断购物项的商品是否有货(购买数量大于库存数量视为无货)
			for(BuyerItems item : items){
				//从数据库中将库存信息获取完整
				Sku sku = cartService.findSkuById(item.getSku().getId());
				item.setSku(sku);
				//判断是否有货
				if(item.getAmount() > item.getSku().getStock()){
					flag = false;
					item.setIsHave(false);
				}
			}
			
			if(flag){
//				有货: 跳转到结算页面进行结算
				return "order";
			}
//			无货: 跳转回购物车, 进行无货提示
		}
		model.addAttribute("buyerCart", buyerCart);
//		没有: 跳转回购物车进行提示
		return "cart";
	}
	
	/**
	 * 提交订单
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/buyer/submitOrder")
	public String submitOrder(Order order, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取令牌
		String token = RequestUtils.getToken(request, response);
		//根据令牌到redis中获取用户登录信息, 也就是用户名
		String userName = loginService.getAttributeFromRedis(token);
		//保存订单
		cartService.insertOrder(order, userName);
		return "success";
	}
}
