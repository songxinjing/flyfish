package com.songxinjing.flyfish.plugin.joom.api;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.songxinjing.flyfish.domain.JoomProduct;
import com.songxinjing.flyfish.domain.JoomVariant;
import com.songxinjing.flyfish.plugin.joom.JoomConstant;
import com.songxinjing.flyfish.plugin.joom.exception.JoomException;
import com.songxinjing.flyfish.util.HttpUtil;

public class JoomProductApi {

	/**
	 * Create a Product
	 */
	public static String add(JoomVariant variant, String token) throws JoomException {
		try {
			String url = JoomConstant.JOOM_API_BASE_URL + "product/add";
			Map<String, String> urlParas = new HashMap<String, String>();
			urlParas = productPara(urlParas, variant.getProduct());
			urlParas = variantPara(urlParas, variant);
			urlParas.put("access_token", token);
			url = url + "?" + HttpUtil.getRequestBody(urlParas);
			return HttpUtil.get(url);
		} catch (Exception e) {
			throw new JoomException("Create a Product Api Error!", e);
		}
	}

	/**
	 * Retrieve a Product
	 */
	public static String product(String parentSku, String token) throws JoomException {
		try {
			String url = JoomConstant.JOOM_API_BASE_URL + "product";
			Map<String, String> urlParas = new HashMap<String, String>();
			urlParas.put("parent_sku", parentSku);
			urlParas.put("access_token", token);
			url = url + "?" + HttpUtil.getRequestBody(urlParas);
			return HttpUtil.get(url);
		} catch (Exception e) {
			throw new JoomException("Retrieve a Product Api Error!", e);
		}
	}

	/**
	 * Update a Product
	 */
	public static String update(JoomProduct product, String token) throws JoomException {
		try {
			String url = JoomConstant.JOOM_API_BASE_URL + "product/update";
			Map<String, String> urlParas = new HashMap<String, String>();
			urlParas = productPara(urlParas, product);
			urlParas.put("access_token", token);
			url = url + "?" + HttpUtil.getRequestBody(urlParas);
			return HttpUtil.get(url);
		} catch (Exception e) {
			throw new JoomException("Update a Product Api Error!", e);
		}
	}

	/**
	 * List all Products
	 */
	public static String multiGet(int start, int limit, Calendar since, String token) throws JoomException {
		try {
			String url = JoomConstant.JOOM_API_BASE_URL + "product/multi-get";
			Map<String, String> urlParas = new HashMap<String, String>();
			urlParas.put("start", start + "");
			urlParas.put("limit", limit + "");
			if (since != null) {
				String str = (new SimpleDateFormat("YYYY-MM-DD")).format(since.getTime());
				urlParas.put("since", str);
			}
			urlParas.put("access_token", token);
			url = url + "?" + HttpUtil.getRequestBody(urlParas);
			return HttpUtil.get(url);
		} catch (Exception e) {
			throw new JoomException("List all Products Api Error!", e);
		}
	}

	private static Map<String, String> productPara(Map<String, String> urlParas, JoomProduct product) {
		urlParas.put("name", product.getName());
		urlParas.put("description", product.getDescription());
		urlParas.put("tags", product.getTags());
		urlParas.put("main_image", product.getMainImage());
		urlParas.put("parent_sku", product.getParentSku());
		urlParas.put("extra_images", product.getExtraImages());
		return urlParas;
	}

	private static Map<String, String> variantPara(Map<String, String> urlParas, JoomVariant variant) {
		urlParas.put("pieces", variant.getPrice());
		urlParas.put("sku", variant.getSku());
		urlParas.put("color", variant.getColor());
		urlParas.put("size", variant.getSize());
		urlParas.put("inventory", variant.getInventory());
		urlParas.put("price", variant.getPrice());
		urlParas.put("shipping", variant.getShipping());
		urlParas.put("shipping_time", variant.getShippingTime());
		return urlParas;
	}
}
