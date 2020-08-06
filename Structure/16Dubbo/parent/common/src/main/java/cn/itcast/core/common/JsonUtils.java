package cn.itcast.core.common;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json转换工具类
 * @author ZJ
 *
 */
public class JsonUtils {

	/**
	 * 将Object类型数据转换成json格式字符串
	 * @param object
	 * @throws Exception
	 */
	public static String objectToJsonStr(Object object) throws Exception {
		//创建转换对象
		ObjectMapper om = new ObjectMapper();
		//设置属性中的空值不参与转换
		om.setSerializationInclusion(Include.NON_NULL);
		//转换并返回
		return om.writeValueAsString(object);
	}
}
