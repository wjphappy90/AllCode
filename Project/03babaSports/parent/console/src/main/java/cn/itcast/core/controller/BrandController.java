package cn.itcast.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.pojo.product.Brand;
import cn.itcast.core.service.BrandService;

/**
 * 品牌管理
 * 品牌列表
 * 品牌批量删除
 * 品牌修改
 * @author ZJ
 *
 */
@Controller
@RequestMapping("/brand")
public class BrandController {
	
	@Autowired
	private BrandService brandService;

	/**
	 * 品牌列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(String name, Integer isDisplay, Integer pageNo, Model model) throws Exception {
		//List<Brand> brandList = brandService.findBrandList(name, isDisplay);
		
		Pagination pagination = brandService.findBrandPagination(name, isDisplay, pageNo);
		
		model.addAttribute("pagination", pagination);
		model.addAttribute("name", name);
		model.addAttribute("isDisplay", isDisplay);
		return "brand/list";
	}
	
	/**
	 * 跳转到修改页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toEdit")
	public String toEdit(Long id, Model model) throws Exception {
		Brand brand = brandService.findBrandById(id);
		model.addAttribute("brand", brand);
		return "brand/edit";
	}
	
	/**
	 * 保存品牌修改
	 * @param brand
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/edit")
	public String edit(Brand brand) throws Exception {
		brandService.updateBrand(brand);
		return "redirect:/brand/list.action";
	}
	
	/**
	 * 批量删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteAll")
	public String deleteAll(Long[] ids) throws Exception {
		brandService.delBrandAll(ids);
		return "forward:list.action";
	}
}
