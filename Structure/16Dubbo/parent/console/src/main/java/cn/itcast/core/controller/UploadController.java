package cn.itcast.core.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import cn.itcast.core.common.Constants;
import cn.itcast.core.service.UploadService;

/**
 * 本类处理上传文件
 * @author ZJ
 */
@Controller
@RequestMapping("/upload")
public class UploadController {
	
	@Autowired
	private UploadService uploadService;

	/**
	 * 上传单张图片
	 * @param pic
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/updatePic")
	public void updatePic(MultipartFile pic, HttpServletResponse response) throws Exception {
		//1. 获取上传文件的完整文件名称
//		String fileName = pic.getOriginalFilename();
//		//2. 使用随机字符串+源文件扩展名组成新的文件名称
//		String newFileName = UUID.randomUUID().toString().replaceAll("-", "") + "." +FilenameUtils.getExtension(fileName);
//		//3. 将文件保存到硬盘中
//		pic.transferTo(new File("E:\\image\\"+newFileName));
		//4. 将文件名称返回
		
		//使用fastDFS上传图片到分布式文件系统
		String path = uploadService.uploadPic(pic.getBytes(), pic.getOriginalFilename(), pic.getSize());
		//JSONObject只能转换string, integer, long, boolean, float, double等基本类型,对于pojo, list, map, set无法转换
		JSONObject jo = new JSONObject();
		jo.put("url", Constants.FILE_SERVER + path);
		response.getWriter().write(jo.toString());
		
	}
	
	/**
	 * 批量上传图片
	 * 
	 */
	@RequestMapping("/uploadPics")
	@ResponseBody
	public List<String> uploadPics(@RequestParam MultipartFile[] pics) throws Exception {
		List<String> urlList = new ArrayList<String>();
		if(pics != null){
			for(MultipartFile pic : pics){
				//使用fastDFS上传图片到分布式文件系统
				String path = uploadService.uploadPic(pic.getBytes(), pic.getOriginalFilename(), pic.getSize());
				urlList.add(Constants.FILE_SERVER + path);
			}
		}
		return urlList;
	}
	
	/**
	 * 无敌版上传(不管单张图片还是多张图片都可以上传)
	 * 即使不知道上传域的name属性名称也可以上传
	 */
	@RequestMapping("/uploadFck")
	public void uploadFck(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//因为页面我们用的是富文本编辑器, 所以不知道上传域的name属性名称, 所以不能用MultipartFile接口接收上传的图片.
		//但是所有上传的东西都是请求所以可以用HttpServletRequest来接收, 
		//这里强转成MultipartRequest接口, 为了方便从里面提取上传的内容
		MultipartRequest multipartRequest = (MultipartRequest)request;
		//获取上传的一堆图片的Map
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		if(fileMap != null && fileMap.size() > 0){
			Set<Entry<String, MultipartFile>> entrySet = fileMap.entrySet();
			for(Entry<String, MultipartFile> entry : entrySet){
				//获取每一个上传的文件的接口
				MultipartFile pic = entry.getValue();
				//使用fastDFS上传图片到分布式文件系统
				String path = uploadService.uploadPic(pic.getBytes(), pic.getOriginalFilename(), pic.getSize());
				String url = Constants.FILE_SERVER + path;
				JSONObject jo = new JSONObject();
				//前端kindeditor插件要求返回json格式数据, 第一个数据叫做url, 图片地址, 第二个数据叫做error, 错误信息, 
				//如果error为0则无错误信息
				jo.put("url", url);
				jo.put("error", 0);
				response.getWriter().write(jo.toString());
			}
		}
	}
}
