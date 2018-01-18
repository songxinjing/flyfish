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

import com.songxinjing.flyfish.controller.base.BaseController;
import com.songxinjing.flyfish.domain.WishStore;
import com.songxinjing.flyfish.service.WishProductService;
import com.songxinjing.flyfish.service.WishStoreService;
import com.songxinjing.flyfish.service.WishVariantService;

/**
 * WISH店铺产品控制类
 * 
 * @author songxinjing
 * 
 */
@Controller
public class WishController extends BaseController {

	@Autowired
	WishStoreService wishStoreService;

	@Autowired
	WishProductService wishProductService;

	@Autowired
	WishVariantService wishVariantService;

	@RequestMapping(value = "wish/storelist", method = RequestMethod.GET)
	public String storelist(Model model) {
		logger.info("进入Wish店铺列表页面");

		List<WishStore> stores = wishStoreService.find();

		model.addAttribute("stores", stores);

		return "wish/storelist";
	}

	@RequestMapping(value = "wish/storemodify", method = RequestMethod.POST)
	public String storemodify(HttpServletRequest request, Model model, int id, String name) {
		logger.info("修改虚拟店铺");
		WishStore store = wishStoreService.find(id);
		store.setName(name);
		// 获取用户登录信息
		wishStoreService.update(store);
		return storelist(model);
	}

	@RequestMapping(value = "wish/productlist", method = RequestMethod.GET)
	public String productlist(Model model, Integer storeId) {
		logger.info("进入Wish店铺列表页面");

		WishStore store = wishStoreService.find(storeId);

		model.addAttribute("store", store);

		return "wish/productlist";
	}

	@RequestMapping(value = "wish/sync", method = RequestMethod.GET)
	@ResponseBody
	public boolean sync(Model model, Integer storeId) {
		logger.info("同步");
		WishStore store = wishStoreService.find(storeId);
		if(store.getState().intValue() == 0){
			store.setState(1);
			store.setApplyJobTime(new Timestamp(System.currentTimeMillis()));
			wishStoreService.update(store);
		}
		return true;
	}

}
