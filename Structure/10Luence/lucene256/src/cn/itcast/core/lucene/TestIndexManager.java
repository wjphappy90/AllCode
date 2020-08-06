package cn.itcast.core.lucene;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.FloatField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import cn.itcast.core.dao.BookDao;
import cn.itcast.core.dao.BookDaoImpl;
import cn.itcast.core.pojo.Book;

/**
 * 演示lucene对索引库的维护(增删改)
 * @author ZJ
 *
 */
public class TestIndexManager {

	@Test
	public void testIndexAndDocumentCreate() throws Exception {
		List<Document> docList = new ArrayList<Document>();
		//1. 获取数据
		BookDao bookDao  = new BookDaoImpl();
		List<Book> bookList = bookDao.queryBookList();
		//2. 创建索引和文档
		for(Book book : bookList){
			//创建文档对象
			Document doc = new Document();
			
			Integer id = book.getId();
			String name = book.getName();
			Float price = book.getPrice();
			String pic = book.getPic();
			String desc = book.getDesc();
			
			//创建域对象: 第一个参数是域名, 第二个参数是域值, 第三个参数是是否存储, 存储yes, 不存储no
//			Field idField = new TextField("id", String.valueOf(id), Store.YES);
//			Field nameField = new TextField("name", name, Store.YES);
//			Field priceField = new TextField("price", String.valueOf(price), Store.YES);
//			Field picField = new TextField("pic", pic, Store.YES);
//			Field descField = new TextField("desc", desc, Store.YES);
			
			/**
			 * 是否分词: 否, 因为id是一个整体, 分词后无意义
			 * 是否索引: 是, 因为需要根据id查询
			 * 是否存储: 是, 因为id需要后期维护数据的时候使用, 很重要, 而id又不会占用大量磁盘空间, 所以存储
			 */
			Field idField = new StringField("id", String.valueOf(id), Store.YES);
			
			/**
			 * 是否分词: 是, 因为需要根据名称查询,并且名称分词后有意义
			 * 是否索引: 是, 因为需要根据名称查询
			 * 是否存储: 是, 因为查询结果需要显示名称
			 */
			Field nameField = new TextField("name", name, Store.YES);
			
			/**
			 * 是否分词: 是, 因为底层算法规定
			 * 是否索引: 是, 因为需要根据价格区间进行查询
			 * 是否存储: 是, 因为页面需要显示价格
			 */
			Field priceField = new FloatField("price", price, Store.YES);
			
			/**
			 * 是否分词: 否, 因为不需要根据图片查询
			 * 是否索引: 否, 因为不需要查询图片
			 * 是否存储: 是, 因为页面需要展示图片
			 */
			Field picField = new StoredField("pic", pic);
			
			/**
			 * 是否分词: 是, 因为需要根据描述查询, 并且描述分词后有意义
			 * 是否索引: 是, 因为需要根据描述查询
			 * 是否存储: 否, 因为描述数据量太大, 会占用大量磁盘空间, 而结果中又不需要展示出来, 所以不存储
			 */
			Field descField = new TextField("desc", desc, Store.NO);
			
			
			//人为影响相关度排序
			if(id == 4){
				//加权重
				descField.setBoost(100000);
			}
			
			//将域对象放入文档对象中
			doc.add(idField);
			doc.add(nameField);
			doc.add(priceField);
			doc.add(picField);
			doc.add(descField);
			
			
			
			//将文档放到文档集合中
			docList.add(doc);
		}
		
		//3. 创建分词器,StandardAnalyzer标准分词器: 标准分词器对英文分词效果很好, 对中文是单字分词, 也就是一个字就是一个词
		//Analyzer analyzer = new StandardAnalyzer();
		Analyzer analyzer = new IKAnalyzer();
		//4. 指定索引库位置,RAMDirectory内存中, FSDirectory硬盘上(file system directory)
		Directory dir = FSDirectory.open(new File("E:\\dic"));
		//5. 创建索引和文档写的初始化对象, 第一个参数是当前使用的lucene的版本号
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
		//6. 创建索引和文档的写对象
		IndexWriter indexWriter = new IndexWriter(dir, config);
		
		//遍历文档结合
		for(Document doc : docList){
			//将文档对象加入到流对象中
			indexWriter.addDocument(doc);
		}
		//提交
		indexWriter.commit();
		//关闭
		indexWriter.close();
	}
	
	/**
	 * 测试索引库数据修改
	 * 修改就是根据条件去查询, 将原有的数据删除, 将新的数据添加进去
	 * @throws Exception
	 */
	@Test
	public void testIndexAndDocumentUpdate() throws Exception {
		
		//3. 创建分词器,StandardAnalyzer标准分词器: 标准分词器对英文分词效果很好, 对中文是单字分词, 也就是一个字就是一个词
		//Analyzer analyzer = new StandardAnalyzer();
		Analyzer analyzer = new IKAnalyzer();
		//4. 指定索引库位置,RAMDirectory内存中, FSDirectory硬盘上(file system directory)
		Directory dir = FSDirectory.open(new File("E:\\dic"));
		//5. 创建索引和文档写的初始化对象, 第一个参数是当前使用的lucene的版本号
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
		//6. 创建索引和文档的写对象
		IndexWriter indexWriter = new IndexWriter(dir, config);
		
		Document doc = new Document();
		Field idField = new StringField("id", "2", Store.YES);
		Field nameField = new TextField("name", "xxxx", Store.YES);
		Field priceField = new FloatField("price", 12.5f, Store.YES);
		Field picField = new StoredField("pic", "xxxx.jpg");
		Field descField = new TextField("desc", "adsfasdfsadfsadf", Store.NO);
		
		//将域对象放入文档对象中
		doc.add(idField);
		doc.add(nameField);
		doc.add(priceField);
		doc.add(picField);
		doc.add(descField);
		//修改: 第一个参数是修改的条件, 第二个参数是修改成的内容
		indexWriter.updateDocument(new Term("id", "2"), doc);
		//提交
		indexWriter.commit();
		//关闭
		indexWriter.close();
	}
	
	/**
	 * 测试删除
	 * @throws Exception
	 */
	@Test
	public void testIndexAndDocumentDelete() throws Exception {
		
		//3. 创建分词器,StandardAnalyzer标准分词器: 标准分词器对英文分词效果很好, 对中文是单字分词, 也就是一个字就是一个词
		//Analyzer analyzer = new StandardAnalyzer();
		Analyzer analyzer = new IKAnalyzer();
		//4. 指定索引库位置,RAMDirectory内存中, FSDirectory硬盘上(file system directory)
		Directory dir = FSDirectory.open(new File("E:\\dic"));
		//5. 创建索引和文档写的初始化对象, 第一个参数是当前使用的lucene的版本号
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
		//6. 创建索引和文档的写对象
		IndexWriter indexWriter = new IndexWriter(dir, config);
		
		//根据查询条件删除
		//indexWriter.deleteDocuments(new Term("id", "3"));
		//删除所有
		indexWriter.deleteAll();
		
		//提交
		indexWriter.commit();
		//关闭
		indexWriter.close();
	}
}
