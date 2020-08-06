package cn.itcast.core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.pojo.product.Product;
import cn.itcast.core.pojo.product.ProductQuery;

@Service("searchServiceImpl")
public class SearchServiceImpl implements SearchService {
	
	@Autowired
	private SolrServer solrServer;

	@Override
	public Pagination searchProductPage(String keyword, Integer pageNo, Long brandId, String price) throws Exception {
		StringBuilder params = new StringBuilder();
		ProductQuery productQuery = new ProductQuery();
		//设置起始查询页数
		productQuery.setPageNo(Pagination.cpn(pageNo));
		//设置每页查询多少条
		productQuery.setPageSize(8);
		List<Product> productList = new ArrayList<Product>();
		
		
		//创建solr查询条件对象
		SolrQuery solrQuery = new SolrQuery();
		//设置查询关键字
		if(keyword != null){
			solrQuery.setQuery("name_ik:" + keyword);
			params.append("&keyword=").append(keyword);
		} else {
			solrQuery.setQuery("*:*");
		}
		//按照品牌过滤
		if(brandId != null){
			solrQuery.addFilterQuery("brandId:"+ brandId);
			params.append("&brandId=").append(brandId);
		}
		//按照价格过滤
		if(price != null){
			String[] split = price.split("-");
			if(split.length == 2){
				solrQuery.addFilterQuery("price:["+split[0]+" TO "+split[1]+"]");
			} else {
				solrQuery.addFilterQuery("price:["+split[0]+" TO *]");
			}
		}
		
		//设置分页
		//设置起始查询条数
		solrQuery.setStart(productQuery.getStartRow());
		//设置每页查询多少条数据
		solrQuery.setRows(productQuery.getPageSize());
		
		//设置根据价格升序排序
		solrQuery.setSort("price", ORDER.asc);
		
		//设置高亮显示
		//开启高亮
		solrQuery.setHighlight(true);
		//设置高亮显示的域名
		solrQuery.addHighlightField("name_ik");
		//设置高亮前缀
		solrQuery.setHighlightSimplePre("<span style=\"color:red\">");
		//设置高亮后缀
		solrQuery.setHighlightSimplePost("</span>");
		
		//搜索并返回响应
		QueryResponse queryResponse = solrServer.query(solrQuery);
		//从响应中获取结果集对象
		SolrDocumentList results = queryResponse.getResults();
		long count = 0;
		if(results != null){
			//获取查询到的结果集总数
			count = results.getNumFound();
			for(SolrDocument doc : results){
				Product product = new Product();
				//获取高亮显示结果
				Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
				if(highlighting != null){
					List<String> list = highlighting.get(doc.get("id")).get("name_ik");
					if(list != null && list.size() > 0){
						product.setName(list.get(0));
					} else {
						product.setName(String.valueOf(doc.get("name_ik")));
					}
				} else {
					product.setName(String.valueOf(doc.get("name_ik")));
				}
				
				product.setId(Long.parseLong(String.valueOf(doc.get("id"))));
				product.setBrandId(Long.parseLong(String.valueOf(doc.get("brandId"))));
				product.setImgUrl(String.valueOf(doc.get("url")));
				if(String.valueOf(doc.get("price")) != null 
						&& !"".equals(String.valueOf(doc.get("price")))){
					product.setPrice(Float.parseFloat(String.valueOf(doc.get("price"))));
				} else {
					product.setPrice(0f);
				}
				productList.add(product);
			}
		}
		
		//创建分页对象
		Pagination pagination = new Pagination(
				productQuery.getPageNo(), 
				productQuery.getPageSize(), 
				(int)count, 
				productList);
		//设置翻页跳转路径
		String url = "/product/list";
		pagination.pageView(url, params.toString());
		return pagination;
	}

}
