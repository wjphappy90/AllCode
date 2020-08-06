package cn.itcast.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.core.pojo.ad.Position;
import cn.itcast.core.service.PositionService;

/**
 * 广告位维护
 * @author ZJ
 *
 */
@Controller
@RequestMapping("/position")
public class PositionController {
	
	@Autowired
	private PositionService positionService;

	/**
	 * 获取广告左侧树形菜单数据
	 * root 当前选中的节点的id, root参数是treeView插件规定的 , 如果是根节点, 那么root的值为字符串source
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/tree")
	public String tree(String root, Model model) throws Exception {
		List<Position> positionList = null;
		if("source".equals(root)){
			//根节点
			positionList = positionService.findPositionListByParentId(0L);
		} else {
			//选中其它节点
			positionList = positionService.findPositionListByParentId(Long.parseLong(root));
		}
		
		model.addAttribute("list", positionList);
		return "position/tree";
	}
	
	/**
	 * 广告位维护列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(String root, Model model) throws Exception {
		List<Position> positionList = positionService.findPositionListByParentId(Long.parseLong(root));
		model.addAttribute("list", positionList);
		return "position/list";
	}
}
