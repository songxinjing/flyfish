package com.songxinjing.flyfish.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.songxinjing.flyfish.dao.CountryDao;
import com.songxinjing.flyfish.domain.Country;
import com.songxinjing.flyfish.service.base.BaseService;

/**
 * 国家信息服务类
 * 
 * @author songxinjing
 * 
 */
@Service
public class CountryService extends BaseService<Country, Integer>{

	@Autowired
	public void setSuperDao(CountryDao dao) {
		super.setDao(dao);
	}

}
