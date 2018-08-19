package com.songxinjing.flyfish.constant;

public class Constant {

	// -------------------------- session --------------------
	/**
	 * 登录用户信息在Session中的Key
	 */
	public static final String SESSION_LOGIN_USER = "session_login_user";

	/**
	 * 商品查询条件
	 */
	public static final String SESSION_GOODS_QUERY = "session_goods_query";

	/**
	 * 店铺信息
	 */
	public static final String SESSION_STORES = "session_stores";

	// -------------------------- 参数 ------------------------

	/**
	 * 分页组件：每页显示数目
	 */
	public static final int PAGE_SIZE = 50;

	/**
	 * 汇率代码：美元／人民币
	 */
	public static final String USD_CNY = "USD/CNY";

	// -------------------------- 平台名称 --------------------

	/**
	 * 平台名称：Ebay
	 */
	public static final String Ebay = "Ebay";

	/**
	 * 平台名称：Amazon
	 */
	public static final String Amazon = "Amazon";

	/**
	 * 平台名称：AliExpress
	 */
	public static final String AliExpress = "AliExpress";

	/**
	 * 平台名称：Wish
	 */
	public static final String Wish = "Wish";

	/**
	 * 平台名称：Joom
	 */
	public static final String Joom = "Joom";
	
	/**
	 * 平台名称：Shopee
	 */
	public static final String Shopee = "Shopee";
	
	/**
	 * 平台名称：Commexp
	 */
	public static final String Commexp = "通用";
	
	// -------------------------- 缓存 ------------------------
	
	/**
	 * 缓存：大类名称下拉单
	 */
	public static final String CACHE_bigCataNames = "bigCataNames";
	
	/**
	 * 缓存：小类名称下拉单
	 */
	public static final String CACHE_smallCataNames = "smallCataNames";
	
	/**
	 * 缓存：业绩归属人1下拉单
	 */
	public static final String CACHE_bussOwner1s = "bussOwner1s";
	
	/**
	 * 缓存：业绩归属人2下拉单
	 */
	public static final String CACHE_bussOwner2s = "bussOwner2s";
	
	/**
	 * 缓存：采购员下拉单
	 */
	public static final String CACHE_buyers = "buyers";
	
}
