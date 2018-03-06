package com.songxinjing.flyfish.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.songxinjing.flyfish.dao.JoomVariantDao;
import com.songxinjing.flyfish.domain.JoomVariant;
import com.songxinjing.flyfish.service.base.BaseService;

/**
 * Wish店铺服务类
 * 
 * @author songxinjing
 * 
 */
@Service
public class JoomVariantService extends BaseService<JoomVariant, String>{

	@Autowired
	public void setSuperDao( JoomVariantDao dao) {
		super.setDao(dao);
	}

}
