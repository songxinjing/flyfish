package com.songxinjing.flyfish.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.songxinjing.flyfish.constant.Constant;
import com.songxinjing.flyfish.dao.GoodsDao;
import com.songxinjing.flyfish.domain.Goods;
import com.songxinjing.flyfish.domain.LogisProd;
import com.songxinjing.flyfish.domain.Platform;
import com.songxinjing.flyfish.service.base.BaseService;

/**
 * 配置信息服务类
 * 
 * @author songxinjing
 * 
 */
@Service
public class GoodsService extends BaseService<Goods, String> {

	@Autowired
	public void setSuperDao(GoodsDao dao) {
		super.setDao(dao);
	}

	@Autowired
	private LogisProdService logisProdService;

	@Autowired
	private ExchangeService exchangeService;

	/**
	 * 计算商品运费
	 * @param platform
	 * @param goods
	 * @return
	 */
	public BigDecimal getShippingPrice(Platform platform, Goods goods) {
		logger.debug("计算运费：" + platform.getName() + " " + goods.getSku());
		BigDecimal shippingPrice = null;
		String weight = goods.getWeight();
		if (StringUtils.isEmpty(weight)) {
			weight = "0";
		}
		BigDecimal goodsWeight = new BigDecimal(weight);
		if (platform.getWeightStrategy() == null || goodsWeight.compareTo(platform.getWeightStrategy()) >= 0) {
			shippingPrice = logisProdService.getShippingPrice(platform, platform.getProdStrategy(), goodsWeight);
		} else {
			for (LogisProd logisProd : logisProdService.find()) {
				BigDecimal temp = logisProdService.getShippingPrice(platform, logisProd, goodsWeight);
				if (temp != null && (shippingPrice == null || shippingPrice.compareTo(temp) > 0)) {
					shippingPrice = temp;
				}
			}
		}
		return shippingPrice;
	}

	/**
	 * 计算商品售价
	 * @param platform
	 * @param goods
	 * @param shippingPrice
	 * @return
	 */
	public BigDecimal getPrice(Platform platform, Goods goods, BigDecimal shippingPrice) {
		logger.debug("计算售价：" + platform.getName() + " " + goods.getSku());
		if (shippingPrice == null) {
			shippingPrice = new BigDecimal(0);
		}
		String costPrice = goods.getCostPrice();
		if (StringUtils.isEmpty(costPrice)) {
			costPrice = "0";
		}
		BigDecimal bdCostPrice = new BigDecimal(costPrice);
		BigDecimal price = new BigDecimal(0);
		if (shippingPrice.compareTo(new BigDecimal(0)) > 0 || bdCostPrice.compareTo(new BigDecimal(0)) > 0) {
			BigDecimal platformRate = platform.getRate().divide(new BigDecimal(100));
			BigDecimal profitRate = platform.getProfitRate().divide(new BigDecimal(100));
			BigDecimal cutRate = platform.getCutRate().divide(new BigDecimal(100));
			BigDecimal tempRate = new BigDecimal(1).subtract(platformRate).subtract(profitRate);
			BigDecimal exchangeRate = exchangeService.find(Constant.USD_CNY).getRate();
			price = shippingPrice.add(bdCostPrice).divide(tempRate, 4, RoundingMode.HALF_UP)
					.divide(cutRate, 4, RoundingMode.HALF_UP).divide(exchangeRate, 4, RoundingMode.HALF_UP);
			price = price.setScale(2, RoundingMode.HALF_UP);
		}
		return price;
	}

	/**
	 * 查询关联带*SKU
	 * @param skuTemp
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Goods> findMoreSku(String skuTemp) {
		String hql = "from Goods where sku like :sku";
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("sku", skuTemp +"%");
		return (List<Goods>) this.findHql(hql, paraMap);
	}

}
