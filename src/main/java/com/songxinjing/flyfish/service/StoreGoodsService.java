package com.songxinjing.flyfish.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.songxinjing.flyfish.dao.StoreGoodsDao;
import com.songxinjing.flyfish.domain.StoreGoods;
import com.songxinjing.flyfish.service.base.BaseService;

/**
 * 域名信息服务类
 * 
 * @author songxinjing
 * 
 */
@Service
public class StoreGoodsService extends BaseService<StoreGoods, Integer>{

	@Autowired
	public void setSuperDao(StoreGoodsDao dao) {
		super.setDao(dao);
	}
	
}
