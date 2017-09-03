package com.songxinjing.flyfish.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.songxinjing.flyfish.controller.base.BaseController;
import com.songxinjing.flyfish.domain.Logis;
import com.songxinjing.flyfish.plugin.page.PageModel;
import com.songxinjing.flyfish.service.LogisService;

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

	@RequestMapping(value = "logis/list", method = RequestMethod.GET)
	public String list(Model model, Integer page) {
		logger.info("进入物流方式列表页面");

		if (page == null) {
			page = 1;
		}

		int total = logisService.find().size();

		// 分页代码
		PageModel<Logis> pageModel = new PageModel<Logis>();
		pageModel.init(page, total);
		pageModel.setUrl("logis/list.html");
		pageModel.setPara("?");
		String hql = "from Logis";
		List<Logis> logises = logisService.findPage(hql, pageModel.getRecFrom(), pageModel.getPageSize());

		for (Logis logis : logises) {
			if(logis.getMethod() == 1){
				logis.setFee100(logis.getParaA().multiply(new BigDecimal(100)).add(logis.getParaB()));
			} else if (logis.getMethod() == 2){
				if(logis.getParaX().compareTo(new BigDecimal(100)) < 0){
					logis.setFee100(logis.getParaC());
				}else{
					logis.setFee100(logis.getParaD().multiply(new BigDecimal(100)));
				}
			}
		}

		pageModel.setRecList(logises);

		model.addAttribute("pageModel", pageModel);

		model.addAttribute("menu", "page");

		return "logis/list";
	}

}
