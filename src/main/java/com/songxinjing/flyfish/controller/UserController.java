package com.songxinjing.flyfish.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.songxinjing.flyfish.controller.base.BaseController;
import com.songxinjing.flyfish.domain.User;
import com.songxinjing.flyfish.plugin.page.PageModel;
import com.songxinjing.flyfish.service.UserService;

/**
 * 用户控制类
 * 
 * @author songxinjing
 * 
 */
@Controller
public class UserController extends BaseController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "user/list", method = RequestMethod.GET)
	public String list(Model model, Integer page) {
		logger.info("进入用户列表页面");

		if (page == null) {
			page = 1;
		}

		int total = userService.find().size();

		// 分页代码
		PageModel<User> pageModel = new PageModel<User>();
		pageModel.init(page, total);
		pageModel.setUrl("user/list.html");
		String hql = "from User";
		List<User> users = userService.findPage(hql, pageModel.getRecFrom(), pageModel.getPageSize());
		pageModel.setRecList(users);

		model.addAttribute("pageModel", pageModel);

		model.addAttribute("menu", "page");
		return "user/list";
	}

}
