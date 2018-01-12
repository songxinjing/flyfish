package com.songxinjing.flyfish.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.songxinjing.flyfish.controller.base.BaseController;
import com.songxinjing.flyfish.service.QuartzService;

/**
 * 主页控制类
 * 
 * @author songxinjing
 * 
 */
@Controller
public class CacheController extends BaseController {

	@Autowired
	QuartzService quartzService;

	@RequestMapping(value = "system/cache", method = RequestMethod.GET)
	public String cache() {
		logger.info("进入缓存管理页面");
		return "system/cache";
	}

	@RequestMapping(value = "system/cache/refresh", method = RequestMethod.GET)
	@ResponseBody
	public boolean refresh() {
		logger.info("刷新缓存");
		quartzService.refreshCache();
		return true;
	}

}
