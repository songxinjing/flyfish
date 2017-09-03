package com.songxinjing.flyfish.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.songxinjing.flyfish.dao.LogisDao;
import com.songxinjing.flyfish.domain.Logis;
import com.songxinjing.flyfish.service.base.BaseService;

/**
 * 配置信息服务类
 * 
 * @author songxinjing
 * 
 */
@Service
public class LogisService extends BaseService<Logis, String>{

	@Autowired
	public void setSuperDao(LogisDao logisDao) {
		super.setDao(logisDao);
	}

}
