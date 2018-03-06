package com.songxinjing.flyfish.plugin.joom;

public class WishConstant {

	/**
	 * wish Client Id
	 * 
	 */
	public static final String WISH_CLIENT_ID = "5a59e91cf186cf7491239a43";

	/**
	 * wish Client Secret
	 * 
	 */
	public static final String WISH_CLIENT_SECRET = "37ac9c732f114138a57ff93be0c0001a";

	public static final String WISH_OAUTH_URL = "https://sandbox.merchant.wish.com/oauth/authorize?client_id="
			+ WISH_CLIENT_ID;
	
	// 生产： https://china-merchant.wish.com/api/v2/
	// 测试：https://sandbox.merchant.wish.com/v2/
	public static final String WISH_API_BASE_URL = "https://sandbox.merchant.wish.com/api/v2/";	
	
}
