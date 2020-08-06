package cn.itcast.service;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.dao.ProductDao;
import cn.itcast.pojo.ResultModel;

@Service
public class ProductServiceImpl implements ProductService {
	//每页显示8条数据
	private static final Integer PAGE_SIZE = 8;

	@Autowired
	private ProductDao productDao;

	@Override
	public ResultModel search(String queryString, String catalog_name, String price, Integer page, String sort)
			throws Exception {
		//创建查询条件
		SolrQuery solrQuery = new SolrQuery();
		//设置默认搜索域
		solrQuery.set("df", "product_keywords");
		//设置查询关键字
		if(queryString != null && !"".equals(queryString)){
			solrQuery.setQuery(queryString);
		} else {
			solrQuery.setQuery("*:*");
		}
		//根据分类名称过滤
		if(catalog_name != null && !"".equals(catalog_name)){
			solrQuery.addFilterQuery("product_catalog_name:" + catalog_name);
		}
		//根据价格过滤
		if(price != null && !"".equals(price)){
			String[] split = price.split("-");
			if(split.length == 2){
				solrQuery.addFilterQuery("product_price:["+split[0]+" TO "+split[1]+"]");
			}
		}
		//根据价格排序
		if("1".equals(sort)){
			solrQuery.setSort("product_price", ORDER.asc);
		} else {
			solrQuery.setSort("product_price", ORDER.desc);
		}
		
		//分页
		if(page == null || page < 1){
			page = 1;
		}
		Integer start = (page - 1) * PAGE_SIZE;
		//起始查询条数
		solrQuery.setStart(start);
		//每页查询多少条
		solrQuery.setRows(PAGE_SIZE);
		
		//高亮
		//开启高亮显示
		solrQuery.setHighlight(true);
		//设置高亮显示的域
		solrQuery.addHighlightField("product_name");
		//设置高亮前缀
		solrQuery.setHighlightSimplePre("<span style=\"color:red\">");
		//设置高亮后缀
		solrQuery.setHighlightSimplePost("</span>");
		
		//查询并返回结果
		ResultModel resultModel = productDao.search(solrQuery);
		//设置当前页
		resultModel.setCurPage(page);
		//计算总页数
		Long count = resultModel.getRecordCount()/PAGE_SIZE;
		if(resultModel.getRecordCount()%PAGE_SIZE > 0){
			count++;
		}
		resultModel.setPageCount(count);
		return resultModel;
	}
}
