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

import com.songxinjing.flyfish.domain.Goods;
import com.songxinjing.flyfish.domain.GoodsImg;
import com.songxinjing.flyfish.domain.GoodsPlat;
import com.songxinjing.flyfish.excel.ExcelTemp;
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
	private GoodsPlatService goodsPlatService;

	@Autowired
	private GoodsImgService goodsImgService;

	public void uploadImg() {
		Calendar now = Calendar.getInstance();
		int hour = now.get(Calendar.HOUR_OF_DAY);
		logger.info("开始执行任务(" + hour + ")");
		String hql = "from GoodsPlat where isUpload = :isUpload";
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("isUpload", 0);
		List<GoodsPlat> list = goodsPlatService.findPage(hql, 0, 300, paraMap);
		// 首先全部标志为正在上传，避免其他任务重复上传
		for (GoodsPlat gp : list) {
			gp.setIsUpload(2); // 2表示正在上传
			goodsPlatService.update(gp);
		}
		logger.info("任务(" + hour + ") 标记完毕");

		int all = list.size();
		int fail = 0;
		for (GoodsPlat gp : list) {
			GoodsImg goodsImg = goodsImgService.find(gp.getSku());
			if (goodsImg == null) {
				goodsImg = new GoodsImg();
				goodsImg.setSku(gp.getSku());
				goodsImgService.save(goodsImg);
			}

			boolean gpSuccess = true;

			for (String key : ExcelTemp.WISH_FIELD.keySet()) {
				String name = ExcelTemp.WISH_FIELD.get(key);
				if (StringUtils.isNotEmpty(name) && name.contains("Img")) {
					String gpUrl = (String) ReflectionUtil.getFieldValue(gp, name);
					String imgName = (String) ReflectionUtil.getFieldValue(goodsImg, name);
					if (StringUtils.isNotEmpty(gpUrl) && StringUtils.isEmpty(imgName)) {
						if (gpUrl.contains("?")) {
							gpUrl = gpUrl.substring(0, gpUrl.indexOf("?"));
						}
						String tempSku = gp.getSku();
						if (name.equals("mainImgUrl")) {
							Goods goods = goodsService.find(gp.getSku());
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
				gp.setIsUpload(1); // 1表示成功
				goodsPlatService.update(gp);
			} else {
				logger.info("SKU " + gp.getSku() + " 图片上传存在失败情况");
				fail++;
			}
			all--;
			logger.info("任务(" + hour + ") SKU " + gp.getSku() + " 图片上传完毕(剩余：" + all + ")");
		}

		logger.info("完成任务(" + hour + ") 图片上传工作，上传数量：" + list.size() + "，其中失败数量：" + fail);
	}

}
