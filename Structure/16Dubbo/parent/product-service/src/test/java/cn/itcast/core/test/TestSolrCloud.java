package cn.itcast.core.test;

import javax.annotation.security.RunAs;

import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
public class TestSolrCloud {
	
	@Autowired
	private CloudSolrServer solrServer;

	/**
	 *  测试solr集群
	 * @throws Exception
	 */
	@Test
	public void testSolrCloud() throws Exception {
		//设置zookeeper集群的ip和端口
		//String zkHost = "192.168.200.128:2181,192.168.200.128:2182,192.168.200.128:2183";
		//创建solr集群实例连接solr集群
		//CloudSolrServer solrServer = new CloudSolrServer(zkHost);
		//设置使用的默认solr实例
		//solrServer.setDefaultCollection("collection1");
		
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", "003");
		doc.addField("title", "红楼梦");
		solrServer.add(doc);
		solrServer.commit();
	}
}
