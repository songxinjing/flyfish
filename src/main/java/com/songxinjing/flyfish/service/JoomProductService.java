package com.songxinjing.flyfish.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.songxinjing.flyfish.dao.JoomProductDao;
import com.songxinjing.flyfish.domain.JoomProduct;
import com.songxinjing.flyfish.service.base.BaseService;

/**
 * Joom店铺服务类
 * 
 * @author songxinjing
 * 
 */
@Service
public class JoomProductService extends BaseService< JoomProduct, String>{

	@Autowired
	public void setSuperDao( JoomProductDao dao) {
		super.setDao(dao);
	}

}
