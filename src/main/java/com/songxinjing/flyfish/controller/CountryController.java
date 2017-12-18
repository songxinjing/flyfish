package com.songxinjing.flyfish.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.songxinjing.flyfish.controller.base.BaseController;
import com.songxinjing.flyfish.domain.Country;
import com.songxinjing.flyfish.service.CountryService;

/**
 * 国家管理控制类
 * 
 * @author songxinjing
 * 
 */
@Controller
public class CountryController extends BaseController {

	@Autowired
	private CountryService countryService;

	@RequestMapping(value = "country/list", method = RequestMethod.GET)
	public String prodList(Model model) {
		logger.info("进入国家列表页面");
		List<Country> recList = countryService.find();
		model.addAttribute("recList", recList);
		return "country/list";
	}

	@RequestMapping(value = "country/add", method = RequestMethod.POST)
	@ResponseBody
	public boolean prodAdd(Country form) {
		logger.info("新增国家");
		int id = (Integer) countryService.save(form);
		Country country = countryService.find(id);
		country.setOrderNum(id);
		countryService.update(country);
		return true;
	}

	@RequestMapping(value = "country/modify", method = RequestMethod.POST)
	@ResponseBody
	public boolean prodModify(Country form) {
		logger.info("修改国家");
		Country country = countryService.find(form.getId());
		country.setName(form.getName());
		countryService.update(country);
		return true;
	}

	@RequestMapping(value = "country/delete", method = RequestMethod.GET)
	public String delete(Integer id) {
		logger.info("删除国家");
		countryService.delete(id);
		return "redirect:/country/list.html";
	}

}
