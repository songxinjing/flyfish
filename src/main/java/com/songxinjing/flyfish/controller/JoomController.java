package com.songxinjing.flyfish.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.songxinjing.flyfish.constant.Constant;
import com.songxinjing.flyfish.controller.base.BaseController;
import com.songxinjing.flyfish.domain.JoomProduct;
import com.songxinjing.flyfish.domain.JoomStore;
import com.songxinjing.flyfish.exception.AppException;
import com.songxinjing.flyfish.plugin.joom.JoomConstant;
import com.songxinjing.flyfish.plugin.joom.api.JoomAuthApi;
import com.songxinjing.flyfish.plugin.joom.exception.JoomException;
import com.songxinjing.flyfish.plugin.page.PageModel;
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
	public String storeadd(HttpServletRequest request, Model model, String name, String clientId, String clientSecret)
			throws AppException {
		logger.info("新增Joom店铺授权");
		try {
			String hql = "from JoomStore where name = :name";
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("name", name);
			@SuppressWarnings("unchecked")
			List<JoomStore> list = (List<JoomStore>) joomStoreService.findHql(hql, paraMap);
			if (list != null && !list.isEmpty()) {
				model.addAttribute("errorMsg", "该店铺名称已被使用，请更换名称，便于区分!!!");
				return "system/error";
			}

			hql = "from JoomStore where clientId = :clientId";
			paraMap.clear();
			paraMap = new HashMap<String, Object>();
			paraMap.put("clientId", clientId);
			@SuppressWarnings("unchecked")
			List<JoomStore> list2 = (List<JoomStore>) joomStoreService.findHql(hql, paraMap);
			if (list2 != null && !list2.isEmpty()) {
				model.addAttribute("errorMsg", "该店铺已存在，店铺名称为：" + list2.get(0).getName());
				return "system/error";
			}

			JoomStore store = new JoomStore();
			store.setName(name);
			store.setClientId(clientId);
			store.setClientSecret(clientSecret);
			store.setState(0);
			int id = (Integer) joomStoreService.save(store);
			request.getSession().setAttribute(JoomConstant.SESSION_JOOM_OAUTH_STORE, id);
			String oauthUrl = JoomConstant.JOOM_OAUTH_URL + clientId;
			return "redirect:" + oauthUrl;
		} catch (Exception e) {
			logger.error("系统错误", e);
			throw new AppException();
		}
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
				Timestamp expiryTime = new Timestamp(data.getLong("expiry_time") * 1000);
				String merchantId = data.getString("merchant_user_id");
				String hql = "from JoomStore where merchantId = :merchantId and id != :id";
				Map<String, Object> paraMap = new HashMap<String, Object>();
				paraMap.put("merchantId", merchantId);
				paraMap.put("id", id);
				@SuppressWarnings("unchecked")
				List<JoomStore> list = (List<JoomStore>) joomStoreService.findHql(hql, paraMap);
				if (list != null && !list.isEmpty()) {
					model.addAttribute("errorMsg", "该店铺已存在，店铺名称为：" + list.get(0).getName());
					return "system/error";
				}
				store.setAccessToken(accessToken);
				store.setRefreshToken(refreshToken);
				store.setExpiryTime(expiryTime);
				store.setMerchantId(merchantId);
				joomStoreService.update(store);
			}
		} catch (JoomException e) {
			logger.error("调用Joom Oauth 接口错误", e);
		}

		return storelist(model);
	}

	@RequestMapping(value = "joom/storemodify", method = RequestMethod.POST)
	public String storemodify(HttpServletRequest request, Model model, int id, String name) {
		logger.info("修改Joom店铺");
		String hql = "from JoomStore where name = :name and id != :id";
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("name", name);
		paraMap.put("id", id);
		@SuppressWarnings("unchecked")
		List<JoomStore> list = (List<JoomStore>) joomStoreService.findHql(hql, paraMap);
		if (list != null && !list.isEmpty()) {
			model.addAttribute("errorMsg", "该店铺名称已被使用，请更换名称，便于区分!!!");
			return "system/error";
		}

		JoomStore store = joomStoreService.find(id);
		store.setName(name);
		// 获取用户登录信息
		joomStoreService.update(store);
		return storelist(model);
	}

	@RequestMapping(value = "joom/reauth", method = RequestMethod.GET)
	public String reauth(HttpServletRequest request, Model model, int id) {
		logger.info("重新授权店铺");
		JoomStore store = joomStoreService.find(id);
		request.getSession().setAttribute(JoomConstant.SESSION_JOOM_OAUTH_STORE, id);
		String oauthUrl = JoomConstant.JOOM_OAUTH_URL + store.getClientId();
		return "redirect:" + oauthUrl;
	}

	@RequestMapping(value = "joom/productlist", method = RequestMethod.GET)
	public String productlist(Model model, Integer storeId, Integer page, Integer pageSize) throws AppException {
		logger.info("进入Joom店铺列表页面");
		try {
			if (page == null) {
				page = 1;
			}
			if (pageSize == null) {
				pageSize = Constant.PAGE_SIZE;
			}

			String hql = "select product from JoomProduct as product left join product.store as ps where ps.id = :storeId";
			String countHql = "select count(product.parentSku) from JoomProduct as product left join product.store as ps where ps.id = :storeId";
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("storeId", storeId);

			int total = ((Long) joomProductService.findHql(countHql, paraMap).get(0)).intValue();

			// 分页代码
			PageModel<JoomProduct> pageModel = new PageModel<JoomProduct>();
			pageModel.setPageSize(pageSize);
			pageModel.init(page, total);
			pageModel.setUrl("joom/productlist.html");
			pageModel.setPara("?storeId=" + storeId + "&pageSize=" + pageSize + "&");
			List<JoomProduct> list = joomProductService.findPage(hql, pageModel.getRecFrom(), pageModel.getPageSize(),
					paraMap);
			pageModel.setRecList(list);
			model.addAttribute("pageModel", pageModel);
			model.addAttribute("page", pageModel.getCurrPage());

			JoomStore store = joomStoreService.find(storeId);
			model.addAttribute("store", store);

			return "joom/productlist";
		} catch (Exception e) {
			logger.error("系统错误", e);
			throw new AppException();
		}
	}

	@RequestMapping(value = "joom/sync", method = RequestMethod.GET)
	@ResponseBody
	public boolean sync(Model model, Integer storeId) throws AppException {
		try {
			logger.info("同步");
			JoomStore store = joomStoreService.find(storeId);
			if (store.getState().intValue() == 0) {
				store.setState(1);
				store.setApplyJobTime(new Timestamp(System.currentTimeMillis()));
				joomStoreService.update(store);
			}
			return true;
		} catch (Exception e) {
			logger.error("系统错误", e);
			throw new AppException();
		}
	}

}
