package cn.itcast.core.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.common.JsonUtils;
import cn.itcast.core.pojo.ad.Ad;
import cn.itcast.core.pojo.product.Brand;
import cn.itcast.core.pojo.product.Color;
import cn.itcast.core.pojo.product.Product;
import cn.itcast.core.pojo.product.Sku;
import cn.itcast.core.service.AdService;
import cn.itcast.core.service.BrandService;
import cn.itcast.core.service.CmsService;
import cn.itcast.core.service.SearchService;

@Controller
public class ProductController {
	
	@Autowired
	private SearchService searchService;
	
	@Autowired
	private BrandService brandService;
	
	@Autowired
	private CmsService cmsService;
	
	@Autowired
	private AdService adService;

	/**
	 * 跳转到首页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/")
	public String index(Model model) throws Exception {
		//List<Ad> adList = adService.findAdByPositionId(89L);
		//将集合转换成json格式字符串
		//String str = JsonUtils.objectToJsonStr(adList);
		
		//大广告优化, 从redis中获取大广告数据
		String str = adService.findAdListFromRedis();
		model.addAttribute("ads", str);
		return "index";
	}
	
	/**
	 * 搜索并返回到结果列表页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/product/list")
	public String list(String keyword, Long brandId, String price, Integer pageNo, Model model) throws Exception {
		//加载品牌
		//List<Brand> brandList = brandService.findBrandList(null, null);
		//从redis分布式缓存中加载品牌数据
		List<Brand> brandList = brandService.findBrandFromRedis();
		
		Pagination pagination = searchService.searchProductPage(keyword, pageNo, brandId, price);
		
		//显示已选择的过滤条件
		Map<String, String> map = new HashMap<String, String>();
		if(brandId != null){
			for(Brand brand : brandList){
				if(brand.getId() == brandId){
					map.put("品牌为:", brand.getName());
					break;
				}
			}
		}
		
		if(price != null){
			map.put("价格为:", price);
		}
		
		model.addAttribute("map", map);
		model.addAttribute("pagination", pagination);
		model.addAttribute("keyword", keyword);
		model.addAttribute("brands", brandList);
		model.addAttribute("brandId", brandId);
		model.addAttribute("price", price);
		return "search";
	}
	
	/**
	 * 跳转到商品详情页面
	 * 根据商品id获取商品对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/product/detail")
	public String detail(Long id, Model model) throws Exception {
		//根据商品id获取商品对象
		Product product = cmsService.findProductById(id);
		//根据商品id获取库存集合
		List<Sku> skuList = cmsService.findSkuListByProductId(id);
		
		//过滤颜色重复数据
		Set<Color> colors = new HashSet<Color>();
		if(skuList != null){
			for(Sku sku : skuList){
				colors.add(sku.getColor());
			}
		}
		
		model.addAttribute("colors", colors);
		model.addAttribute("skuList", skuList);
		model.addAttribute("product", product);
		return "product";
	}
}
