package cn.itcast.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.pojo.product.Brand;
import cn.itcast.core.pojo.product.Color;
import cn.itcast.core.pojo.product.Product;
import cn.itcast.core.service.BrandService;
import cn.itcast.core.service.ColorService;
import cn.itcast.core.service.ProductService;

/**
 * 商品管理
 * @author ZJ
 *
 */
@Controller
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private BrandService brandService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ColorService colorService;

	/**
	 * 商品列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(String name, Long brandId, Boolean isShow, Integer pageNo, Model model) throws Exception {
		List<Brand> brandList = brandService.findBrandList(null, null);
		model.addAttribute("brandList", brandList);
		
		Pagination pagination = productService.findProductPage(name, brandId, isShow, pageNo);
		model.addAttribute("pagination", pagination);
		model.addAttribute("name", name);
		model.addAttribute("brandId", brandId);
		model.addAttribute("isShow", isShow);
		return "product/list";
	}
	
	/**
	 * 跳转到添加页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toAdd")
	public String toAdd(Model model) throws Exception {
		//加载品牌数据
		List<Brand> brandList = brandService.findBrandList(null, null);
		model.addAttribute("brandList", brandList);
		
		//加载颜色数据
		List<Color> colorList = colorService.findColorList();
		model.addAttribute("colorList", colorList);
		return "product/add";
	}
	
	@RequestMapping("/add")
	public String add(Product product) throws Exception {
		productService.insertProduct(product);
		return "redirect:/product/list.action";
	}
	
	/**
	 * 商品上架
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/isShow")
	public String isShow(Long[] ids) throws Exception {
		productService.isShow(ids);
		return "redirect:/product/list.action";
	}
}
