package com.songxinjing.flyfish.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.songxinjing.flyfish.dao.WishStoreDao;
import com.songxinjing.flyfish.domain.WishStore;
import com.songxinjing.flyfish.service.base.BaseService;

/**
 * Wish店铺服务类
 * 
 * @author songxinjing
 * 
 */
@Service
public class WishStoreService extends BaseService< WishStore, Integer>{

	@Autowired
	public void setSuperDao( WishStoreDao dao) {
		super.setDao(dao);
	}

}
