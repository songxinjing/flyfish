package com.songxinjing.flyfish.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.songxinjing.flyfish.controller.base.BaseController;
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
	public String list(Model model, Integer platid) {
		logger.info("进入物流方式列表页面");

		List<Weight> weights = platformService.find(1).getWeights();

		model.addAttribute("weights", weights);

		model.addAttribute("countries", countryService.find());
		model.addAttribute("platforms", platformService.find());

		return "weight/list";
	}
	
	@RequestMapping(value = "weight/add", method = RequestMethod.POST)
	public String add(Model model, int country, int platform, BigDecimal rate) {
		Weight weight = new Weight();
		weight.setCountry(countryService.find(country));
		weight.setPlatform(platformService.find(platform));
		weight.setRate(rate);
		weight.setModifyId("songxinjing");
		weight.setModifyer("宋鑫晶");
		weight.setModifyTm(new Timestamp(System.currentTimeMillis()));
		weightService.save(weight);
		return list(model,1);
	}

}
