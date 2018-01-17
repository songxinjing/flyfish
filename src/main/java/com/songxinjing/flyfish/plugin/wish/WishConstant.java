package com.songxinjing.flyfish.plugin.wish;

public class WishConstant {

	/**
	 * wish Client Id
	 * 
	 */
	public static final String WISH_CLIENT_ID = "5a5aea7a6d2c6b26b89e82fa";

	/**
	 * wish Client Secret
	 * 
	 */
	public static final String WISH_CLIENT_SECRET = "4b7275ca4c2a4d7f80032cfde2dcd709";

	public static final String WISH_OAUTH_URL = "https://merchant.wish.com/oauth/authorize?client_id="
			+ WISH_CLIENT_ID;
	
	// 生产： https://china-merchant.wish.com/api/v2/
	// 测试：https://sandbox.merchant.wish.com/v2/
	public static final String WISH_API_BASE_URL = "https://china-merchant.wish.com/api/v2/";	
	
}
