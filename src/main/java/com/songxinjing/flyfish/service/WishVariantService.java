package com.songxinjing.flyfish.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.songxinjing.flyfish.dao.WishVariantDao;
import com.songxinjing.flyfish.domain.WishVariant;
import com.songxinjing.flyfish.service.base.BaseService;

/**
 * Wish店铺服务类
 * 
 * @author songxinjing
 * 
 */
@Service
public class WishVariantService extends BaseService< WishVariant, String>{

	@Autowired
	public void setSuperDao( WishVariantDao dao) {
		super.setDao(dao);
	}

}
