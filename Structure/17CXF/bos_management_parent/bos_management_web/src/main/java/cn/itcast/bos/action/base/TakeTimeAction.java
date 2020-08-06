package cn.itcast.bos.action.base;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.base.TakeTime;
import cn.itcast.bos.service.base.TakeTimeService;

@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
public class TakeTimeAction extends BaseAction<TakeTime> {

	@Autowired
	private TakeTimeService service;
	
	@Action("takeTimeAction_findAll")
	public void findAll(){
		List<TakeTime> list = service.findAll();
		javaToJson(list, null);
	}
	
}
