package cn.itcast.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.itcast.pojo.ProductModel;
import cn.itcast.pojo.ResultModel;

@Repository
public class ProductDaoImpl implements ProductDao {

	@Autowired
	private SolrServer solrServer;

	@Override
	public ResultModel search(SolrQuery solrQuery) throws Exception {
		ResultModel resultModel = new ResultModel();
		List<ProductModel> productList = new ArrayList<ProductModel>();
		//查询并获取响应
		QueryResponse queryResponse = solrServer.query(solrQuery);
		//从响应中获取结果集
		SolrDocumentList results = queryResponse.getResults();
		//遍历结果集
		if(results != null){
			//获取查询到的数据的总数
			resultModel.setRecordCount(results.getNumFound());
			for(SolrDocument doc : results){
				ProductModel product = new ProductModel();
				//获取高亮
				Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
				if(highlighting != null){
					List<String> list = highlighting.get(doc.get("id")).get("product_name");
					if(list != null && list.size() > 0){
						product.setName(list.get(0));
					} else {
						product.setName(String.valueOf(doc.get("product_name")));
					}
				} else {
					product.setName(String.valueOf(doc.get("product_name")));
				}
				
				product.setPid(String.valueOf(doc.get("id")));
				if(String.valueOf(doc.get("product_price")) != null 
						&& !"".equals(String.valueOf(doc.get("product_price")))){
					product.setPrice(Float.parseFloat(String.valueOf(doc.get("product_price"))));
				}else {
					product.setPrice(0f);
				}
				
				product.setCatalog_name(String.valueOf(doc.get("product_catalog_name")));
				product.setPicture(String.valueOf(doc.get("product_picture")));
				
				productList.add(product);
			}
			resultModel.setProductList(productList);
		}
		return resultModel;
	}
}
