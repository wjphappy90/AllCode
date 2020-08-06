package cn.itcast.bos.action;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.persistence.Access;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bos.utils.MailUtils;
import cn.itcast.bos.utils.SmsUtils;
import cn.itcast.bos.utils.sendsms;
import cn.itcast.crm.service.ws.Customer;
import cn.itcast.crm.service.ws.CustomerService;
import redis.clients.jedis.Jedis;

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class CustomerAction extends BaseAction<Customer> {
	
	@Action("customerAction_sendCode")
	public void sendCode(){
//		生成4个随机数
		String randomNumeric = RandomStringUtils.randomNumeric(4);
		System.out.println("randomNumeric:"+randomNumeric);
//		调用吉信通的发送短信的服务
//		SmsUtils.sendSmsByWebService(model.getTelephone(), "尊敬的用户，您的验证码是"+randomNumeric);
//		调用互亿无线的发送短信平台
		sendsms.sendCode(model.getTelephone(), randomNumeric);
		//		把验证码保存到session中，以供之后校验
		ServletActionContext.getRequest().getSession().setAttribute(model.getTelephone(), randomNumeric);
	}
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	private CustomerService crmProxy;
	private String checkcode;//验证码
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	@Action(value="customerAction_regist",
			results={@Result(name="success",type="redirect",location="/signup-success.html")
			,@Result(name="error",type="redirect",location="/signup-fail.html")})
	public String regist(){
		
		String randomNumeric = (String) ServletActionContext.getRequest().getSession().getAttribute(model.getTelephone());
	
		if(checkcode.equals(randomNumeric)){
			try {
				crmProxy.save(model);
				String randomUUID = UUID.randomUUID().toString();
				String mailUrl ="http://localhost:8082/bos_fore/activeMail.action?telephone="+model.getTelephone()+"&code="+randomUUID;
//				发送邮件  
				MailUtils.sendMail("激活邮件", "尊敬的用户您好，请在24小时内点击以下链接激活邮箱：<a href='"+mailUrl+"'>"+mailUrl+"</a>", model.getEmail());
//				opsForValue用来操作字符串的方法
//				p1:key  p2:value  p3：时间长度  p4：时间的单位
				redisTemplate.opsForValue().set(model.getTelephone(), randomUUID, 24, TimeUnit.HOURS);
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return "error";
			}
		}else{
			return "error";
		}
	}
	
//	/activeMail.action?telephone=13456789876&code=234176f9-746c-4bdc-85fb-4daf3da67dbb
	private String code;
	public void setCode(String code) {
		this.code = code;
	}
	@Action("activeMail")
	public void activeMail(){
//		1、从redis中获取验证码和页面上传过来的验证码做比较
		String redisCode = redisTemplate.opsForValue().get(model.getTelephone());
		if(redisCode==null){ //判断是否超过24小时
			javaToJson("激活码已失效，激活失败");
		}else{
			if(!code.equals(redisCode)){ //判断两个值是否相等
				javaToJson("激活码错误，激活失败");
			}else{
//				激活客户 -调用crm提供的方法
				try {
					crmProxy.activeMail(model.getTelephone());
					javaToJson("激活成功");
				} catch (Exception e) {
					javaToJson("激活码失败");
					e.printStackTrace();
				}
			}
		}
		
	}

	@Action("customerAction_findByTelephone")
	public void findByTelephone(){
//		model.getTelephone()
		Customer customer = crmProxy.findByTelephone(model.getTelephone());
		if(customer==null){
			javaToJson(ajaxReturn(true, ""), null);
		}else{
			javaToJson(ajaxReturn(false, ""), null);
		}
	}
	
	@Action("customerAction_login")
	public void login(){
		String validateCode = (String) ServletActionContext.getRequest().getSession().getAttribute("validateCode");
		if(!validateCode.equals(checkcode)){
			javaToJson(ajaxReturn(false, "验证码不正确"), null);
			return;
		}
		if(model.getTelephone().contains("@")){
//			crm根据邮箱查询
			Customer customer = crmProxy.findByEmailAndPassword(model.getTelephone(), model.getPassword());
			if(customer==null){
				javaToJson(ajaxReturn(false, "用户名或密码错误"), null);
				return;
			}
			if(customer.getType()==null){
				javaToJson(ajaxReturn(false, "邮箱未激活"), null);
				return;
			}
			javaToJson(ajaxReturn(true, ""), null);
		}else{
//			crm根据电话号码查询
			Customer customer = crmProxy.findByTelephoneAndPassword(model.getTelephone(), model.getPassword());
			if(customer==null){
				javaToJson(ajaxReturn(false, "用户名或密码错误"), null);
				return;
			}
			javaToJson(ajaxReturn(true, ""), null);
		}
	}
	
	
}
