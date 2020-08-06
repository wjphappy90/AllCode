package cn.itcast.bos.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.service.base.SubAreaService;
import cn.itcast.bos.utils.FileUtils;

@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
public class SubAreaAction extends BaseAction<SubArea> {
	@Autowired
	private SubAreaService service;
	
	@Action("subAreaAction_save")
	public void save(){
		try {
			service.save(model);
			javaToJson(ajaxReturn(true, "操作成功"), null);
		} catch (Exception e) {
			javaToJson(ajaxReturn(false, "操作失败"), null);
			e.printStackTrace();
		}
	}
	@Action("subAreaAction_findByPage")
	public void findByPage(){
		Pageable pageable = new PageRequest(page-1, rows);
		Page<SubArea> page = service.findByPage(pageable);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalElements());
		map.put("rows", page.getContent());
		javaToJson(map, new String[]{"subareas","couriers"});
	}
	
	@Action("subAreaAction_export")
	public void export() throws Exception{
//		ServletActionContext.getServletContext().getRealPath(File.separator) 项目的根目录
		String filePath = ServletActionContext.getServletContext().getRealPath(File.separator)+"template"+File.separator+"sub_area.xls";
		FileInputStream in = new FileInputStream(new File(filePath));
		HttpServletResponse response = ServletActionContext.getResponse();
		ServletOutputStream out = response.getOutputStream();
		HttpServletRequest request = ServletActionContext.getRequest();
		String agent = request.getHeader("User-Agent");
		String fileName = FileUtils.encodeDownloadFilename("分区数据.xls", agent);
		response.setHeader("content-disposition", "attachment;filename="+fileName);
//		response.setContentType("application/vnd.ms-excel");
		service.export(in, out);
//		文件的下载  一个流 两个头
//		两个头 文件的打开方式   文件的mime类型  application/vnd.ms-excel是可以省略
//		inline浏览器中打开    attachment
//		contentDisposition

	}
	@Action("subAreaAction_showChart")
	public void showChart(){
		List list = service.showChart();
		javaToJson(list, null);
	}
}
