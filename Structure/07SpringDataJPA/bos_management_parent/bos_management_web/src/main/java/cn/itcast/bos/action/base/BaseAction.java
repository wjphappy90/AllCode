package cn.itcast.bos.action.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.base.Courier;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

	protected T model ;
	public T getModel() {
		return model;
	}

	public BaseAction(){
		Type type = this.getClass().getGenericSuperclass();  //得到的结果是父类的参数化类型   BaseAction<Courier>
		ParameterizedType ptype = (ParameterizedType)type;
		Type[] types = ptype.getActualTypeArguments();  //<Courier>
		Class<T> t = (Class<T>) types[0]; //类
		try {
			model = t.newInstance();  //实例
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	protected int page;
	protected int rows;
	public void setPage(int page) {
		this.page = page;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	
//	把Java对象或集合转成json回显到浏览器上
	public void javaToJson(Map map,String[] strings){
		try {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");  //响应回去的数据是json对象
		PrintWriter writer = response.getWriter();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(strings);
		String string = JSONObject.fromObject(map,jsonConfig).toString();
		writer.write(string);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	把Java对象或集合转成json回显到浏览器上
	public void javaToJson(List list,String[] strings){
		try {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");  //响应回去的数据是json对象
		PrintWriter writer = response.getWriter();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(strings);
		String string = JSONArray.fromObject(list,jsonConfig).toString();
		writer.write(string);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Map ajaxReturn(boolean success,String message){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", success);
		map.put("message", message);
		return map;
	}

}
