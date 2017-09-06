package com.songxinjing.flyfish.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
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
import com.songxinjing.flyfish.service.CountryService;
import com.songxinjing.flyfish.service.LogisProdService;
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
	LogisProdService logisProdService;

	@Autowired
	CountryService countryService;

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
				if (logis.getParaX().compareTo(new BigDecimal(100)) > 0) {
					logis.setFee100(logis.getParaC());
				} else {
					logis.setFee100(logis.getParaD().multiply(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP));
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

		// 物流产品在该平台的加权平均运费
		Map<String, BigDecimal> mapFee100ByWeight = new HashMap<String, BigDecimal>();
		
		// 物流产品备注
		Map<String, String> remarkMap = new HashMap<String, String>();

		for (String logisName : map.keySet()) {
			BigDecimal fee100ByWeight = new BigDecimal(0);
			BigDecimal allRate = new BigDecimal(0);
			for (Logis logis : map.get(logisName)) {
				BigDecimal weightRate = new BigDecimal(0);
				if(countryWeight.get(logis.getCountry().getId()) != null){
					weightRate = countryWeight.get(logis.getCountry().getId());
				}
				fee100ByWeight = fee100ByWeight.add(logis.getFee100().multiply(weightRate).divide(new BigDecimal(100)));
				allRate = allRate.add(weightRate);
			}
			mapFee100ByWeight.put(logisName, fee100ByWeight.setScale(2, RoundingMode.HALF_UP));
			String remark = "";
			if(allRate.compareTo(new BigDecimal(100)) < 0){
				remark = "<span class='remark-red'>权重合计"+allRate+"%,不足100%!!!</span>";
			}else if(allRate.compareTo(new BigDecimal(100)) > 0){
				remark = "<span class='remark-red'>权重合计"+allRate+"%,超过100%!!!</span>";
			}else {
				remark = "<span class='remark-gren'>权重合计"+allRate+"%</span>";
			}
			remarkMap.put(logisName, remark);
		}

		model.addAttribute("map", map);
		model.addAttribute("countryWeight", countryWeight);
		model.addAttribute("mapFee100ByWeight", mapFee100ByWeight);
		model.addAttribute("remarkMap", remarkMap);

		model.addAttribute("countries", countryService.find());
		model.addAttribute("prods", logisProdService.find());

		return "logis/list";
	}

	@RequestMapping(value = "logis/add", method = RequestMethod.POST)
	public String add(Model model, int prod, int country, int method, BigDecimal paraA, BigDecimal paraB,
			BigDecimal paraC, BigDecimal paraX, BigDecimal paraD) {
		Logis logis = new Logis();
		logis.setCountry(countryService.find(country));
		logis.setProd(logisProdService.find(prod));
		logis.setMethod(method);
		logis.setParaA(paraA);
		logis.setParaB(paraB);
		logis.setParaC(paraC);
		logis.setParaX(paraX);
		logis.setParaD(paraD);
		logis.setModifyId("songxinjing");
		logis.setModifyer("宋鑫晶");
		logis.setModifyTm(new Timestamp(System.currentTimeMillis()));
		logisService.save(logis);
		return list(model,1);
	}

}
