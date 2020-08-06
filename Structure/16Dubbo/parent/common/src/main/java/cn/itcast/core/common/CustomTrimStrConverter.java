package cn.itcast.core.common;

import org.springframework.core.convert.converter.Converter;

/**
 * 自定义转换器, 处理去除空格
 * @author ZJ
 *
 */
public class CustomTrimStrConverter implements Converter<String, String> {

	@Override
	public String convert(String source) {
		if(source != null){
			source = source.trim();
			if(!"".equals(source)){
				return source;
			}
		}
		return null;
	}

}
