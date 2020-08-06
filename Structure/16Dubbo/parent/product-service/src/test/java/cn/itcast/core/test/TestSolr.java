package cn.itcast.core.test;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
public class TestSolr {
	
	@Autowired
	private SolrServer solrServer;

	@Test
	public void testSolr() throws Exception {
		//连接solr服务器
		//SolrServer solrServer = new HttpSolrServer("http://192.168.200.128:8080/solr");
		//创建文档对象
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", "003");
		doc.addField("title", "西游记");
		//将文档对象加入到solr服务中
		solrServer.add(doc);
		//提交
		solrServer.commit();
	}
	
}
