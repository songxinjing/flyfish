package com.songxinjing.flyfish.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.songxinjing.flyfish.constant.Constant;
import com.songxinjing.flyfish.domain.Goods;
import com.songxinjing.flyfish.domain.GoodsImg;
import com.songxinjing.flyfish.domain.WishProduct;
import com.songxinjing.flyfish.domain.WishStore;
import com.songxinjing.flyfish.domain.WishVariant;
import com.songxinjing.flyfish.excel.ExcelTemp;
import com.songxinjing.flyfish.plugin.cache.MapCache;
import com.songxinjing.flyfish.plugin.wish.api.WishProductApi;
import com.songxinjing.flyfish.plugin.wish.exception.WishException;
import com.songxinjing.flyfish.util.ReflectionUtil;
import com.songxinjing.flyfish.util.SftpUtil;

/**
 * 配置信息服务类
 * 
 * @author songxinjing
 * 
 */
@Service
public class QuartzService {

	protected static Logger logger = LoggerFactory.getLogger(QuartzService.class);

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private GoodsImgService goodsImgService;

	@Autowired
	private WishStoreService wishStoreService;

	@Autowired
	private WishProductService wishProductService;
	
	@Autowired
	private WishVariantService wishVariantService;

	public void uploadImg() {
		Calendar now = Calendar.getInstance();
		int hour = now.get(Calendar.HOUR_OF_DAY);
		logger.info("开始执行图片上传任务");
		String hql = "select sku from Goods where isUpload = :isUpload";
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("isUpload", 0);
		@SuppressWarnings("unchecked")
		List<String> list = (List<String>) goodsService.findPage(hql, 0, 300, paraMap, String.class);
		if (list.isEmpty()) {
			logger.info("任务(" + hour + ") 没有需要上传的图片，任务结束");
			return;
		}
		// 首先全部标志为正在上传，避免其他任务重复上传
		hql = "update Goods set isUpload = 2 where sku in (:skus)";
		paraMap.clear();
		paraMap.put("skus", list);
		goodsService.updateHql(hql, paraMap);
		logger.info("任务(" + hour + ") 标记完毕");

		int all = list.size();
		int fail = 0;
		for (String sku : list) {
			Goods goods = goodsService.find(sku);
			GoodsImg goodsImg = goodsImgService.find(sku);
			if (goodsImg == null) {
				goodsImg = new GoodsImg();
				goodsImg.setSku(sku);
				goodsImgService.save(goodsImg);
			}

			boolean gpSuccess = true;

			for (String key : ExcelTemp.WISH_FIELD.keySet()) {
				String name = ExcelTemp.WISH_FIELD.get(key);
				if (StringUtils.isNotEmpty(name) && name.contains("Img")) {
					String gpUrl = (String) ReflectionUtil.getFieldValue(goods, name);
					String imgName = (String) ReflectionUtil.getFieldValue(goodsImg, name);
					if (StringUtils.isNotEmpty(gpUrl) && StringUtils.isEmpty(imgName)) {
						if (gpUrl.contains("?")) {
							gpUrl = gpUrl.substring(0, gpUrl.indexOf("?"));
						}
						String tempSku = sku;
						if (name.equals("mainImgUrl")) {
							tempSku = goods.getParentSku();
						}
						if (tempSku.contains("*")) {
							tempSku = tempSku.replace("*", "_");
						}
						imgName = tempSku + "-" + name + ".jpg";
						boolean isSuccess = SftpUtil.doFTP(imgName, gpUrl);
						if (isSuccess) {
							ReflectionUtil.setFieldValue(goodsImg, name, imgName);
						} else {
							gpSuccess = false;
						}
					}
				}
			}
			goodsImgService.update(goodsImg);
			if (gpSuccess) {
				hql = "update Goods set isUpload = 1 where sku = :sku";
				paraMap.clear();
				paraMap.put("sku", sku);
				goodsService.updateHql(hql, paraMap);
			} else {
				logger.info("SKU " + sku + " 图片上传存在失败情况");
				fail++;
			}
			all--;
			logger.info("任务(" + hour + ") SKU " + sku + " 图片上传完毕(剩余：" + all + ")");
		}

		logger.info("完成任务(" + hour + ") 图片上传工作，上传数量：" + list.size() + "，其中失败数量：" + fail);
	}

