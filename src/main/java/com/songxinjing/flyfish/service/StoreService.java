package com.songxinjing.flyfish.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.songxinjing.flyfish.dao.StoreDao;
import com.songxinjing.flyfish.domain.Store;
import com.songxinjing.flyfish.service.base.BaseService;

/**
 * 域名信息服务类
 * 
 * @author songxinjing
 * 
 */
@Service
public class StoreService extends BaseService<Store, Integer>{

	@Autowired
	public void setSuperDao(StoreDao dao) {
		super.setDao(dao);
	}
	
	public int getNextMove(int platformId){
		String hql = "select max(move) from Store where platform.id = ? ";
		Object max = this.findHqlAObject(hql, platformId);
		int iMax = 0;
		if(max != null){
			iMax = (Integer)max;
		}
		return iMax+1;
	}

}
