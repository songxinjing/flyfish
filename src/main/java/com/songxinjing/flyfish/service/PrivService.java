package com.songxinjing.flyfish.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.songxinjing.flyfish.dao.PrivDao;
import com.songxinjing.flyfish.domain.Privilege;
import com.songxinjing.flyfish.service.base.BaseService;

/**
 * 配置信息服务类
 * 
 * @author songxinjing
 * 
 */
@Service
public class PrivService extends BaseService<Privilege, String>{

	@Autowired
	public void setSuperDao(PrivDao dao) {
		super.setDao(dao);
	}
	
	/**
	 * 获取已经激活的权限列表
	 */
	@SuppressWarnings("unchecked")
	public List<Privilege> findEnable() {
		String hql = "from Privilege where enable = :enable";
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("enable", true);
		return (List<Privilege>) findHql(hql, paraMap);
	}

}
