package cn.itcast.core.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 生成静态化页面service
 * 因为在freemaker.xml中配置了这个类的bean所以这里就不用写@Service注解了
 * @author ZJ
 *
 */
public class StaticPageServiceImpl implements StaticPageService, ServletContextAware {
	
	private Configuration configuration ;
	
	//使用ServletContextAware对象从spring中获取ServletContextAware的实例来初始化这个属性
	private ServletContext servletContext;
	
	//初始化Configuration对象
	public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
		configuration = freeMarkerConfigurer.getConfiguration();
	}

	@Override
	public void createStaticPage(Map<String, Object> rootMap, String productId) throws Exception {
		//1. 设置生成的静态化页面的位置和文件名称
		String path = "/html/" + productId + ".html";
		//2. 根据相对路径获取绝对路径, http://IP地址:端口号/项目名如果没有自动省略/html/productId.html
		String url = getRealPath(path);
		//3. 判断url路径中的文件夹是否都存在, 如果不存在则自动创建
		File file = new File(url);
		//获取上级目录
		File parentFile = file.getParentFile();
		if(!parentFile.exists()){
			parentFile.mkdirs();
		}
		
		//获取模板对象, 模板所在目录在freemarker.xml中已经配置好
		Template template = configuration.getTemplate("product.html");
		//设置输出流, 指定输出的静态页面的位置和名称
		Writer out = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
		//生成静态化页面
		template.process(rootMap, out);
	}
	
	//根据相对路径获取绝对路径
	private String getRealPath(String path) {
		return servletContext.getRealPath(path);
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

}
