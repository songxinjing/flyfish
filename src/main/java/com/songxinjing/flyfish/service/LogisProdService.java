package com.songxinjing.flyfish.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.songxinjing.flyfish.dao.LogisProdDao;
import com.songxinjing.flyfish.domain.Logis;
import com.songxinjing.flyfish.domain.LogisProd;
import com.songxinjing.flyfish.domain.Platform;
import com.songxinjing.flyfish.service.base.BaseService;

/**
 * 物流产品信息服务类
 * 
 * @author songxinjing
 * 
 */
@Service
public class LogisProdService extends BaseService<LogisProd, Integer> {

	@Autowired
	public void setSuperDao(LogisProdDao dao) {
		super.setDao(dao);
	}

	@Autowired
	private LogisService logisService;

	public BigDecimal getShippingPrice(Platform platform, LogisProd logisProd, BigDecimal weight) {
		if (logisProd == null) {
			return null;
		}
		BigDecimal price = new BigDecimal(0);
		BigDecimal allRate = new BigDecimal(0);
		for (Logis logis : logisProd.getLogises()) {
			BigDecimal weightRate = logisService.getPlatCountryWeight(platform, logis);
			if (weightRate != null) {
				price = price.add(logisService.getShippingPrice(logis, weight).multiply(weightRate)
						.divide(new BigDecimal(100)));
				allRate = allRate.add(weightRate);
			}

		}
		price = price.setScale(2, RoundingMode.HALF_UP);
		if (allRate.compareTo(new BigDecimal(100)) == 0) {
			return price;
		}
		return null;
	}

}
