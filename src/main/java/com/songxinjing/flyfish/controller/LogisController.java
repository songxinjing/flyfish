package com.songxinjing.flyfish.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.songxinjing.flyfish.constant.Constant;
import com.songxinjing.flyfish.controller.base.BaseController;
import com.songxinjing.flyfish.domain.Country;
import com.songxinjing.flyfish.domain.Logis;
import com.songxinjing.flyfish.domain.LogisProd;
import com.songxinjing.flyfish.domain.User;
import com.songxinjing.flyfish.domain.Weight;
import com.songxinjing.flyfish.form.LogisProdForm;
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
	public String list(Model model, Integer platId) {
		logger.info("进入物流方式列表页面");

		if (platId == null || platId == 0) {
			platId = 1;
		}

		List<Weight> weights = platformService.find(platId).getWeights();

		Map<Country, BigDecimal> countryWeightMap = new HashMap<Country, BigDecimal>();

		for (Weight weight : weights) {
			countryWeightMap.put(weight.getCountry(), weight.getRate());
		}

		List<Logis> logises = logisService.find();

		Map<LogisProd, List<Logis>> logisMap = new LinkedHashMap<LogisProd, List<Logis>>();

		for (Logis logis : logises) {

			if (logis.getMethod() == 1 && logis.getParaA() != null && logis.getParaB() != null) {
				logis.setFee100(logis.getParaA().multiply(new BigDecimal(100)).add(logis.getParaB()).setScale(2,
						RoundingMode.HALF_UP));
			} else if (logis.getMethod() == 2 && logis.getParaC() != null && logis.getParaX() != null
					&& logis.getParaD() != null) {
				if (logis.getParaX().compareTo(new BigDecimal(100)) > 0) {
					logis.setFee100(logis.getParaC());
				} else {
					logis.setFee100(logis.getParaD().multiply(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP));
				}
			} else {
				logis.setFee100(new BigDecimal(0));
			}
			if (countryWeightMap.get(logis.getCountry()) != null) {
				logis.setPlatCountryWeight(countryWeightMap.get(logis.getCountry()));
			}
			if (logisMap.containsKey(logis.getProd())) {
				logisMap.get(logis.getProd()).add(logis);
			} else {
				List<Logis> list = new ArrayList<Logis>();
				list.add(logis);
				logisMap.put(logis.getProd(), list);
			}
		}

		List<LogisProdForm> prodFormList = new ArrayList<LogisProdForm>();

		for (LogisProd logisProd : logisMap.keySet()) {
			LogisProdForm form = new LogisProdForm();
			form.setLogisProd(logisProd);
			form.setLogises(logisMap.get(logisProd));
			BigDecimal fee100ByWeight = new BigDecimal(0);
			BigDecimal allRate = new BigDecimal(0);
			for (Logis logis : logisMap.get(logisProd)) {
				BigDecimal weightRate = new BigDecimal(0);
				if (logis.getPlatCountryWeight() != null) {
					weightRate = logis.getPlatCountryWeight();
				}
				fee100ByWeight = fee100ByWeight.add(logis.getFee100().multiply(weightRate).divide(new BigDecimal(100)));
				allRate = allRate.add(weightRate);
			}
			form.setFee100ByWeight(fee100ByWeight.setScale(2, RoundingMode.HALF_UP));
			String remark = "";
			if (allRate.compareTo(new BigDecimal(100)) < 0) {
				remark = "<span class='remark-red'>权重合计" + allRate + "%,不足100%!!!</span>";
			} else if (allRate.compareTo(new BigDecimal(100)) > 0) {
				remark = "<span class='remark-red'>权重合计" + allRate + "%,超过100%!!!</span>";
			} else {
				remark = "<span class='remark-green'>权重合计" + allRate + "%</span>";
			}
			form.setRemark(remark);
			prodFormList.add(form);
		}

		model.addAttribute("prodFormList", prodFormList);

		model.addAttribute("countries", countryService.find());
		model.addAttribute("prods", logisProdService.find());
		model.addAttribute("platforms", platformService.find());
		model.addAttribute("platId", platId);

		return "logis/list";
	}

	@RequestMapping(value = "logis/add", method = RequestMethod.POST)
	public String add(HttpServletRequest request, Model model, int prodId, int countryId, int method, BigDecimal paraA,
			BigDecimal paraB, BigDecimal paraC, BigDecimal paraX, BigDecimal paraD, int platId) {
		if (method == 1) {
			if (paraA == null) {
				paraA = new BigDecimal(0);
			}
			if (paraB == null) {
				paraB = new BigDecimal(0);
			}
		}

		if (method == 2) {
			if (paraC == null) {
				paraC = new BigDecimal(0);
			}
			if (paraD == null) {
				paraD = new BigDecimal(0);
			}
			if (paraX == null) {
				paraX = new BigDecimal(0);
			}
		}

		Logis logis = new Logis();
		logis.setCountry(countryService.find(countryId));
		logis.setProd(logisProdService.find(prodId));
		logis.setMethod(method);
		logis.setParaA(paraA);
		logis.setParaB(paraB);
		logis.setParaC(paraC);
		logis.setParaX(paraX);
		logis.setParaD(paraD);
		// 获取用户登录信息
		User user = (User) request.getSession().getAttribute(Constant.SESSION_LOGIN_USER);
		logis.setModifyId(user.getUserId());
		logis.setModifyer(user.getName());
		logis.setModifyTm(new Timestamp(System.currentTimeMillis()));
		logisService.save(logis);
		model.addAttribute("queryProdId", prodId);
		return list(model, platId);
	}

	@RequestMapping(value = "logis/modify", method = RequestMethod.POST)
	public String modify(HttpServletRequest request, Model model, int id, int prodId, int countryId, int method,
			BigDecimal paraA, BigDecimal paraB, BigDecimal paraC, BigDecimal paraX, BigDecimal paraD, int platId) {
		if (method == 1) {
			if (paraA == null) {
				paraA = new BigDecimal(0);
			}
			if (paraB == null) {
				paraB = new BigDecimal(0);
			}
		}

		if (method == 2) {
			if (paraC == null) {
				paraC = new BigDecimal(0);
			}
			if (paraD == null) {
				paraD = new BigDecimal(0);
			}
			if (paraX == null) {
				paraX = new BigDecimal(0);
			}
		}

		Logis logis = logisService.find(id);
		logis.setCountry(countryService.find(countryId));
		logis.setProd(logisProdService.find(prodId));
		logis.setMethod(method);
		logis.setParaA(paraA);
		logis.setParaB(paraB);
		logis.setParaC(paraC);
		logis.setParaX(paraX);
		logis.setParaD(paraD);
		// 获取用户登录信息
		User user = (User) request.getSession().getAttribute(Constant.SESSION_LOGIN_USER);
		logis.setModifyId(user.getUserId());
		logis.setModifyer(user.getName());
		logis.setModifyTm(new Timestamp(System.currentTimeMillis()));
		logisService.update(logis);
		model.addAttribute("queryProdId", prodId);
		return list(model, platId);
	}

	@RequestMapping(value = "logis/del", method = RequestMethod.GET)
	public String del(Model model, int id, int platId) {
		Logis logis = logisService.find(id);
		int queryProdId = logis.getProd().getId();
		logisService.delete(logis);
		model.addAttribute("queryProdId", queryProdId);
		return list(model, platId);
	}

}
