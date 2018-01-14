package com.songxinjing.flyfish.plugin.wish.api;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.songxinjing.flyfish.plugin.wish.WishConstant;
import com.songxinjing.flyfish.plugin.wish.exception.WishException;
import com.songxinjing.flyfish.util.HttpUtil;

public class WishProductApi {

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
