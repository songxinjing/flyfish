package com.songxinjing.flyfish.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.songxinjing.flyfish.dao.LogisProdDao;
import com.songxinjing.flyfish.domain.LogisProd;
import com.songxinjing.flyfish.service.base.BaseService;

/**
 * 物流产品信息服务类
 * 
 * @author songxinjing
 * 
 */
@Service
public class LogisProdService extends BaseService<LogisProd, Integer>{

	@Autowired
	public void setSuperDao(LogisProdDao dao) {
		super.setDao(dao);
	}

}
