package com.songxinjing.flyfish.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.songxinjing.flyfish.dao.PlatformDao;
import com.songxinjing.flyfish.domain.Platform;
import com.songxinjing.flyfish.service.base.BaseService;

/**
 * 配置信息服务类
 * 
 * @author songxinjing
 * 
 */
@Service
public class PlatformService extends BaseService<Platform, Integer> {

	@Autowired
	public void setSuperDao(PlatformDao dao) {
		super.setDao(dao);
	}

	public Platform findByName(String name) {
		String hql = "from Platform where name = :name";
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("name", name);
		@SuppressWarnings("unchecked")
		List<Platform> list = (List<Platform>) findHql(hql, paraMap);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

}
