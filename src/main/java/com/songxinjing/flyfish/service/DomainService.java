package com.songxinjing.flyfish.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.songxinjing.flyfish.dao.DomainDao;
import com.songxinjing.flyfish.domain.Domain;
import com.songxinjing.flyfish.service.base.BaseService;

/**
 * 域名信息服务类
 * 
 * @author songxinjing
 * 
 */
@Service
public class DomainService extends BaseService<Domain, String>{

	@Autowired
	public void setSuperDao(DomainDao dao) {
		super.setDao(dao);
	}

}
