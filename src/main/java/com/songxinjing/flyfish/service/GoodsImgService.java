package com.songxinjing.flyfish.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.songxinjing.flyfish.dao.GoodsImgDao;
import com.songxinjing.flyfish.domain.GoodsImg;
import com.songxinjing.flyfish.service.base.BaseService;

/**
 * 配置信息服务类
 * 
 * @author songxinjing
 * 
 */
@Service
public class GoodsImgService extends BaseService<GoodsImg, String> {

	@Autowired
	public void setSuperDao(GoodsImgDao dao) {
		super.setDao(dao);
	}

}
