package com.songxinjing.flyfish.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.songxinjing.flyfish.constant.Constant;
import com.songxinjing.flyfish.domain.Goods;
import com.songxinjing.flyfish.domain.GoodsImg;
import com.songxinjing.flyfish.excel.ExcelTemp;
import com.songxinjing.flyfish.plugin.cache.MapCache;
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

	public void uploadImg() {
		Calendar now = Calendar.getInstance();
		int hour = now.get(Calendar.HOUR_OF_DAY);
		logger.info("开始执行图片上传任务");
		String hql = "select sku from Goods where isUpload = :isUpload";
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("isUpload", 0);
		@SuppressWarnings("unchecked")
		List<String> list = (List<String>) goodsService.findPage(hql, 0, 300, paraMap, String.class);
		if(list.isEmpty()){
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
		String hql = "select distinct trim(bigCataName) from Goods where length(bigCataName) > 0";
		List<String> bigCataNames = (List<String>) goodsService.findHql(hql);
		MapCache.addUpdate(Constant.CACHE_bigCataNames, bigCataNames);

		hql = "select distinct trim(smallCataName) from Goods  where length(smallCataName) > 0";
		List<String> smallCataNames = (List<String>) goodsService.findHql(hql);
		MapCache.addUpdate(Constant.CACHE_smallCataNames, smallCataNames);

		hql = "select distinct trim(bussOwner1) from Goods  where length(bussOwner1) > 0";
		List<String> bussOwner1s = (List<String>) goodsService.findHql(hql);
		MapCache.addUpdate(Constant.CACHE_bussOwner1s, bussOwner1s);

		hql = "select distinct trim(bussOwner2) from Goods  where length(bussOwner2) > 0";
		List<String> bussOwner2s = (List<String>) goodsService.findHql(hql);
		MapCache.addUpdate(Constant.CACHE_bussOwner2s, bussOwner2s);

		hql = "select distinct trim(buyer) from Goods  where length(buyer) > 0";
		List<String> buyers = (List<String>) goodsService.findHql(hql);
		MapCache.addUpdate(Constant.CACHE_buyers, buyers);

	}

}
