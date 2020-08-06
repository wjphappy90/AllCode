package cn.itcast.mail.test;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

import cn.itcast.mail.MailUtils;

public class MailTest {

	public static void main(String[] args) {
		ApplicationContext app = new ClassPathXmlApplicationContext("classpath:applicationContext_mail.xml");
		MailUtils mailUtils = (MailUtils) app.getBean("mailUtil");
//		mailUtils.sendMail();
		mailUtils.sendMail1();
	}

}
