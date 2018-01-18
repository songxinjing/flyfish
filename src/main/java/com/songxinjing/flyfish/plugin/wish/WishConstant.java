package com.songxinjing.flyfish.plugin.wish;

public class WishConstant {

	/**
	 * wish Client Id
	 * 
	 */
	public static final String WISH_CLIENT_ID = "5a6016eddb0e427a13ba0aff";

	/**
	 * wish Client Secret
	 * 
	 */
	public static final String WISH_CLIENT_SECRET = "a737034b581340ca8e78f037d7a0bf5a";

	public static final String WISH_OAUTH_URL = "https://merchant.wish.com/oauth/authorize?client_id="
			+ WISH_CLIENT_ID;
	
	// 生产： https://china-merchant.wish.com/api/v2/
	// 测试：https://sandbox.merchant.wish.com/v2/
	public static final String WISH_API_BASE_URL = "https://china-merchant.wish.com/api/v2/";	
	
}
