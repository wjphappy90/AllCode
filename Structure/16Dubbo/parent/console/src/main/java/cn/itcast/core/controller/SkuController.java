package cn.itcast.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.core.pojo.product.Sku;
import cn.itcast.core.service.SkuService;

/**
 * 库存管理
 * @author ZJ
 *
 */
@Controller
@RequestMapping("/sku")
public class SkuController {
	
	@Autowired
	private SkuService skuService;

	/**
	 * 跳转到库存管理
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(Long productId, Model model) throws Exception {
		
		List<Sku> skuList = skuService.findSkuListByProductId(productId);
		
		model.addAttribute("skuList", skuList);
		model.addAttribute("productId", productId);
		return "sku/list";
	}
	
	/**
	 * 保存修改
	 * @param sku
	 * @throws Exception
	 */
	@RequestMapping("/update")
	public void update(Sku sku, HttpServletResponse response) throws Exception {
		skuService.updateSku(sku);
		
		JSONObject jo = new JSONObject();
		jo.put("message", "保存成功!");
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(jo.toString());
		
	}
}
