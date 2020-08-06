package cn.itcast.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

/**
 * 测试solr查询
 * @author ZJ
 *
 */
public class TestIndexSearch {

	/**
	 * 测试简单查询
	 * @throws Exception
	 */
	@Test
	public void testSimpleSearch() throws Exception {
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr");
		
		//创建solr查询条件对象
		SolrQuery solrQuery = new SolrQuery();
		//设置查询的关键字, 这里是查询所有
		solrQuery.setQuery("*:*");
		//查询并返回响应, 通过本项目远程连接solr服务器, 发送请求查询, 并返回响应
		QueryResponse queryResponse = solrServer.query(solrQuery);
		//从响应中获取结果集
		SolrDocumentList results = queryResponse.getResults();
		//遍历结果集
		if(results != null){
			//获取查询到的数据的总数
			System.out.println("======count====" + results.getNumFound());
			for(SolrDocument doc : results){
				System.out.println("===id====" + doc.get("id"));
				System.out.println("===title====" + doc.get("title"));
				System.out.println("========================================");
			}
		}
	}
}
