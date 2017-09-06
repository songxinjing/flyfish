package com.songxinjing.flyfish.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.songxinjing.flyfish.dao.WeightDao;
import com.songxinjing.flyfish.domain.Weight;
import com.songxinjing.flyfish.service.base.BaseService;

/**
 * 权重信息服务类
 * 
 * @author songxinjing
 * 
 */
@Service
public class WeightService extends BaseService<Weight, Integer>{

	@Autowired
	public void setSuperDao(WeightDao dao) {
		super.setDao(dao);
	}

}
