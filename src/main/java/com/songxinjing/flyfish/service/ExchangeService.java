package com.songxinjing.flyfish.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.songxinjing.flyfish.dao.ExchangeDao;
import com.songxinjing.flyfish.domain.Exchange;
import com.songxinjing.flyfish.service.base.BaseService;

/**
 * 汇率信息服务类
 * 
 * @author songxinjing
 * 
 */
@Service
public class ExchangeService extends BaseService<Exchange, String>{

	@Autowired
	public void setSuperDao(ExchangeDao dao) {
		super.setDao(dao);
	}

}
