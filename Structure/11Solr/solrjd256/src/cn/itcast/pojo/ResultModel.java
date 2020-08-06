package cn.itcast.pojo;

import java.util.List;

public class ResultModel {

	// 商品列表
	private List<ProductModel> productList;
	// 商品总数
	private Long recordCount;
	// 总页数
	private Long pageCount;
	// 当前页
	private long curPage;
	
	
	public List<ProductModel> getProductList() {
		return productList;
	}
	public void setProductList(List<ProductModel> productList) {
		this.productList = productList;
	}
	public Long getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(Long recordCount) {
		this.recordCount = recordCount;
	}
	public Long getPageCount() {
		return pageCount;
	}
	public void setPageCount(Long pageCount) {
		this.pageCount = pageCount;
	}
	public long getCurPage() {
		return curPage;
	}
	public void setCurPage(long curPage) {
		this.curPage = curPage;
	}

		
}
