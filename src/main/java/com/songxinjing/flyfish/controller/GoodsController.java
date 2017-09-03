package com.songxinjing.flyfish.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.songxinjing.flyfish.controller.base.BaseController;
import com.songxinjing.flyfish.domain.Goods;
import com.songxinjing.flyfish.plugin.page.PageModel;
import com.songxinjing.flyfish.service.GoodsService;

/**
 * 商品管理控制类
 * 
 * @author songxinjing
 * 
 */
@Controller
public class GoodsController extends BaseController {

	@Autowired
	GoodsService goodsService;

	@RequestMapping(value = "goods/list", method = RequestMethod.GET)
	public String list(Model model, Integer page) {
		logger.info("进入商品列表页面");

		if (page == null) {
			page = 1;
		}

		int total = goodsService.find().size();

		// 分页代码
		PageModel<Goods> pageModel = new PageModel<Goods>();
		pageModel.init(page, total);
		pageModel.setUrl("goods/list.html");
		pageModel.setPara("?");
		String hql = "from Goods";
		List<Goods> goodses = goodsService.findPage(hql, pageModel.getRecFrom(), pageModel.getPageSize());
		pageModel.setRecList(goodses);

		model.addAttribute("pageModel", pageModel);

		model.addAttribute("menu", "page");

		return "goods/list";
	}

	@RequestMapping(value = "goods/add", method = RequestMethod.GET)
	public String add() {
		logger.info("进入商品列表页面");
		return "goods/add";
	}

	@RequestMapping(value = "goods/import", method = RequestMethod.GET)
	public String home() {
		logger.info("进入商品列表页面");
		return "goods/list";
	}

}
