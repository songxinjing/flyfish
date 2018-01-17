package com.songxinjing.flyfish.plugin.wish.api;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.songxinjing.flyfish.domain.WishVariant;
import com.songxinjing.flyfish.plugin.wish.WishConstant;
import com.songxinjing.flyfish.plugin.wish.exception.WishException;
import com.songxinjing.flyfish.util.HttpUtil;

public class WishProductApi {

	/**
	 * Create a Product
	 */
	public static String add(WishVariant variant, String token)
			throws WishException {
		String url = WishConstant.WISH_API_BASE_URL + "product/add";
		Map<String, String> urlParas = new HashMap<String, String>();
		urlParas.put("name", variant.getProduct().getName());
		urlParas.put("description", variant.getProduct().getDescription());
		urlParas.put("pieces", variant.getPrice());
		urlParas.put("tags", variant.getProduct().getTags());
		urlParas.put("sku", variant.getSku());
	/*	urlParas.put("color", variant);
		urlParas.put("size", parentSku);
		urlParas.put("inventory", parentSku);
		urlParas.put("price", parentSku);
		urlParas.put("shipping", parentSku);
		urlParas.put("msrp", parentSku);
		urlParas.put("shipping_time", parentSku);
		urlParas.put("main_image", parentSku);
		urlParas.put("parent_sku", parentSku);
		urlParas.put("brand", parentSku);
		urlParas.put("landing_page_url", parentSku);
		urlParas.put("upc", parentSku);
		urlParas.put("extra_images", parentSku);
		urlParas.put("max_quantity", parentSku);
		urlParas.put("length", parentSku);
		urlParas.put("width", parentSku);
		urlParas.put("height", parentSku);
		urlParas.put("weight", parentSku);
		urlParas.put("declared_value", parentSku);
		urlParas.put("hscode", parentSku);
		urlParas.put("origin_country", parentSku);
		urlParas.put("has_powder", parentSku);
		urlParas.put("has_liquid", parentSku);
		urlParas.put("has_battery", parentSku);
		urlParas.put("has_metal", parentSku);*/
		urlParas.put("access_token", token);
		url = url + "?" + HttpUtil.getRequestBody(urlParas);
		return HttpUtil.get(url);
	}
	
	/**
	 * Retrieve a Product
	 */
	public static String product(String parentSku, String token)
			throws WishException {
		String url = WishConstant.WISH_API_BASE_URL + "product";
		Map<String, String> urlParas = new HashMap<String, String>();
		urlParas.put("parent_sku", parentSku);
		urlParas.put("access_token", token);
		url = url + "?" + HttpUtil.getRequestBody(urlParas);
		return HttpUtil.get(url);
	}
	
	/**
	 * List all Products
	 */
	public static String multiGet(int star, int limit, Calendar since, boolean showRejected, String token)
			throws WishException {
		String url = WishConstant.WISH_API_BASE_URL + "product/multi-get";
		Map<String, String> urlParas = new HashMap<String, String>();
		urlParas.put("star", star + "");
		urlParas.put("limit", limit + "");
		if (since != null) {
			String str = (new SimpleDateFormat("YYYY-MM-DD")).format(since.getTime());
			urlParas.put("since", str);
		}
		urlParas.put("show_rejected", showRejected + "");
		urlParas.put("access_token", token);
		url = url + "?" + HttpUtil.getRequestBody(urlParas);
		return HttpUtil.get(url);
	}
	
	
	
	

}