	@SuppressWarnings("unchecked")
	public void refreshCache() {
		logger.info("开始执行缓存刷新任务");
		String hql = "select distinct bigCataName from Goods where length(bigCataName) > 0 order by bigCataName";
		List<String> bigCataNames = (List<String>) goodsService.findHql(hql);
		MapCache.addUpdate(Constant.CACHE_bigCataNames, bigCataNames);

		hql = "select distinct smallCataName from Goods  where length(smallCataName) > 0 order by smallCataName";
		List<String> smallCataNames = (List<String>) goodsService.findHql(hql);
		MapCache.addUpdate(Constant.CACHE_smallCataNames, smallCataNames);

		hql = "select distinct bussOwner1 from Goods  where length(bussOwner1) > 0 order by bussOwner1";
		List<String> bussOwner1s = (List<String>) goodsService.findHql(hql);
		MapCache.addUpdate(Constant.CACHE_bussOwner1s, bussOwner1s);

		hql = "select distinct bussOwner2 from Goods  where length(bussOwner2) > 0 order by bussOwner2";
		List<String> bussOwner2s = (List<String>) goodsService.findHql(hql);
		MapCache.addUpdate(Constant.CACHE_bussOwner2s, bussOwner2s);

		hql = "select distinct buyer from Goods  where length(buyer) > 0 order by buyer";
		List<String> buyers = (List<String>) goodsService.findHql(hql);
		MapCache.addUpdate(Constant.CACHE_buyers, buyers);

	}

	public void wishSync() {

		logger.info("开始执行Wish同步任务");
		String hql = "from WishStore where state = :state order by applyJobTime asc";
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("state", 1);
		@SuppressWarnings("unchecked")
		List<WishStore> list = (List<WishStore>) wishStoreService.findPage(hql, 0, 1, paraMap, WishStore.class);
		if (!list.isEmpty()) {
			WishStore store = list.get(0);
			hql = "update WishStore set state = 2 where id = :id";
			paraMap.clear();
			paraMap.put("id", store.getId());
			wishStoreService.updateHql(hql, paraMap);

			int from = 0;
			int size = 50;
			boolean hasNext = true;
			do {
				try {
					String result = WishProductApi.multiGet(from, size, null, true, store.getAccessToken());
					JSONObject json = JSON.parseObject(result);
					JSONArray products = json.getJSONArray("data");
					for (int i = 0; i < products.size(); i++) {
						JSONObject product = products.getJSONObject(i).getJSONObject("Product");
						saveProduct(store, product);
					}
					JSONObject paging = json.getJSONObject("paging");
					String next = paging.getString("next");
					if (StringUtils.isEmpty(next)) {
						hasNext = false;
					} else {
						from = from + size;
					}
				} catch (WishException e) {
					logger.error("调用Wish List all Products 接口错误", e);
					break;
				}

			} while (hasNext);

			Timestamp syncTime = new Timestamp(System.currentTimeMillis());
			hql = "update WishStore set state = 0, lastSyncTime = :lastSyncTime where id = :id";
			paraMap.clear();
			paraMap.put("lastSyncTime", syncTime);
			paraMap.put("id", store.getId());
			wishStoreService.updateHql(hql, paraMap);
		}
	}

	public void saveProduct(WishStore store, JSONObject product) {

		String parentSku = product.getString("parent_sku");
		WishProduct wishProduct = null;
		if (StringUtils.isNotEmpty(parentSku)) {
			wishProduct = wishProductService.find(parentSku);
		}
		if (wishProduct == null) {
			wishProduct = new WishProduct();
			wishProduct.setParentSku(parentSku);
			wishProduct.setStore(store);
		}
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
		wishProduct.setVariants(null);
		
		wishProductService.saveOrUpdate(wishProduct);
		
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
				wishVariant.setSku(sku);
				wishVariant.setProduct(wishProduct);
			}
			wishVariant.setWishId(variant.getString("id"));
			wishVariant.setAllImages(variant.getString("all_images"));
			wishVariant.setPrice(variant.getString("price"));
			wishVariant.setEnabled(variant.getString("enabled"));
			wishVariant.setShipping(variant.getString("shipping"));
			wishVariant.setInventory(variant.getString("inventory"));
			wishVariant.setSize(variant.getString("size"));
			wishVariant.setMsrp(variant.getString("msrp"));
			wishVariant.setShippingTime(variant.getString("shipping_time"));
			wishVariantService.saveOrUpdate(wishVariant);
		}
	}

}
