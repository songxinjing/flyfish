package com.songxinjing.flyfish.plugin.joom.api;

import java.util.HashMap;
import java.util.Map;

import com.songxinjing.flyfish.plugin.joom.JoomConstant;
import com.songxinjing.flyfish.plugin.joom.exception.JoomException;
import com.songxinjing.flyfish.util.HttpUtil;

public class JoomAuthApi {
	
	public static String oauth(String clientId, String clientSecret, String code) throws JoomException {
		try {
			String url = JoomConstant.JOOM_TOKEN_URL;
			Map<String, String> bodyParas = new HashMap<String, String>();
			bodyParas.put("client_id", clientId);
			bodyParas.put("client_secret", clientSecret);
			bodyParas.put("code", code);
			bodyParas.put("grant_type", "authorization_code");
			bodyParas.put("redirect_uri", JoomConstant.REDIRECT_URI);
			String body = HttpUtil.getRequestBody(bodyParas);
			return HttpUtil.post(url, body);
		} catch (Exception e) {
			throw new JoomException("Joom Oauth Api Error!", e);
		}
	}

}
