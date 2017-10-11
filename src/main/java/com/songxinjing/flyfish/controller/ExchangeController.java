package com.songxinjing.flyfish.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.songxinjing.flyfish.controller.base.BaseController;
import com.songxinjing.flyfish.domain.Exchange;
import com.songxinjing.flyfish.service.ExchangeService;

/**
 * 商品管理控制类
 * 
 * @author songxinjing
 * 
 */
@Controller
public class ExchangeController extends BaseController {

	@Autowired
	ExchangeService exchangeService;

	@RequestMapping(value = "exchange/list", method = RequestMethod.GET)
	public String prodList(Model model) {
		logger.info("进入物流产品列表页面");

		List<Exchange> recList = exchangeService.find();
		model.addAttribute("recList", recList);

		return "exchange/list";
	}

	@RequestMapping(value = "exchange/modify", method = RequestMethod.POST)
	@ResponseBody
	public boolean prodModify(Exchange form) {
		Exchange exchange = exchangeService.find(form.getCode());
		exchange.setRate(form.getRate());
		exchangeService.update(exchange);
		return true;
	}

}
