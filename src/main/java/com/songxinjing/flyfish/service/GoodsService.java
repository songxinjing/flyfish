package com.songxinjing.flyfish.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.songxinjing.flyfish.dao.GoodsDao;
import com.songxinjing.flyfish.domain.Goods;
import com.songxinjing.flyfish.service.base.BaseService;

/**
 * 配置信息服务类
 * 
 * @author songxinjing
 * 
 */
@Service
public class GoodsService extends BaseService<Goods, String>{

	@Autowired
	public void setSuperDao(GoodsDao goodsDao) {
		super.setDao(goodsDao);
	}

}
