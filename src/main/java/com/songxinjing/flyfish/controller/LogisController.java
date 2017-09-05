package com.songxinjing.flyfish.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.songxinjing.flyfish.controller.base.BaseController;
import com.songxinjing.flyfish.domain.Logis;
import com.songxinjing.flyfish.domain.Weight;
import com.songxinjing.flyfish.service.LogisService;
import com.songxinjing.flyfish.service.PlatformService;

/**
 * 商品管理控制类
 * 
 * @author songxinjing
 * 
 */
@Controller
public class LogisController extends BaseController {

	@Autowired
	LogisService logisService;

	@Autowired
	PlatformService platformService;

	@RequestMapping(value = "logis/list", method = RequestMethod.GET)
	public String list(Model model, Integer platid) {
		logger.info("进入物流方式列表页面");

		List<Logis> logises = logisService.find();

		Map<String, List<Logis>> map = new HashMap<String, List<Logis>>();

		for (Logis logis : logises) {

			if (logis.getMethod() == 1) {
				logis.setFee100(logis.getParaA().multiply(new BigDecimal(100)).add(logis.getParaB()));
			} else if (logis.getMethod() == 2) {
				if (logis.getParaX().compareTo(new BigDecimal(100)) < 0) {
					logis.setFee100(logis.getParaC());
				} else {
					logis.setFee100(logis.getParaD().multiply(new BigDecimal(100)));
				}
			}

			if (map.containsKey(logis.getProd().getName())) {
				map.get(logis.getProd().getName()).add(logis);
			} else {
				List<Logis> list = new ArrayList<Logis>();
				list.add(logis);
				map.put(logis.getProd().getName(), list);
			}
		}

		List<Weight> weights = platformService.find(1).getWeights();

		Map<Integer, BigDecimal> countryWeight = new HashMap<Integer, BigDecimal>();

		for (Weight weight : weights) {
			countryWeight.put(weight.getCountry().getId(), weight.getRate());
		}

		Map<String, BigDecimal> mapFee100ByWeight = new HashMap<String, BigDecimal>();

		for (String logisName : map.keySet()) {
			BigDecimal fee100ByWeight = new BigDecimal(0);
			for (Logis logis : map.get(logisName)) {
				fee100ByWeight.add(logis.getFee100().multiply(countryWeight.get(logis.getCountry().getId())));
			}
			mapFee100ByWeight.put(logisName, fee100ByWeight);
		}

		model.addAttribute("map", map);
		model.addAttribute("countryWeight", countryWeight);
		model.addAttribute("mapFee100ByWeight", mapFee100ByWeight);

		return "logis/list";
	}

}
