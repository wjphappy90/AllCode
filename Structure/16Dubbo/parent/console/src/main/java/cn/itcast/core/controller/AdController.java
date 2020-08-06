package cn.itcast.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.core.pojo.ad.Ad;
import cn.itcast.core.pojo.ad.Position;
import cn.itcast.core.service.AdService;
import cn.itcast.core.service.PositionService;

/**
 * 广告数据维护
 * @author ZJ
 *
 */
@Controller
@RequestMapping("/ad")
public class AdController {
	
	@Autowired
	private AdService adService;
	
	@Autowired
	private PositionService positionService;

	/**
	 * 加载广告数据列表
	 * root这里是广告位的id, 也就是positionId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(String root, Model model) throws Exception {
		List<Ad> adList = adService.findAdByPositionId(Long.parseLong(root));
		
		model.addAttribute("adList", adList);
		model.addAttribute("positionId", root);
		return "ad/list";
	}
	
	/**
	 * 跳转到添加页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toAdd")
	public String toAdd(Long positionId, Model model) throws Exception {
		Position position = positionService.findPositionById(positionId);
		model.addAttribute("position", position);
		model.addAttribute("positionId", positionId);
		return "ad/add";
	}
	
	/**
	 * 保存添加广告数据
	 * @param ad
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/add")
	public String add(Ad ad) throws Exception {
		adService.insertAd(ad);
		return "redirect:/ad/list.action?root=" + ad.getPositionId();
	}
}
