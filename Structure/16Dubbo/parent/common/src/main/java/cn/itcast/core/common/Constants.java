package cn.itcast.core.common;

/**
 * 常量
 * @author ZJ
 *
 */
public interface Constants {

	//分布式文件系统服务器地址
	public final static String FILE_SERVER = "http://192.168.200.128/";
	//redis中品牌的key
	public final static String REDIS_BRANDS = "brands";
	//cookie中存入令牌的key
	public final static String COOKIE_TOKEN = "token";
	//redis中存入的用户登录信息前缀
	public final static String REDIS_USERNAME = "UserName_";
	
	public final static String BUYER_CART = "buyer_cart";
}
