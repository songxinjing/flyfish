package com.songxinjing.flyfish.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		if (weight == null) {
			weight = new BigDecimal(0);
		}
		if (logis.getMethod() == 1 && logis.getParaA() != null && logis.getParaB() != null) {
			price = logis.getParaA().multiply(weight).add(logis.getParaB()).setScale(2, RoundingMode.HALF_UP);
		} else if (logis.getMethod() == 2 && logis.getParaC() != null && logis.getParaX() != null
				&& logis.getParaD() != null && logis.getParaD() != null) {
			if (logis.getParaX().compareTo(weight) > 0) {
				price = logis.getParaC();
			} else {
				price = logis.getParaD().multiply(weight).add(logis.getParaE()).setScale(2, RoundingMode.HALF_UP);
			}
		}
		return price;
	}

	public BigDecimal getPlatCountryWeight(Platform platform, Logis logis) {
		String hql = "select weight.rate from Weight as weight left join weight.platform as platform "
				+ "left join weight.country as country where platform.id = :platId and country.id = :countryId";
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("platId", platform.getId());
		paraMap.put("countryId", logis.getCountry().getId());
		@SuppressWarnings("unchecked")
		List<BigDecimal> list = (List<BigDecimal>) this.findHql(hql, paraMap);
		if(!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}

}
