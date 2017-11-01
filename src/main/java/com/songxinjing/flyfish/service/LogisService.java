package com.songxinjing.flyfish.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.songxinjing.flyfish.dao.LogisDao;
import com.songxinjing.flyfish.domain.Logis;
import com.songxinjing.flyfish.domain.Platform;
import com.songxinjing.flyfish.service.base.BaseService;

/**
 * 配置信息服务类
 * 
 * @author songxinjing
 * 
 */
@Service
public class LogisService extends BaseService<Logis, Integer> {

	@Autowired
	public void setSuperDao(LogisDao dao) {
		super.setDao(dao);
	}

	public BigDecimal getShippingPrice(Logis logis, BigDecimal weight) {
		BigDecimal price = new BigDecimal(0);
		if(weight == null){
			weight = new BigDecimal(0);
		}
		if (logis.getMethod() == 1 && logis.getParaA() != null && logis.getParaB() != null) {
			price = logis.getParaA().multiply(weight).add(logis.getParaB()).setScale(2, RoundingMode.HALF_UP);
		} else if (logis.getMethod() == 2 && logis.getParaC() != null && logis.getParaX() != null
				&& logis.getParaD() != null) {
			if (logis.getParaX().compareTo(weight) > 0) {
				price = logis.getParaC();
			} else {
				price = logis.getParaD().multiply(weight).setScale(2, RoundingMode.HALF_UP);
			}
		}
		return price;
	}

	public BigDecimal getPlatCountryWeight(Platform platform, Logis logis) {
		String hql = "select weight.rate from Weight as weight left join weight.platform as platform "
				+ "left join weight.country as country where platform.id = ? and country.id = ?";
		return (BigDecimal) this.findHqlAObject(hql, platform.getId(), logis.getCountry().getId());
	}

}
