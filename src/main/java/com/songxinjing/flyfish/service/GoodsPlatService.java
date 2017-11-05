package com.songxinjing.flyfish.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.songxinjing.flyfish.dao.GoodsPlatDao;
import com.songxinjing.flyfish.domain.GoodsPlat;
import com.songxinjing.flyfish.service.base.BaseService;

/**
 * 商品（平台）信息服务类
 * 
 * @author songxinjing
 * 
 */
@Service
public class GoodsPlatService extends BaseService<GoodsPlat, String> {

	@Autowired
	public void setSuperDao(GoodsPlatDao dao) {
		super.setDao(dao);
	}

}
