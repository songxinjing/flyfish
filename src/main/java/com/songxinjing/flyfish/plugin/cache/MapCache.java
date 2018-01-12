package com.songxinjing.flyfish.plugin.cache;

import java.util.HashMap;
import java.util.Map;

public class MapCache {

	/**
	 * 对象存储map
	 */
	private static Map<String, Object> mapCache = new HashMap<String, Object>();

	public static void addUpdate(String key, Object value) {
		mapCache.put(key, value);
	}

	public static Object get(String key) {
		return mapCache.get(key);
	}

	public static void remove(String key) {
		mapCache.remove(key);
	}

	public static void clear() {
		mapCache.clear();
	}

}
