package cn.itcast.core.lucene;

import static org.junit.Assert.*;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class TestIndexSearch {

	@Test
	public void testIndexSearch() throws Exception {
		//1. 创建分词器
		//Analyzer analyzer = new StandardAnalyzer();
		Analyzer analyzer = new IKAnalyzer();
		//2. 创建查询关键字对象, 第一个参数:默认搜索域, 在查询语法中如果指定了从某个域中进行查询, 则从指定的域中进行查询,
		//如果查询语法中没有指定的域名, 则从默认搜索域中进行查询
		QueryParser queryParser = new QueryParser("name", analyzer);
		//3. 创建查询语法对象
		Query query = queryParser.parse("desc:java");
		//4. 指定索引库的位置
		Directory dir = FSDirectory.open(new File("E:\\dic"));
		//5. 创建索引的读取对象
		IndexReader indexReader = IndexReader.open(dir);
		//6. 创建搜索对象
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		//7. 查询并返回结果对象, 第二个参数是每页显示多少条
		TopDocs topDocs = indexSearcher.search(query, 2);
		//8. 打印一共查询到的结果的总数
		System.out.println("====count====" + topDocs.totalHits);
		//9. 从结果对象中获取数据的结果集
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		
		//10.遍历结果集
		if(scoreDocs != null && scoreDocs.length > 0){
			for(ScoreDoc scoreDoc : scoreDocs){
				//获取文档的id, 这个id是在创建文档对象的时候, lucene为每个文档分配的唯一的标识, 文档id, 
				//而不是自己放入的业务级别的id
				int docID = scoreDoc.doc;
				//根据文档id, 读取文档对象
				Document doc = indexReader.document(docID);
				//根据域名获取域值
				System.out.println("====id====" + doc.get("id"));
				System.out.println("====name====" + doc.get("name"));
				System.out.println("====price====" + doc.get("price"));
				System.out.println("====pic====" + doc.get("pic"));
				System.out.println("====desc====" + doc.get("desc"));
				System.out.println("=======================================================");
			}
		}
	}
	
	@Test
	public void testTermQuery() throws Exception {
		//1. 创建分词器
		//Analyzer analyzer = new StandardAnalyzer();
		//Analyzer analyzer = new IKAnalyzer();
		
		//演示termQuery对象
		TermQuery query = new TermQuery(new Term("name", "java"));
		
		//4. 指定索引库的位置
		Directory dir = FSDirectory.open(new File("E:\\dic"));
		//5. 创建索引的读取对象
		IndexReader indexReader = IndexReader.open(dir);
		//6. 创建搜索对象
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		//7. 查询并返回结果对象, 第二个参数是每页显示多少条
		TopDocs topDocs = indexSearcher.search(query, 2);
		//8. 打印一共查询到的结果的总数
		System.out.println("====count====" + topDocs.totalHits);
		//9. 从结果对象中获取数据的结果集
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		
		//10.遍历结果集
		if(scoreDocs != null && scoreDocs.length > 0){
			for(ScoreDoc scoreDoc : scoreDocs){
				//获取文档的id, 这个id是在创建文档对象的时候, lucene为每个文档分配的唯一的标识, 文档id, 
				//而不是自己放入的业务级别的id
				int docID = scoreDoc.doc;
				//根据文档id, 读取文档对象
				Document doc = indexReader.document(docID);
				//根据域名获取域值
				System.out.println("====id====" + doc.get("id"));
				System.out.println("====name====" + doc.get("name"));
				System.out.println("====price====" + doc.get("price"));
				System.out.println("====pic====" + doc.get("pic"));
				System.out.println("====desc====" + doc.get("desc"));
				System.out.println("=======================================================");
			}
		}
	}
	
	/**
	 * 根据数值范围查询
	 * @throws Exception
	 */
	@Test
	public void testNumericRangeQuery() throws Exception {
		//1. 创建分词器
		//Analyzer analyzer = new StandardAnalyzer();
		//Analyzer analyzer = new IKAnalyzer();
		
		//需求: 查询价格在60元以上, 80元以下的书, 包括60元和80元
		//第一个参数:域名, 第二个参数:最小值, 第三个参数:最大值, 第四个参数:是否包含最小值, 第五个参数:是否包含最大值
		NumericRangeQuery<Float> query = NumericRangeQuery.newFloatRange("price", 60f, 80f, true, true);
		
		//4. 指定索引库的位置
		Directory dir = FSDirectory.open(new File("E:\\dic"));
		//5. 创建索引的读取对象
		IndexReader indexReader = IndexReader.open(dir);
		//6. 创建搜索对象
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		//7. 查询并返回结果对象, 第二个参数是每页显示多少条
		TopDocs topDocs = indexSearcher.search(query, 10);
		//8. 打印一共查询到的结果的总数
		System.out.println("====count====" + topDocs.totalHits);
		//9. 从结果对象中获取数据的结果集
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		
		//10.遍历结果集
		if(scoreDocs != null && scoreDocs.length > 0){
			for(ScoreDoc scoreDoc : scoreDocs){
				//获取文档的id, 这个id是在创建文档对象的时候, lucene为每个文档分配的唯一的标识, 文档id, 
				//而不是自己放入的业务级别的id
				int docID = scoreDoc.doc;
				//根据文档id, 读取文档对象
				Document doc = indexReader.document(docID);
				//根据域名获取域值
				System.out.println("====id====" + doc.get("id"));
				System.out.println("====name====" + doc.get("name"));
				System.out.println("====price====" + doc.get("price"));
				System.out.println("====pic====" + doc.get("pic"));
				System.out.println("====desc====" + doc.get("desc"));
				System.out.println("=======================================================");
			}
		}
	}
	
	/**
	 * 组合条件查询
	 * @throws Exception
	 */
	@Test
	public void testBooleanQuery() throws Exception {
		
		
		//需求: 查询价格在60元以上, 80元以下的书, 包括60元和80元
		//第一个参数:域名, 第二个参数:最小值, 第三个参数:最大值, 第四个参数:是否包含最小值, 第五个参数:是否包含最大值
		NumericRangeQuery<Float> rangeQuery = NumericRangeQuery.newFloatRange("price", 60f, 80f, true, true);
		//名称中包含java关键字的
		TermQuery termQuery = new TermQuery(new Term("name", "java"));
		/**
		 * 组合查询
		 * 	MUST 必须 相当于并且  and
		 *  MUST_NOT 不必须, 相当于not 
		 *  SHOULD 应该, 相当于or 或者的意思
		 *  
		 *  注意: 单独一个条件使用MUST_NOT, 或者多个条件组合都是MUST_NOT, 无法查询, 查询不到任何结果
		 */
		BooleanQuery query = new BooleanQuery();
		query.add(rangeQuery, Occur.MUST);
		query.add(termQuery, Occur.MUST);
		
		//4. 指定索引库的位置
		Directory dir = FSDirectory.open(new File("E:\\dic"));
		//5. 创建索引的读取对象
		IndexReader indexReader = IndexReader.open(dir);
		//6. 创建搜索对象
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		//7. 查询并返回结果对象, 第二个参数是每页显示多少条
		TopDocs topDocs = indexSearcher.search(query, 10);
		//8. 打印一共查询到的结果的总数
		System.out.println("====count====" + topDocs.totalHits);
		//9. 从结果对象中获取数据的结果集
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		
		//10.遍历结果集
		if(scoreDocs != null && scoreDocs.length > 0){
			for(ScoreDoc scoreDoc : scoreDocs){
				//获取文档的id, 这个id是在创建文档对象的时候, lucene为每个文档分配的唯一的标识, 文档id, 
				//而不是自己放入的业务级别的id
				int docID = scoreDoc.doc;
				//根据文档id, 读取文档对象
				Document doc = indexReader.document(docID);
				//根据域名获取域值
				System.out.println("====id====" + doc.get("id"));
				System.out.println("====name====" + doc.get("name"));
				System.out.println("====price====" + doc.get("price"));
				System.out.println("====pic====" + doc.get("pic"));
				System.out.println("====desc====" + doc.get("desc"));
				System.out.println("=======================================================");
			}
		}
	}
	
	/**
	 * 从多个域中进行查询
	 * @throws Exception
	 */
	@Test
	public void testMultiFieldQueryParser() throws Exception {
		//1. 创建分词器
		//Analyzer analyzer = new StandardAnalyzer();
		Analyzer analyzer = new IKAnalyzer();
		
		//需求: 名称中, 或者是描述中包含java关键字的查询出来
		String[] fields = {"name","desc"};
		MultiFieldQueryParser queryParser = new MultiFieldQueryParser(fields, analyzer);
		Query query = queryParser.parse("java");
		
		
		//4. 指定索引库的位置
		Directory dir = FSDirectory.open(new File("E:\\dic"));
		//5. 创建索引的读取对象
		IndexReader indexReader = IndexReader.open(dir);
		//6. 创建搜索对象
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		//7. 查询并返回结果对象, 第二个参数是每页显示多少条
		TopDocs topDocs = indexSearcher.search(query, 10);
		//8. 打印一共查询到的结果的总数
		System.out.println("====count====" + topDocs.totalHits);
		//9. 从结果对象中获取数据的结果集
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		
		//10.遍历结果集
		if(scoreDocs != null && scoreDocs.length > 0){
			for(ScoreDoc scoreDoc : scoreDocs){
				//获取文档的id, 这个id是在创建文档对象的时候, lucene为每个文档分配的唯一的标识, 文档id, 
				//而不是自己放入的业务级别的id
				int docID = scoreDoc.doc;
				//根据文档id, 读取文档对象
				Document doc = indexReader.document(docID);
				//根据域名获取域值
				System.out.println("====id====" + doc.get("id"));
				System.out.println("====name====" + doc.get("name"));
				System.out.println("====price====" + doc.get("price"));
				System.out.println("====pic====" + doc.get("pic"));
				System.out.println("====desc====" + doc.get("desc"));
				System.out.println("=======================================================");
			}
		}
	}
}
