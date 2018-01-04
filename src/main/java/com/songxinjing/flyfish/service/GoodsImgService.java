package com.songxinjing.flyfish.service;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.songxinjing.flyfish.dao.GoodsImgDao;
import com.songxinjing.flyfish.domain.Goods;
import com.songxinjing.flyfish.domain.GoodsImg;
import com.songxinjing.flyfish.domain.GoodsPlat;
import com.songxinjing.flyfish.excel.ExcelTemp;
import com.songxinjing.flyfish.service.base.BaseService;
import com.songxinjing.flyfish.util.ReflectionUtil;
import com.songxinjing.flyfish.util.SftpUtil;

/**
 * 配置信息服务类
 * 
 * @author songxinjing
 * 
 */
@Service
public class GoodsImgService extends BaseService<GoodsImg, String> {

	@Autowired
	public void setSuperDao(GoodsImgDao dao) {
		super.setDao(dao);
	}

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private GoodsPlatService goodsPlatService;

	public void updateImg() {
		Calendar now = Calendar.getInstance();
		int hour = now.get(Calendar.HOUR_OF_DAY);
		logger.info("开始执行当日任务-" + hour);
		String hql = "from GoodsPlat where isUpload = ?";
		List<GoodsPlat> list = goodsPlatService.findPage(hql, 0, 300, 0);
		// 首先全部标志为已经上传，避免其他任务重复上传
		for (GoodsPlat gp : list) {
			gp.setIsUpload(1);
			goodsPlatService.update(gp);
		}
		logger.info("完成当日任务-" + hour + " 标记工作");

		int all = 300;
		for (GoodsPlat gp : list) {
			GoodsImg goodsImg = find(gp.getSku());
			if (goodsImg == null) {
				goodsImg = new GoodsImg();
				goodsImg.setSku(gp.getSku());
				save(goodsImg);
			}

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
						SftpUtil.doFTP(imgName, gpUrl);
						ReflectionUtil.setFieldValue(goodsImg, name, imgName);
					}
				}
			}
			update(goodsImg);
			all--;
			logger.info("完成当日任务-" + hour + " SKU " + gp.getSku() + " 图片上传工作");
			logger.info("完成当日任务-" + hour + " SKU剩余数量为 " + all);
		}
	}

}
