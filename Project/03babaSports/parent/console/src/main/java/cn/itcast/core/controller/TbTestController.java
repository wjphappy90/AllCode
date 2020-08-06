package cn.itcast.core.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.core.pojo.TbTest;
import cn.itcast.core.service.TbTestService;

@Controller
public class TbTestController {
	
	@Autowired
	private TbTestService tbService;

	@RequestMapping("/test")
	public String test() throws Exception {
		TbTest tb = new TbTest();
		tb.setName("王五");
		tb.setBirthday(new Date());
		tbService.insertTest(tb);
		return "test";
	}
}
