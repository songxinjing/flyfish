package com.songxinjing.flyfish.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.songxinjing.flyfish.constant.Constant;
import com.songxinjing.flyfish.controller.base.BaseController;
import com.songxinjing.flyfish.domain.Goods;
import com.songxinjing.flyfish.domain.GoodsImg;
import com.songxinjing.flyfish.domain.Store;
import com.songxinjing.flyfish.domain.StoreGoods;
import com.songxinjing.flyfish.form.GoodsForm;
import com.songxinjing.flyfish.service.GoodsImgService;
import com.songxinjing.flyfish.service.GoodsService;
import com.songxinjing.flyfish.service.StoreGoodsService;
import com.songxinjing.flyfish.service.StoreService;
import com.songxinjing.flyfish.util.BaseUtil;

/**
 * 商品管理控制类
 * 
 * @author songxinjing
 * 
 */
@Controller
public class PublishController extends BaseController {

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private GoodsImgService goodsImgService;

	@Autowired
	private StoreService storeService;

	@Autowired
	private StoreGoodsService storeGoodsService;

	@RequestMapping(value = "publish/list")
	public String list(Model model, Integer storeId, String batchNo, Boolean dataFlag) {
		logger.info("进入刊登店铺列表页面");
		if (dataFlag == null) {
			dataFlag = true;
		}
		Map<String, Object> paraMap = new HashMap<String, Object>();
		Store store = storeService.find(storeId);
		if (StringUtils.isEmpty(batchNo)) {
			String hql = "select max(batchNo) from StoreGoods where store.id = :storeId ";
			paraMap.clear();
			paraMap.put("storeId", storeId);
			batchNo = (String) storeGoodsService.findHql(hql, paraMap).get(0);
		}

		String hql = "select distinct batchNo from StoreGoods where store.id = :storeId ";
		paraMap.clear();
		paraMap.put("storeId", storeId);
		@SuppressWarnings("unchecked")
		List<String> batchNos = (List<String>) storeGoodsService.findHql(hql, paraMap);

		hql = "select goods from Goods as goods left join goods.storeGoodses as sg left join sg.store as store where store.id = :storeId and sg.batchNo = :batchNo ";
		paraMap.clear();
		paraMap.put("storeId", storeId);
		paraMap.put("batchNo", batchNo);
		@SuppressWarnings("unchecked")
		List<Goods> goodses = (List<Goods>) goodsService.findHql(hql, paraMap);

		List<GoodsForm> listT = new ArrayList<GoodsForm>();
		List<GoodsForm> listF = new ArrayList<GoodsForm>();
		for (Goods goods : goodses) {
			GoodsForm goodsForm = new GoodsForm();
			GoodsImg goodsImg = goodsImgService.find(goods.getSku());
			if (goodsImg == null) {
				goodsImg = new GoodsImg();
			}
			goodsForm.setGoods(goods);
			goodsForm.setGoodsImg(goodsImg);
			goodsForm.setListingSku(BaseUtil.changeSku(goods.getSku(), store.getMove()));
			if (StringUtils.isNotEmpty(goods.getParentSku())) {
				goodsForm.setListingParentSku(BaseUtil.changeSku(goods.getParentSku(), store.getMove()));
			}
			BigDecimal shippingPrice = goodsService.getShippingPrice(store.getPlatform(), goods);
			if (shippingPrice == null) {
				shippingPrice = new BigDecimal(0);
			}
			BigDecimal price = goodsService.getPrice(store.getPlatform(), goods, shippingPrice);
			goodsForm.setPlatformPrice(price);
			if (store.getPlatform().getName().equals(Constant.Wish)) {
				goodsForm.setPlatformTitle(goods.getTitle());
				goodsForm.setTitleRed(false);
			} else if (store.getPlatform().getName().equals(Constant.Ebay)) {
				goodsForm.setPlatformTitle(goods.getEbayTitle());
				if (StringUtils.isNotEmpty(goods.getEbayTitle()) && goods.getEbayTitle().length() > 75) {
					goodsForm.setTitleRed(true);
				} else {
					goodsForm.setTitleRed(false);
				}
			} else {
				goodsForm.setPlatformTitle(goods.getOtherTitle());
				if (StringUtils.isNotEmpty(goods.getOtherTitle()) && goods.getOtherTitle().length() > 90) {
					goodsForm.setTitleRed(true);
				} else {
					goodsForm.setTitleRed(false);
				}
			}

			if (StringUtils.isNotEmpty(goods.getWeight()) && StringUtils.isNotEmpty(goods.getCostPrice())
					&& StringUtils.isNotEmpty(goods.getTitle()) && !goodsForm.isTitleRed()
					&& StringUtils.isNotEmpty(goodsImg.getMainImgUrl())) {
				listT.add(goodsForm);
			} else {
				listF.add(goodsForm);
			}
		}
		if (dataFlag) {
			model.addAttribute("goodsForms", listT);
		} else {
			model.addAttribute("goodsForms", listF);
		}
		model.addAttribute("batchNo", batchNo);
		model.addAttribute("dataFlag", dataFlag);
		model.addAttribute("batchNos", batchNos);
		model.addAttribute("store", store);

		return "publish/list";
	}

	@RequestMapping(value = "publish/remove", method = RequestMethod.GET)
	public String remove(Model model, Integer storeId, String sku, Boolean dataFlag) {
		logger.info("商品移除店铺");
		String hql = "select sg from StoreGoods as sg left join sg.store as store left join sg.goods as goods where store.id = :storeId and goods.sku = :sku ";
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("storeId", storeId);
		paraMap.put("sku", sku);
		@SuppressWarnings("unchecked")
		List<StoreGoods> list = (List<StoreGoods>) storeGoodsService.findHql(hql, paraMap);
		String batchNo = null;
		if (!list.isEmpty()) {
			batchNo = list.get(0).getBatchNo();
			storeGoodsService.delete(list);
		}
		return list(model, storeId, batchNo, dataFlag);
	}

	@RequestMapping(value = "publish/removeall", method = RequestMethod.GET)
	public String removeall(Model model, Integer storeId, String batchNo) {
		logger.info("商品批量移除店铺");
		String hql = "select sg from StoreGoods as sg left join sg.store as store where store.id = :storeId and sg.batchNo = :batchNo ";
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("storeId", storeId);
		paraMap.put("batchNo", batchNo);
		@SuppressWarnings("unchecked")
		List<StoreGoods> list = (List<StoreGoods>) storeGoodsService.findHql(hql, paraMap);
		storeGoodsService.delete(list);
		return list(model, storeId, null, null);
	}

}
