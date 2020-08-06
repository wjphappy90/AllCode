package cn.itcast.core.common;

import org.apache.commons.io.FilenameUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;

/**
 * 分布式文件系统工具类
 * @author ZJ
 */
public class FastDFSUtils {

	/**
	 * 上传文件到fastDFS分布式文件系统
	 * @param fileName  文件名
	 * @param pic		文件内容
	 * @param size		文件大小
	 * @return
	 * @throws Exception
	 */
	public static String uploadPic(String fileName, byte[] pic, long size) throws Exception {
		//加载配置文件, 交给spring进行管理
		ClassPathResource resource = new ClassPathResource("fdfs_client.conf");
		//再次加载配置文件
		ClientGlobal.init(resource.getClassLoader().getResource("fdfs_client.conf").getPath());
		
		//创建管理端对象
		TrackerClient trackerClient = new TrackerClient();
		//创建管理端的连接
		TrackerServer connection = trackerClient.getConnection();
		//创建存储端对象, 这里第二个参数无用, 传入null就可以
		StorageClient1 storageClient = new StorageClient1(connection, null);
		
		//获取文件扩展名
		String ext = FilenameUtils.getExtension(fileName);
		
		//文件描述信息对象
		NameValuePair[] meta_list = new NameValuePair[3];
		meta_list[0] = new NameValuePair("fileName", fileName);
		meta_list[1] = new NameValuePair("fileExt", ext);
		meta_list[2] = new NameValuePair("fileSize", String.valueOf(size));
		
		//存储并返回存储后的路径, 第一个参数: 文件内容, 第二个参数:文件扩展名, 第三个参数:文件描述信息对象
		String path = storageClient.upload_file1(pic, ext, meta_list);
		return path;
	}
}
