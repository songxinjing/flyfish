package com.songxinjing.flyfish.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.songxinjing.flyfish.constant.Constant;
import com.songxinjing.flyfish.controller.base.BaseController;
import com.songxinjing.flyfish.domain.Logis;
import com.songxinjing.flyfish.domain.LogisProd;
import com.songxinjing.flyfish.domain.Platform;
import com.songxinjing.flyfish.domain.User;
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
	private LogisService logisService;

	@Autowired
	private LogisProdService logisProdService;

	@Autowired
	private CountryService countryService;

	@Autowired
	private PlatformService platformService;

	@RequestMapping(value = "logis/list", method = RequestMethod.GET)
	public String list(Model model, Integer platId) {
		logger.info("进入物流方式列表页面");

		if (platId == null || platId == 0) {
			platId = 1;
		}

		Platform platform = platformService.find(platId);

		List<Logis> logises = logisService.find();

		Map<LogisProd, List<Logis>> logisMap = new LinkedHashMap<LogisProd, List<Logis>>();

		for (Logis logis : logises) {
			logis.setFee100(logisService.getShippingPrice(logis, new BigDecimal(100)));
			logis.setPlatCountryWeight(logisService.getPlatCountryWeight(platform, logis));
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
			BigDecimal fee100ByWeight = logisProdService.getShippingPrice(platform, logisProd, new BigDecimal(100));
			String remark = "";
			if (fee100ByWeight == null) {
				remark = "<span class='remark-red'>权重合计不等于100%!!!</span>";
				fee100ByWeight = new BigDecimal(0);
			}
			form.setFee100ByWeight(fee100ByWeight.setScale(2, RoundingMode.HALF_UP));
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
		logger.info("新增物流方式");
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
		logger.info("修改物流方式");
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
		logger.info("删除物流方式");
		Logis logis = logisService.find(id);
		int queryProdId = logis.getProd().getId();
		logisService.delete(logis);
		model.addAttribute("queryProdId", queryProdId);
		return list(model, platId);
	}

	@RequestMapping(value = "logisprod/list", method = RequestMethod.GET)
	public String prodList(Model model) {
		logger.info("进入物流产品列表页面");
		List<LogisProd> recList = logisProdService.find();
		model.addAttribute("recList", recList);
		return "logisprod/list";
	}

	@RequestMapping(value = "logisprod/add", method = RequestMethod.POST)
	@ResponseBody
	public boolean prodAdd(LogisProd form) {
		logger.info("新增物流产品");
		int id = (Integer) logisProdService.save(form);
		LogisProd logisProd = logisProdService.find(id);
		logisProd.setOrderNum(id);
		logisProdService.update(logisProd);
		return true;
	}

	@RequestMapping(value = "logisprod/modify", method = RequestMethod.POST)
	@ResponseBody
	public boolean prodModify(LogisProd form) {
		logger.info("修改物流产品");
		LogisProd logisProd = logisProdService.find(form.getId());
		logisProd.setName(form.getName());
		logisProdService.update(logisProd);
		return true;
	}

	@RequestMapping(value = "logisprod/delete", method = RequestMethod.GET)
	public String delete(Integer id) {
		logger.info("删除物流产品");
		logisProdService.delete(id);
		return "redirect:/logisprod/list.html";
	}

}
