package cn.itcast.core.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.itcast.core.pojo.BuyerCart;
import cn.itcast.core.pojo.BuyerItems;
import cn.itcast.core.pojo.ad.Ad;
import cn.itcast.core.pojo.product.Sku;

public class TestJson {

	@Test
	public void testObjectToJson() throws Exception {
		List<Ad> adList = new ArrayList<Ad>();
		Ad ad1 = new Ad();
		ad1.setId(001L);
		ad1.setTitle("玄武");
		
		Ad ad2  = new Ad();
		ad2.setId(002L);
		ad2.setTitle("朱雀");
		
		adList.add(ad1);
		adList.add(ad2);
		
		//创建转换对象
		ObjectMapper om = new ObjectMapper();
		//设置属性中的空值不参与转换
		om.setSerializationInclusion(Include.NON_NULL);
		String str = om.writeValueAsString(adList);
		System.out.println("==========" + str);
		
	}
	
	@Test
	public void testJsonStrToObject() throws Exception {
		BuyerCart buyerCart = new BuyerCart();
		BuyerItems item = new BuyerItems();
		item.setAmount(10);
		Sku sku = new Sku();
		sku.setId(001L);
		item.setSku(sku);
		buyerCart.addItems(item);
		
		ObjectMapper om = new ObjectMapper();
		//设置属性中的空值不参与转换
		om.setSerializationInclusion(Include.NON_NULL);
		//将对象装换成json格式字符串
		String str = om.writeValueAsString(buyerCart);
		System.out.println("==========" + str);
		
		//将json格式字符串转换成购物车对象
		BuyerCart buyerCart1 = om.readValue(str, BuyerCart.class);
		System.out.println("======" + buyerCart1);
	}
}
