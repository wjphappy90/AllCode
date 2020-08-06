package cn.itcast.mail;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailUtils {

	private JavaMailSender javaMailSender;
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}


	public void sendMail(){
//		创建一个邮件对象
		MimeMessage mime = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mime);
//		发送方
		try {
			helper.setFrom("ljc13273@126.com");
//			接收方
			helper.setTo("itcast_server@sina.com");
//			主题
			helper.setSubject("spring发送邮件");
//			内容
			helper.setText("<h1>spring发送邮件的内容</h1>",true);
			javaMailSender.send(mime);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	发送邮件带附件
	public void sendMail1(){
//		创建一个邮件对象
		MimeMessage mime = javaMailSender.createMimeMessage();
		

		try {
			MimeMessageHelper helper = new MimeMessageHelper(mime,true,"utf-8");//true代表可以发送多媒体文件
//			发送方
			helper.setFrom("ljc13273@126.com");
//			接收方
//			helper.setTo("dongyingnan@outlook.com");
			helper.setTo("itcast_server@sina.com");
//			主题
			helper.setSubject("spring发送邮件");
			helper.addAttachment("区域导入测试数据.xls", new File("D:\\区域导入测试数据.xls"));
//			内容
//			helper.setText("<span style='color:red'>spring发送邮件的内容</span>",true);
			helper.setText("<img src='cid:myImg'>", true);
			helper.addInline("myImg", new File("d:\\37d3d539b6003af39dd744af3d2ac65c1138b694.jpg"));
			javaMailSender.send(mime);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
