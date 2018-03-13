package com.songxinjing.flyfish.controller;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.songxinjing.flyfish.controller.base.BaseController;
import com.songxinjing.flyfish.domain.JoomStore;
import com.songxinjing.flyfish.plugin.joom.JoomConstant;
import com.songxinjing.flyfish.plugin.joom.api.JoomAuthApi;
import com.songxinjing.flyfish.plugin.joom.exception.JoomException;
import com.songxinjing.flyfish.service.JoomProductService;
import com.songxinjing.flyfish.service.JoomStoreService;
import com.songxinjing.flyfish.service.JoomVariantService;

/**
 * Joom店铺产品控制类
 * 
 * @author songxinjing
 * 
 */
@Controller
public class JoomController extends BaseController {

	@Autowired
	JoomStoreService joomStoreService;

	@Autowired
	JoomProductService joomProductService;

	@Autowired
	JoomVariantService joomVariantService;

	@RequestMapping(value = "joom/storelist", method = RequestMethod.GET)
	public String storelist(Model model) {
		logger.info("进入Joom店铺列表页面");

		List<JoomStore> stores = joomStoreService.find();

		model.addAttribute("stores", stores);

		return "joom/storelist";
	}

	@RequestMapping(value = "joom/storeadd", method = RequestMethod.POST)
	public String storeadd(HttpServletRequest request, Model model, String name, String clientId, String clientSecret) {
		logger.info("新增Joom店铺授权");
		JoomStore store = new JoomStore();
		store.setName(name);
		store.setClientId(clientId);
		store.setClientSecret(clientSecret);
		// 获取用户登录信息
		int id = (Integer) joomStoreService.save(store);
		request.getSession().setAttribute(JoomConstant.SESSION_JOOM_OAUTH_STORE, id);
		String oauthUrl = JoomConstant.JOOM_OAUTH_URL + clientId;
		return "redirect:" + oauthUrl;
	}

	@RequestMapping(value = "joom", method = RequestMethod.GET)
	public String storelist(HttpServletRequest request, Model model, String code) {
		logger.info("认证回调页面");

		int id = (Integer) request.getSession().getAttribute(JoomConstant.SESSION_JOOM_OAUTH_STORE);
		JoomStore store = joomStoreService.find(id);

		try {
			String result = JoomAuthApi.oauth(store.getClientId(), store.getClientSecret(), code);
			JSONObject json = JSON.parseObject(result);
			int iCode = json.getIntValue("code");
			if (iCode == 0) {
				JSONObject data = json.getJSONObject("data");
				String accessToken = data.getString("access_token");
				String refreshToken = data.getString("refresh_token");
				Timestamp expiryTime = new Timestamp(data.getLong("expiry_time"));
				store.setAccessToken(accessToken);
				store.setRefreshToken(refreshToken);
				store.setExpiryTime(expiryTime);
				joomStoreService.update(store);
			}
		} catch (JoomException e) {
			logger.error("调用Joom Oauth 接口错误", e);
		}

		return storelist(model);
	}

	@RequestMapping(value = "joom/storemodify", method = RequestMethod.POST)
	public String storemodify(HttpServletRequest request, Model model, int id, String name) {
		logger.info("修改虚拟店铺");
		JoomStore store = joomStoreService.find(id);
		store.setName(name);
		// 获取用户登录信息
		joomStoreService.update(store);
		return storelist(model);
	}

	@RequestMapping(value = "joom/productlist", method = RequestMethod.GET)
	public String productlist(Model model, Integer storeId) {
		logger.info("进入Joom店铺列表页面");

		JoomStore store = joomStoreService.find(storeId);

		model.addAttribute("store", store);

		return "joom/productlist";
	}

	@RequestMapping(value = "joom/sync", method = RequestMethod.GET)
	@ResponseBody
	public boolean sync(Model model, Integer storeId) {
		logger.info("同步");
		JoomStore store = joomStoreService.find(storeId);
		if (store.getState().intValue() == 0) {
			store.setState(1);
			store.setApplyJobTime(new Timestamp(System.currentTimeMillis()));
			joomStoreService.update(store);
		}
		return true;
	}

}
