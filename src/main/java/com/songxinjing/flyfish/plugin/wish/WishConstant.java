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
	/**
	+----+----------------------------------+---------------------+--------------------------+------------+----------------------------------+
	| id | accessToken                      | expiryTime          | merchantId               | name       | refreshToken                     |
	+----+----------------------------------+---------------------+--------------------------+------------+----------------------------------+
	|  1 | 7132dda341504ac3ad14c88bfca0683a | 2018-02-14 10:00:00 | 573590288a093d5928dc570e | wish-emaer | 5a3eb3e7f541446e9e6adcff633249ba |
	+----+----------------------------------+---------------------+--------------------------+------------+----------------------------------+
	*/
}
