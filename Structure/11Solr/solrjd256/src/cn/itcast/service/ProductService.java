package cn.itcast.service;

import cn.itcast.pojo.ResultModel;

public interface ProductService {

	public ResultModel search(String queryString, String catalog_name, String price, 
			Integer page, String sort) throws Exception;
}
