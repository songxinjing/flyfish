package com.songxinjing.flyfish.plugin.joom.api;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.songxinjing.flyfish.domain.WishProduct;
import com.songxinjing.flyfish.domain.WishVariant;
import com.songxinjing.flyfish.plugin.wish.WishConstant;
import com.songxinjing.flyfish.plugin.wish.exception.WishException;
import com.songxinjing.flyfish.util.HttpUtil;

public class JoomProductApi {

	/**
	 * Create a Product
	 */
	public static String add(WishVariant variant, String token) throws WishException {
		try {
			String url = WishConstant.WISH_API_BASE_URL + "product/add";
			Map<String, String> urlParas = new HashMap<String, String>();
			urlParas = productPara(urlParas, variant.getProduct());
			urlParas = variantPara(urlParas, variant);
			urlParas.put("access_token", token);
			url = url + "?" + HttpUtil.getRequestBody(urlParas);
			return HttpUtil.get(url);
		} catch (Exception e) {
			throw new WishException("Create a Product Api Error!", e);
		}
	}

	/**
	 * Retrieve a Product
	 */
	public static String product(String parentSku, String token) throws WishException {
		try {
			String url = WishConstant.WISH_API_BASE_URL + "product";
			Map<String, String> urlParas = new HashMap<String, String>();
			urlParas.put("parent_sku", parentSku);
			urlParas.put("access_token", token);
			url = url + "?" + HttpUtil.getRequestBody(urlParas);
			return HttpUtil.get(url);
		} catch (Exception e) {
			throw new WishException("Retrieve a Product Api Error!", e);
		}
	}

	/**
	 * Update a Product
	 */
	public static String update(WishProduct product, String token) throws WishException {
		try {
			String url = WishConstant.WISH_API_BASE_URL + "product/update";
			Map<String, String> urlParas = new HashMap<String, String>();
			urlParas = productPara(urlParas, product);
			urlParas.put("access_token", token);
			url = url + "?" + HttpUtil.getRequestBody(urlParas);
			return HttpUtil.get(url);
		} catch (Exception e) {
			throw new WishException("Update a Product Api Error!", e);
		}
	}

	/**
	 * List all Products
	 */
	public static String multiGet(int start, int limit, Calendar since, boolean showRejected, String token)
			throws WishException {
		try {
			String url = WishConstant.WISH_API_BASE_URL + "product/multi-get";
			Map<String, String> urlParas = new HashMap<String, String>();
			urlParas.put("start", start + "");
			urlParas.put("limit", limit + "");
			if (since != null) {
				String str = (new SimpleDateFormat("YYYY-MM-DD")).format(since.getTime());
				urlParas.put("since", str);
			}
			urlParas.put("show_rejected", showRejected + "");
			urlParas.put("access_token", token);
			url = url + "?" + HttpUtil.getRequestBody(urlParas);
			return HttpUtil.get(url);
		} catch (Exception e) {
			throw new WishException("List all Products Api Error!", e);
		}
	}

	private static Map<String, String> productPara(Map<String, String> urlParas, WishProduct product) {
		urlParas.put("name", product.getName());
		urlParas.put("description", product.getDescription());
		urlParas.put("tags", product.getTags());
		urlParas.put("main_image", product.getMainImage());
		urlParas.put("parent_sku", product.getParentSku());
		urlParas.put("brand", product.getBrand());
		urlParas.put("landing_page_url", product.getLandingPageUrl());
		urlParas.put("upc", product.getUpc());
		urlParas.put("extra_images", product.getExtraImages());
		urlParas.put("max_quantity", product.getMaxQuantity());
		return urlParas;
	}

	private static Map<String, String> variantPara(Map<String, String> urlParas, WishVariant variant) {
		urlParas.put("pieces", variant.getPrice());
		urlParas.put("sku", variant.getSku());
		urlParas.put("color", variant.getColor());
		urlParas.put("size", variant.getSize());
		urlParas.put("inventory", variant.getInventory());
		urlParas.put("price", variant.getPrice());
		urlParas.put("shipping", variant.getShipping());
		urlParas.put("msrp", variant.getMsrp());
		urlParas.put("shipping_time", variant.getShippingTime());
		urlParas.put("length", variant.getLength());
		urlParas.put("width", variant.getWidth());
		urlParas.put("height", variant.getHeight());
		urlParas.put("weight", variant.getWeight());
		urlParas.put("declared_value", variant.getDeclaredValue());
		urlParas.put("hscode", variant.getHscode());
		urlParas.put("origin_country", variant.getOriginCountry());
		urlParas.put("has_powder", variant.getHasPowder());
		urlParas.put("has_liquid", variant.getHasLiquid());
		urlParas.put("has_battery", variant.getHasBattery());
		urlParas.put("has_metal", variant.getHasMetal());
		return urlParas;
	}
}
