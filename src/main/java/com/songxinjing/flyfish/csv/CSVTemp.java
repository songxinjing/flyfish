package com.songxinjing.flyfish.csv;

import java.util.LinkedHashMap;
import java.util.Map;

public class CSVTemp {
	
	
	/**
	 * WISH模版文件
	 */
	public static final String WISH = "/excelTemp/wish.csv";
	
	/**
	 * JOOM模版文件
	 */
	public static final String JOOM = "/excelTemp/joom.csv";
	
	/**
	 * WISH模版字段映射
	 */
	public static final Map<String,String> WISH_FIELD = new LinkedHashMap<String,String>();
	
	static {
		WISH_FIELD.put("Parent Unique ID", "parentSku");
		WISH_FIELD.put("*Product Name", "name");
		WISH_FIELD.put("Description", "descp");
		WISH_FIELD.put("*Tags", "tags");
		WISH_FIELD.put("*Unique ID", "sku");
		WISH_FIELD.put("Color", "color");
		WISH_FIELD.put("Size", "size");
		WISH_FIELD.put("*Quantity", "quantity");
		WISH_FIELD.put("*Price", "price");
		WISH_FIELD.put("*Shipping", "shipPrice");
		WISH_FIELD.put("Shipping Time(enter without \" \", just the estimated days )", "shipDays");
		WISH_FIELD.put("*Product Main Image URL", "mainImgUrl");
		WISH_FIELD.put("Variant Main Image URL", "vMainImgUrl");
		WISH_FIELD.put("Extra Image URL", "eImgUrl");
		WISH_FIELD.put("Extra Image URL 1", "eImgUrl1");
		WISH_FIELD.put("Extra Image URL 2", "eImgUrl2");
		WISH_FIELD.put("Extra Image URL 3", "eImgUrl3");
		WISH_FIELD.put("Extra Image URL 4", "eImgUrl4");
		WISH_FIELD.put("Extra Image URL 5", "eImgUrl5");
		WISH_FIELD.put("Extra Image URL 6", "eImgUrl6");
		WISH_FIELD.put("Extra Image URL 7", "eImgUrl7");
		WISH_FIELD.put("Extra Image URL 8", "eImgUrl8");
		WISH_FIELD.put("Extra Image URL 9", "eImgUrl9");
		WISH_FIELD.put("Extra Image URL 10", "eImgUrl10");
	}
	
	/**
	 * JOOM模版字段映射
	 */
	public static final Map<String,String> JOOM_FIELD = new LinkedHashMap<String,String>();
	
	static {
		JOOM_FIELD.put("Parent SKU", "parentSku");
		JOOM_FIELD.put("SKU", "sku");
		JOOM_FIELD.put("product name", "name");
		JOOM_FIELD.put("description", "descp");
		JOOM_FIELD.put("tags", "tags");
		JOOM_FIELD.put("msrp", "msrp");
		JOOM_FIELD.put("color", "color");
		JOOM_FIELD.put("size", "size");
		JOOM_FIELD.put("price", "price");
		JOOM_FIELD.put("shipping price", "shipPrice");
		JOOM_FIELD.put("inventory", "inventory");
		JOOM_FIELD.put("shipping days", "shipDays");
		JOOM_FIELD.put("product main image URL", "mainImgUrl");
		JOOM_FIELD.put("variant main image URL", "vMainImgUrl");
		JOOM_FIELD.put("extra image URL 1", "eImgUrl");
		JOOM_FIELD.put("extra image URL 2", "eImgUrl1");
		JOOM_FIELD.put("extra image URL 3", "eImgUrl2");
		JOOM_FIELD.put("extra image URL 4", "eImgUrl3");
		JOOM_FIELD.put("extra image URL 5", "eImgUrl4");
		JOOM_FIELD.put("extra image URL 6", "eImgUrl5");
		JOOM_FIELD.put("extra image URL 7", "eImgUrl6");
		JOOM_FIELD.put("extra image URL 8", "eImgUrl7");
		JOOM_FIELD.put("extra image URL 9", "eImgUrl8");
	}
	
	

}
