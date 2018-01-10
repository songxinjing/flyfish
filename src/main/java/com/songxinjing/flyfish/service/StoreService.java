package com.songxinjing.flyfish.service;

import java.util.HashMap;
import java.util.Map;

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
public class StoreService extends BaseService<Store, Integer> {

	@Autowired
	public void setSuperDao(StoreDao dao) {
		super.setDao(dao);
	}

	public int getNextMove(int platformId) {
		String hql = "select max(move) from Store where platform.id = :platId ";
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("platId", platformId);
		Long max = (Long) this.findHql(hql, paraMap).get(0);
		int iMax = 0;
		if (max != null) {
			iMax = max.intValue();
		}
		return iMax + 1;
	}

}
