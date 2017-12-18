package com.songxinjing.flyfish.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.songxinjing.flyfish.controller.base.BaseController;
import com.songxinjing.flyfish.domain.Platform;
import com.songxinjing.flyfish.service.LogisProdService;
import com.songxinjing.flyfish.service.PlatformService;

/**
 * 平台管理控制类
 * 
 * @author songxinjing
 * 
 */
@Controller
public class PlatformController extends BaseController {

	@Autowired
	PlatformService platformService;
	
	@Autowired
	LogisProdService logisProdService;

	@RequestMapping(value = "platform/list", method = RequestMethod.GET)
	public String prodList(Model model) {
		logger.info("进入平台列表页面");
		List<Platform> recList = platformService.find();
		model.addAttribute("recList", recList);
		model.addAttribute("prods", logisProdService.find());
		return "platform/list";
	}

	@RequestMapping(value = "platform/add", method = RequestMethod.POST)
	@ResponseBody
	public boolean prodAdd(Platform form) {
		logger.info("新增平台");
		int id = (Integer) platformService.save(form);
		Platform platform = platformService.find(id);
		platform.setOrderNum(id);
		platformService.update(platform);
		return true;
	}

	@RequestMapping(value = "platform/modify", method = RequestMethod.POST)
	@ResponseBody
	public boolean prodModify(Platform form) {
		logger.info("修改平台");
		Platform platform = platformService.find(form.getId());
		platform.setName(form.getName());
		platform.setRate(form.getRate());
		platform.setProfitRate(form.getProfitRate());
		platform.setCutRate(form.getCutRate());
		platform.setWeightStrategy(form.getWeightStrategy());
		platform.setProdStrategy(logisProdService.find(form.getProdStrategy().getId()));
		platformService.update(platform);
		return true;
	}

	@RequestMapping(value = "platform/delete", method = RequestMethod.GET)
	public String delete(Integer id) {
		logger.info("删除平台");
		platformService.delete(id);
		return "redirect:/platform/list.html";
	}

}
