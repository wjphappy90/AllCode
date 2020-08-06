package cn.itcast.core.dao;

import java.util.List;

import cn.itcast.core.pojo.Book;

public interface BookDao {
	
	/**
	 * 查询所有的book数据
	 * 
	 * @return
	 */
	public List<Book> queryBookList();

}
