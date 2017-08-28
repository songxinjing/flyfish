package com.songxinjing.flyfish.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.songxinjing.flyfish.controller.base.BaseController;

/**
 * 主页控制类
 * 
 * @author songxinjing
 * 
 */
@Controller
public class HomeController extends BaseController {

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String home(Model model, HttpServletRequest request) {
		logger.info("进入主页面");
		model.addAttribute("menu", "index");
		return "index";
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login(Model model, HttpServletRequest request) {
		logger.info("进入登录页面");
		return "login";
	}

	@RequestMapping(value = "system/error", method = RequestMethod.GET)
	public String error() {
		logger.info("进入报错页面");
		return "system/error";
	}

	@RequestMapping(value = "system/404", method = RequestMethod.GET)
	public String notFound() {
		logger.info("进入404页面");
		return "system/404";
	}

}
