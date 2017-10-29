package com.songxinjing.flyfish.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.songxinjing.flyfish.controller.base.BaseController;
import com.songxinjing.flyfish.domain.Domain;
import com.songxinjing.flyfish.service.DomainService;

/**
 * 商品管理控制类
 * 
 * @author songxinjing
 * 
 */
@Controller
public class DomainController extends BaseController {

	@Autowired
	DomainService domainService;

	@RequestMapping(value = "domain/list", method = RequestMethod.GET)
	public String prodList(Model model) {
		logger.info("进入物流产品列表页面");

		List<Domain> recList = domainService.find();
		model.addAttribute("recList", recList);

		return "domain/list";
	}

	@RequestMapping(value = "domain/add", method = RequestMethod.POST)
	@ResponseBody
	public boolean prodAdd(Domain form) {
		String name = (String) domainService.save(form);
		Domain domain = domainService.find(name);
		domainService.update(domain);
		return true;
	}

	@RequestMapping(value = "domain/delete", method = RequestMethod.GET)
	public String delete(String name) {
		domainService.delete(name);
		return "redirect:/domain/list.html";
	}

}
