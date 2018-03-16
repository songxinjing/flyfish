package com.songxinjing.flyfish.plugin.joom;

public class JoomConstant {
	
	public static final String JOOM_API_BASE_URL = "https://api-merchant.joom.com/api/v2/";	

	public static final String JOOM_OAUTH_URL = JOOM_API_BASE_URL + "oauth/authorize?client_id=";	
	
	public static final String JOOM_TOKEN_URL = JOOM_API_BASE_URL + "oauth/access_token";	
	
	public static final String REDIRECT_URI = "https://www.flyfishs.com/flyfish/joom.html";
	
	public static final String SESSION_JOOM_OAUTH_STORE = "session_joom_oauth_store";
	
	
	
}
