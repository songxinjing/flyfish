package com.songxinjing.flyfish.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.songxinjing.flyfish.constant.Constant;
import com.songxinjing.flyfish.controller.base.BaseController;
import com.songxinjing.flyfish.domain.Store;
import com.songxinjing.flyfish.service.DomainService;
import com.songxinjing.flyfish.service.PlatformService;
import com.songxinjing.flyfish.service.StoreService;

/**
 * 权重管理控制类
 * 
 * @author songxinjing
 * 
 */
@Controller
public class StoreController extends BaseController {

	@Autowired
	PlatformService platformService;

	@Autowired
	DomainService domainService;

	@Autowired
	StoreService storeService;

	@RequestMapping(value = "store/list", method = RequestMethod.GET)
	public String list(Model model, Integer platId) {
		logger.info("进入列表页面");

		if (platId == null || platId == 0) {
			platId = 1;
		}

		List<Store> stores = platformService.find(platId).getStores();

		model.addAttribute("stores", stores);

		model.addAttribute("platforms", platformService.find());
		
		model.addAttribute("domains", domainService.find());

		model.addAttribute("platId", platId);

		return "store/list";
	}

	@RequestMapping(value = "store/add", method = RequestMethod.POST)
	public String add(HttpServletRequest request, Model model, int platformId, String name, String domainName) {
		Store store = new Store();
		store.setPlatform(platformService.find(platformId));
		store.setName(name);
		store.setDomainName(domainName);
		store.setMove(storeService.getNextMove(platformId));
		// 获取用户登录信息
		storeService.save(store);
		request.getSession().setAttribute(Constant.SESSION_STORES,storeService.find());
		return list(model, platformId);
	}

	@RequestMapping(value = "store/modify", method = RequestMethod.POST)
	public String modify(HttpServletRequest request, Model model, int id, String name,
			String domainName) {
		Store store = storeService.find(id);
		store.setName(name);
		store.setDomainName(domainName);
		// 获取用户登录信息
		storeService.update(store);
		request.getSession().setAttribute(Constant.SESSION_STORES,storeService.find());
		return list(model, store.getPlatform().getId());
	}

	@RequestMapping(value = "store/del", method = RequestMethod.GET)
	public String del(Model model, int id) {
		Store store = storeService.find(id);
		int platId = store.getPlatform().getId();
		storeService.delete(store);
		return list(model, platId);
	}

}
