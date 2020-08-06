package cn.itcast.core.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 购物车对象
 * @author ZJ
 *
 */
public class BuyerCart implements Serializable{

	private static final long serialVersionUID = -9002550667971908591L;
	
	//购物项集合
	private List<BuyerItems> items = new ArrayList<BuyerItems>();
	
	
	//商品总价
	//因为购物车对象需要转换成json格式字符串保存到cookie中, 又需要将json格式字符串转换成对象, 所以用了ObjectMapper这个类.
	//如果使用ObjectMapper来进行转换, 要求pojo对象必须是严格的pojo, 也就是必须有完整的属性和get, set方法, 如果没有则会报错, 无法转换.
	//@JsonIgnore注解可以在json转换的时候不参与转换, 所以加上它就可以不报错
	@JsonIgnore
	public Float getProductPrice() {
		Float total = 0f;
		for(BuyerItems item : items){
			total += item.getAmount() * item.getSku().getPrice();
		}
		return total;
	}
	//运费
	@JsonIgnore
	public Float getFee() {
		if(getProductPrice() > 90){
			return 0f;
		}
		return 10f;
	}
	//商品总数
	@JsonIgnore
	public Integer getProductAmount() {
		Integer count = 0;
		for(BuyerItems item : items){
			count += item.getAmount();
		}
		return count;
	}
	//总价格
	@JsonIgnore
	public Float getTotalPrice() {
		return getProductPrice() + getFee();
	}


	public List<BuyerItems> getItems() {
		return items;
	}

	/**
	 * 每次加入购物车操作, 是将一个购物项添加到当前的购物项集合中.
	 * @param items
	 */
	public void addItems(BuyerItems item) {
		//判断item是否在items集合中
		if(items.contains(item)){
			for(BuyerItems it : items){
				//集合中有同款商品数量相加
				if(it.getSku().getId().equals(item.getSku().getId())){
					it.setAmount(it.getAmount() + item.getAmount());
				}
			}
		} else {
			this.items.add(item);
		}
	}
	
	
}
