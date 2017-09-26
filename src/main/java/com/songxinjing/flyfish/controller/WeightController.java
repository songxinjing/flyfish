package com.songxinjing.flyfish.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.songxinjing.flyfish.constant.Constant;
import com.songxinjing.flyfish.controller.base.BaseController;
import com.songxinjing.flyfish.domain.User;
import com.songxinjing.flyfish.domain.Weight;
import com.songxinjing.flyfish.service.CountryService;
import com.songxinjing.flyfish.service.PlatformService;
import com.songxinjing.flyfish.service.WeightService;

/**
 * 权重管理控制类
 * 
 * @author songxinjing
 * 
 */
@Controller
public class WeightController extends BaseController {

	@Autowired
	PlatformService platformService;

	@Autowired
	CountryService countryService;

	@Autowired
	WeightService weightService;

	@RequestMapping(value = "weight/list", method = RequestMethod.GET)
	public String list(Model model, Integer platId) {
		logger.info("进入国家权重列表页面");

		if (platId == null || platId == 0) {
			platId = 1;
		}

		List<Weight> weights = platformService.find(platId).getWeights();

		model.addAttribute("weights", weights);

		model.addAttribute("countries", countryService.find());
		model.addAttribute("platforms", platformService.find());

		model.addAttribute("platId", platId);

		return "weight/list";
	}

	@RequestMapping(value = "weight/add", method = RequestMethod.POST)
	public String add(HttpServletRequest request, Model model, int countryId, int platformId, BigDecimal rate) {
		Weight weight = new Weight();
		weight.setCountry(countryService.find(countryId));
		weight.setPlatform(platformService.find(platformId));
		weight.setRate(rate);
		// 获取用户登录信息
		User user = (User) request.getSession().getAttribute(Constant.SESSION_LOGIN_USER);
		weight.setModifyId(user.getUserId());
		weight.setModifyer(user.getName());
		weight.setModifyTm(new Timestamp(System.currentTimeMillis()));
		weightService.save(weight);
		return list(model, platformId);
	}

	@RequestMapping(value = "weight/modify", method = RequestMethod.POST)
	public String modify(HttpServletRequest request, Model model, int id, int countryId, int platformId,
			BigDecimal rate) {
		Weight weight = weightService.find(id);
		weight.setCountry(countryService.find(countryId));
		weight.setPlatform(platformService.find(platformId));
		weight.setRate(rate);
		// 获取用户登录信息
		User user = (User) request.getSession().getAttribute(Constant.SESSION_LOGIN_USER);
		weight.setModifyId(user.getUserId());
		weight.setModifyer(user.getName());
		weight.setModifyTm(new Timestamp(System.currentTimeMillis()));
		weightService.update(weight);
		return list(model, platformId);
	}

	@RequestMapping(value = "weight/del", method = RequestMethod.GET)
	public String del(Model model, int id) {
		Weight weight = weightService.find(id);
		int platId = weight.getPlatform().getId();
		weightService.delete(weight);
		return list(model, platId);
	}

}
