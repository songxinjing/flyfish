package com.songxinjing.flyfish.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.songxinjing.flyfish.constant.Constant;
import com.songxinjing.flyfish.controller.base.BaseController;
import com.songxinjing.flyfish.domain.User;
import com.songxinjing.flyfish.service.StoreService;
import com.songxinjing.flyfish.service.UserService;

/**
 * 主页控制类
 * 
 * @author songxinjing
 * 
 */
@Controller
public class HomeController extends BaseController {

	@Autowired
	UserService userService;
	
	@Autowired
	StoreService storeService;

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String home() {
		logger.info("进入主页面");
		return "index";
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login(Model model, String reqUrl) {
		logger.info("进入登录页面");
		model.addAttribute("reqUrl", reqUrl);
		return "login";
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		logger.info("执行登出");
		request.getSession().removeAttribute(Constant.SESSION_LOGIN_USER);
		return "redirect:/login.html";
	}

	@RequestMapping(value = "doLogin", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model, String reqUrl, String userId, String password) {
		logger.info("进行登录");
		if (StringUtils.isEmpty(reqUrl)) {
			reqUrl = "/index.html";
		}
		User loginUser = userService.find(userId);
		if (loginUser != null && password.equals(loginUser.getPassword())) {
			request.getSession().setAttribute(Constant.SESSION_LOGIN_USER, loginUser);
			request.getSession().setAttribute(Constant.SESSION_STORES,storeService.find());
			return "redirect:" + reqUrl;
		}
		model.addAttribute("msg", "用户名或密码错误，请重试！！！");
		return login(model, reqUrl);
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
