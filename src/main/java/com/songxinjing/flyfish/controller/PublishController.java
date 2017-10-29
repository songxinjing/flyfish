package com.songxinjing.flyfish.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.songxinjing.flyfish.controller.base.BaseController;
import com.songxinjing.flyfish.domain.Goods;
import com.songxinjing.flyfish.domain.Store;
import com.songxinjing.flyfish.form.GoodsForm;
import com.songxinjing.flyfish.service.GoodsImgService;
import com.songxinjing.flyfish.service.GoodsPlatService;
import com.songxinjing.flyfish.service.GoodsService;
import com.songxinjing.flyfish.service.StoreService;

/**
 * 商品管理控制类
 * 
 * @author songxinjing
 * 
 */
@Controller
public class PublishController extends BaseController {

	@Autowired
	GoodsService goodsService;

	@Autowired
	GoodsPlatService goodsPlatService;

	@Autowired
	GoodsImgService goodsImgService;
	
	@Autowired
	StoreService storeService;

	@RequestMapping(value = "publish/list")
	public String list(Model model, Integer storeId) {
		logger.info("进入刊登店铺列表页面");

		Map<String, Object> paraMap = new HashMap<String, Object>();

		String hql = "select goods from Goods as goods left join goods.stores as store where store.id = :storeId) ";
		paraMap.put("storeId", storeId);
		
		List<Goods> goodses = new ArrayList<Goods>();
		goodses =goodsService.findHql(hql, paraMap);

		List<GoodsForm> list = new ArrayList<GoodsForm>();
		for (Goods goods : goodses) {
			GoodsForm goodsForm = new GoodsForm();
			goodsForm.setGoods(goods);
			goodsForm.setGoodsPlat(goodsPlatService.find(goods.getSku()));
			goodsForm.setGoodsImg(goodsImgService.find(goods.getSku()));
			list.add(goodsForm);
		}

		model.addAttribute("goodsForms", list);
		model.addAttribute("store", storeService.find(storeId));
		
		return "publish/list";
	}
	
	@RequestMapping(value = "publish/remove", method = RequestMethod.GET)
	public String del(Model model, int storeId, String sku) {
		Store store = storeService.find(storeId);
		Set<Goods> set = store.getGoodses();
		Goods goods = goodsService.find(sku);
		if(set.contains(goods)){
			set.remove(goods);
		}
		storeService.update(store);
		return list(model, storeId);
	}

}
