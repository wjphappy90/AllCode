package cn.itcast.solr;

import static org.junit.Assert.*;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * 测试solr增删改
 * @author ZJ
 *
 */
public class TestIndexManager {

	/**
	 * solr中没有专门的修改方法, 添加和修改都一样, 都是根据id查询, 如果查找到了则将原来的数据删除
	 * 将新的数据添加进去, 如果没有查询到, 则直接添加数据
	 * @throws Exception
	 */
	@Test
	public void testIndexCreateAndUpdate() throws Exception {
		//创建solr服务连接
		//这里是连接默认的solr实例, 也就是collection1实例
		//如果连接其他实例例如:	http://localhost:8080/solr/实例名称
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr");
		
		//创建solr的文档对象
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", "003");
		doc.addField("title", "三国演义");
		
		//将文档对象添加到solr服务中
		solrServer.add(doc);
		//提交
		solrServer.commit();
	}
	
	/**
	 * 测试删除
	 * @throws Exception
	 */
	@Test
	public void testIndexDelete() throws Exception {
		//创建solr服务连接
		//这里是连接默认的solr实例, 也就是collection1实例
		//如果连接其他实例例如:	http://localhost:8080/solr/实例名称
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr");
		
		//根据id删除
		//solrServer.deleteById("001");
		
		//根据查询条件删除,这里是删除所有
		solrServer.deleteByQuery("*:*");
		
		//提交
		solrServer.commit();
	}
}
