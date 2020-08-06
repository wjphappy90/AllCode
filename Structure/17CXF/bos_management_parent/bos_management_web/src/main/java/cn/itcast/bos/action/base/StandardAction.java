package cn.itcast.bos.action.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.base.StandardService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
public class StandardAction extends BaseAction<Standard> {

	@Autowired
	private StandardService service;
	
	@Action("standardAction_findByPage")
	public void findByPage() throws IOException{
		Pageable pageable = new PageRequest(page-1, rows);
		Page<Standard> page = service.findByPage(pageable);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalElements());//getTotalElements() 总记录数
		map.put("rows", page.getContent());//getContent() 当前页的数据
		javaToJson(map,null);
	}
	
	@Action("standardAction_save")
	public void save(){
//		data的数据格式 {"success":true|false,"message":"保存成功|保存失败"}
		try {
			service.save(model);
//			成功
			javaToJson(ajaxReturn(true, "操作成功"),null);
		} catch (Exception e) {
//			失败
			javaToJson(ajaxReturn(false, "操作失败"),null);
			e.printStackTrace();
		}
	}
	
	@Action("standardAction_findAll")
	public void findAll() throws IOException{
		List<Standard> list = service.findAll();
		javaToJson(list,null);
	}
}
