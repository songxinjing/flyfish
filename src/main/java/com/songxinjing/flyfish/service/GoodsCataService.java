package com.songxinjing.flyfish.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.songxinjing.flyfish.dao.GoodsCataDao;
import com.songxinjing.flyfish.domain.GoodsCata;
import com.songxinjing.flyfish.service.base.BaseService;

/**
 * 商品分类信息服务类
 * 
 * @author songxinjing
 * 
 */
@Service
public class GoodsCataService extends BaseService<GoodsCata, Long>{

	@Autowired
	public void setSuperDao(GoodsCataDao dao) {
		super.setDao(dao);
	}

}
