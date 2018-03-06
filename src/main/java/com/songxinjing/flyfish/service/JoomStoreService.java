package com.songxinjing.flyfish.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.songxinjing.flyfish.dao.JoomStoreDao;
import com.songxinjing.flyfish.domain.JoomStore;
import com.songxinjing.flyfish.service.base.BaseService;

/**
 * Wish店铺服务类
 * 
 * @author songxinjing
 * 
 */
@Service
public class JoomStoreService extends BaseService<JoomStore, Integer>{

	@Autowired
	public void setSuperDao( JoomStoreDao dao) {
		super.setDao(dao);
	}

}
