package com.songxinjing.flyfish.controller;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.songxinjing.flyfish.constant.Constant;
import com.songxinjing.flyfish.controller.base.BaseController;
import com.songxinjing.flyfish.domain.Goods;
import com.songxinjing.flyfish.domain.GoodsPlat;
import com.songxinjing.flyfish.domain.User;
import com.songxinjing.flyfish.form.GoodsEditForm;
import com.songxinjing.flyfish.form.GoodsForm;
import com.songxinjing.flyfish.plugin.page.PageModel;
import com.songxinjing.flyfish.service.DomainService;
import com.songxinjing.flyfish.service.GoodsImgService;
import com.songxinjing.flyfish.service.GoodsPlatService;
import com.songxinjing.flyfish.service.GoodsService;
import com.songxinjing.flyfish.service.LogisProdService;
import com.songxinjing.flyfish.service.PlatformService;
import com.songxinjing.flyfish.util.ReflectionUtil;

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

	@Autowired
	GoodsPlatService goodsPlatService;
	
	@Autowired
	GoodsImgService goodsImgService;

	@Autowired
	DomainService domainService;

	@Autowired
	PlatformService platformService;

	@Autowired
	LogisProdService logisProdService;

	@RequestMapping(value = "goods/list")
	public String list(Model model, Integer page, String skuQuery, String nameQuery) {
		logger.info("进入商品列表页面");

		if (skuQuery == null) {
			skuQuery = "";
		}

		if (nameQuery == null) {
			nameQuery = "";
		}

		if (page == null) {
			page = 1;
		}
		int total = 0;
		String hql = "from Goods where 1=1";
		if (StringUtils.isNotEmpty(skuQuery) && StringUtils.isNotEmpty(nameQuery)) {
			hql = hql + " and (sku like ? or parentSku like ?) and name like ?";
			total = goodsService.findHql(hql, "%" + skuQuery + "%", "%" + skuQuery + "%", "%" + nameQuery + "%").size();
		} else if (StringUtils.isNotEmpty(skuQuery)) {
			hql = hql + " and (sku like ? or parentSku like ?)";
			total = goodsService.findHql(hql, "%" + skuQuery + "%", "%" + skuQuery + "%").size();
		} else if (StringUtils.isNotEmpty(nameQuery)) {
			hql = hql + " and name like ?";
			total = goodsService.findHql(hql, "%" + nameQuery + "%").size();
		} else {
			total = goodsService.find().size();
		}

		// 分页代码
		PageModel<GoodsForm> pageModel = new PageModel<GoodsForm>();
		pageModel.init(page, total);
		pageModel.setUrl("goods/list.html");
		pageModel.setPara("?skuQuery=" + skuQuery + "&nameQuery=" + nameQuery + "&");

		List<Goods> goodses = new ArrayList<Goods>();
		hql = "from Goods where 1=1";
		if (StringUtils.isNotEmpty(skuQuery) && StringUtils.isNotEmpty(nameQuery)) {
			hql = hql + " and (sku like ? or parentSku like ?) and name like ?";
			goodses = goodsService.findPage(hql, pageModel.getRecFrom(), pageModel.getPageSize(), "%" + skuQuery + "%",
					"%" + skuQuery + "%", "%" + nameQuery + "%");
		} else if (StringUtils.isNotEmpty(skuQuery)) {
			hql = hql + " and (sku like ? or parentSku like ?)";
			goodses = goodsService.findPage(hql, pageModel.getRecFrom(), pageModel.getPageSize(), "%" + skuQuery + "%",
					"%" + skuQuery + "%");
		} else if (StringUtils.isNotEmpty(nameQuery)) {
			hql = hql + " and name like ?";
			goodses = goodsService.findPage(hql, pageModel.getRecFrom(), pageModel.getPageSize(),
					"%" + nameQuery + "%");
		} else {
			goodses = goodsService.findPage(hql, pageModel.getRecFrom(), pageModel.getPageSize());
		}

		List<GoodsForm> list = new ArrayList<GoodsForm>();
		for (Goods goods : goodses) {
			GoodsForm form = new GoodsForm();
			form.setGoods(goods);
			form.setGoodsPlat(goodsPlatService.find(goods.getSku()));
			list.add(form);
		}

		pageModel.setRecList(list);

		model.addAttribute("pageModel", pageModel);

		model.addAttribute("page", page);
		model.addAttribute("skuQuery", skuQuery);
		model.addAttribute("nameQuery", nameQuery);
		model.addAttribute("platforms", platformService.find());
		model.addAttribute("domains", domainService.find());
		model.addAttribute("prods", logisProdService.find());
		return "goods/list";
	}

	@RequestMapping(value = "goods/edit", method = RequestMethod.GET)
	public String edit(Model model, String sku) {
		logger.info("进入商品详情页面");
		GoodsForm form = new GoodsForm();
		form.setGoods(goodsService.find(sku));
		form.setGoodsPlat(goodsPlatService.find(sku));
		model.addAttribute("form", form);
		return "goods/edit";
	}
	
	@RequestMapping(value = "goods/delete", method = RequestMethod.GET)
	public String delete(String sku) {
		logger.info("删除商品信息");
		goodsService.delete(sku);
		goodsPlatService.delete(sku);
		goodsImgService.delete(sku);
		return "redirect:/goods/list.html";
	}

	@RequestMapping(value = "goods/save", method = RequestMethod.POST)
	@ResponseBody
	public boolean save(HttpServletRequest request, Model model, GoodsEditForm form) {
		logger.info("保存商品详情页面");
		Goods goods = goodsService.find(form.getSku());
		GoodsPlat goodsPlat = goodsPlatService.find(form.getSku());

		Field[] fields = form.getClass().getDeclaredFields();
		for (Field field : fields) {
			Object value = ReflectionUtil.getFieldValue(form, field.getName());
			if(value != null){
				try {
					goods.getClass().getDeclaredField(field.getName());
					ReflectionUtil.setFieldValue(goods, field.getName(), value);
				} catch (NoSuchFieldException e) {
					logger.debug("Goods不存在字段：" + field.getName());
				}
				try {
					goodsPlat.getClass().getDeclaredField(field.getName());
					ReflectionUtil.setFieldValue(goodsPlat, field.getName(), value);
				} catch (NoSuchFieldException e) {
					logger.debug("GoodsPlat不存在字段：" + field.getName());
				}
			}
		}

		// 获取用户登录信息
		User user = (User) request.getSession().getAttribute(Constant.SESSION_LOGIN_USER);
		goods.setModifyId(user.getUserId());
		goods.setModifyer(user.getName());
		goods.setModifyTm(new Timestamp(System.currentTimeMillis()));

		goodsPlat.setModifyId(user.getUserId());
		goodsPlat.setModifyer(user.getName());
		goodsPlat.setModifyTm(new Timestamp(System.currentTimeMillis()));

		goodsService.update(goods);
		goodsPlatService.update(goodsPlat);
		return true;
	}

}
