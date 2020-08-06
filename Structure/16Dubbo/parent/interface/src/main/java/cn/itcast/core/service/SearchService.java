package cn.itcast.core.service;

import cn.itcast.common.page.Pagination;

public interface SearchService {

	public Pagination searchProductPage(String keyword, Integer pageNo, Long brandId, String price) throws Exception;
}
