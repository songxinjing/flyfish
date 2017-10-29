package com.songxinjing.flyfish.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.songxinjing.flyfish.dao.StoreDao;
import com.songxinjing.flyfish.domain.Store;
import com.songxinjing.flyfish.service.base.BaseService;

/**
 * 域名信息服务类
 * 
 * @author songxinjing
 * 
 */
@Service
public class StoreService extends BaseService<Store, Integer>{

	@Autowired
	public void setSuperDao(StoreDao dao) {
		super.setDao(dao);
	}

}
