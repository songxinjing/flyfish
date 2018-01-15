package com.songxinjing.flyfish.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.songxinjing.flyfish.controller.base.BaseController;
import com.songxinjing.flyfish.domain.WishProduct;
import com.songxinjing.flyfish.domain.WishStore;
import com.songxinjing.flyfish.domain.WishVariant;
import com.songxinjing.flyfish.plugin.wish.api.WishProductApi;
import com.songxinjing.flyfish.plugin.wish.exception.WishException;
import com.songxinjing.flyfish.service.WishProductService;
import com.songxinjing.flyfish.service.WishStoreService;
import com.songxinjing.flyfish.service.WishVariantService;

/**
 * WISH店铺产品控制类
 * 
 * @author songxinjing
 * 
 */
@Controller
public class WishController extends BaseController {

	@Autowired
	WishStoreService wishStoreService;

	@Autowired
	WishProductService wishProductService;

	@Autowired
	WishVariantService wishVariantService;

	@RequestMapping(value = "wish/storelist", method = RequestMethod.GET)
	public String storelist(Model model) {
		logger.info("进入Wish店铺列表页面");

		List<WishStore> stores = wishStoreService.find();

		model.addAttribute("stores", stores);

		return "wish/storelist";
	}

	@RequestMapping(value = "wish/storemodify", method = RequestMethod.POST)
	public String storemodify(HttpServletRequest request, Model model, int id, String name) {
		logger.info("修改虚拟店铺");
		WishStore store = wishStoreService.find(id);
		store.setName(name);
		// 获取用户登录信息
		wishStoreService.update(store);
		return storelist(model);
	}

	@RequestMapping(value = "wish/productlist", method = RequestMethod.GET)
	public String productlist(Model model, Integer storeId) {
		logger.info("进入Wish店铺列表页面");

		WishStore store = wishStoreService.find(storeId);

		model.addAttribute("store", store);

		return "wish/productlist";
	}

	@RequestMapping(value = "wish/sync", method = RequestMethod.GET)
	@ResponseBody
	public boolean sync(Model model, Integer storeId) {
		logger.info("同步");
		try {
			WishStore store = wishStoreService.find(storeId);
			String result = WishProductApi.multiGet(0, 10, null, true, store.getAccessToken());
			JSONObject json = JSON.parseObject(result);
			JSONArray products = json.getJSONArray("data");
			for (int i = 0; i < products.size(); i++) {
				JSONObject product = products.getJSONObject(i).getJSONObject("Product");
				String parentSku = product.getString("parent_sku");
				WishProduct wishProduct = null;
				if (StringUtils.isNotEmpty(parentSku)) {
					wishProduct = wishProductService.find(parentSku);
				}
				if (wishProduct == null) {
					wishProduct = new WishProduct();
				}
				wishProduct.setParentSku(parentSku);
				wishProduct.setWishId(product.getString("id"));
				wishProduct.setMainImage(product.getString("main_image"));
				wishProduct.setIsPromoted(product.getString("is_promoted"));
				wishProduct.setName(product.getString("name"));
				// wishProduct.setTags(product.getString("tags"));
				wishProduct.setReviewStatus(product.getString("review_status"));
				wishProduct.setExtraImages(product.getString("extra_images"));
				wishProduct.setNumberSaves(product.getString("number_saves"));
				wishProduct.setNumberSold(product.getString("number_sold"));
				wishProduct.setLastUpdated(product.getString("last_updated"));
				wishProduct.setDescription(product.getString("description"));
				
				List<WishVariant> variantList = wishProduct.getVariants();
				if(variantList != null){
					variantList.clear();
				}else{
					variantList = new ArrayList<WishVariant>();
				}
				JSONArray variants = product.getJSONArray("variants");
				for (int j = 0; j < variants.size(); j++) {
					JSONObject variant = variants.getJSONObject(j).getJSONObject("Variant");
					String sku = variant.getString("sku");
					WishVariant wishVariant = null;
					if (StringUtils.isNotEmpty(sku)) {
						wishVariant = wishVariantService.find(sku);
					}
					if (wishVariant == null) {
						wishVariant = new WishVariant();
					}
					wishVariant.setSku(sku);
					wishVariant.setWishId(variant.getString("id"));
					wishVariant.setAllImages(variant.getString("all_images"));
					wishVariant.setPrice(variant.getString("price"));
					wishVariant.setEnabled(variant.getString("enabled"));
					wishVariant.setShipping(variant.getString("shipping"));
					wishVariant.setInventory(variant.getString("inventory"));
					wishVariant.setSize(variant.getString("size"));
					wishVariant.setMsrp(variant.getString("msrp"));
					wishVariant.setShippingTime(variant.getString("shipping_time"));
					wishVariant.setProduct(wishProduct);
					variantList.add(wishVariant);
				}
				wishProduct.setVariants(variantList);
				wishProduct.setStore(store);
				wishProductService.saveOrUpdate(wishProduct);
			}

		} catch (WishException e) {
			logger.error("调用Wish List all Products 接口错误", e);
		}

		return true;
	}

}
