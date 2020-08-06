package cn.itcast.bos.action.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.base.StandardService;
import net.sf.json.JSONObject;

@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
public class StandardAction extends ActionSupport implements ModelDriven<Standard> {

	Standard  model = new Standard();
	public Standard getModel() {
		return model;
	}

	@Autowired
	private StandardService service;
	
	@Action("standardAction_save")
	public void save(){
//		data的数据格式 {"success":true|false,"message":"保存成功|保存失败"}
		Map<String, Object> map = new HashMap<String, Object>();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");  //响应回去的数据是json对象
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			service.save(model);
//			成功
			map.put("success", true);
			map.put("message", "保存成功");
			String jsonString = JSONObject.fromObject(map).toString();
			writer.write(jsonString);
		} catch (Exception e) {
//			失败
			map.put("success", false);
			map.put("message", "保存失败");
			String jsonString = JSONObject.fromObject(map).toString();
			writer.write(jsonString);
			e.printStackTrace();
		}
	}
	
}
