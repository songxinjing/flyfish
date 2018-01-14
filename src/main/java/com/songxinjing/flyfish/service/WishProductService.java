package com.songxinjing.flyfish.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.songxinjing.flyfish.dao.WishProductDao;
import com.songxinjing.flyfish.domain.WishProduct;
import com.songxinjing.flyfish.service.base.BaseService;

/**
 * Wish店铺服务类
 * 
 * @author songxinjing
 * 
 */
@Service
public class WishProductService extends BaseService< WishProduct, String>{

	@Autowired
	public void setSuperDao( WishProductDao dao) {
		super.setDao(dao);
	}

}
