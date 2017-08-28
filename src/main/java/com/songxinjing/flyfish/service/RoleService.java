package com.songxinjing.flyfish.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.songxinjing.flyfish.dao.RoleDao;
import com.songxinjing.flyfish.domain.Role;
import com.songxinjing.flyfish.service.base.BaseService;

/**
 * 配置信息服务类
 * 
 * @author songxinjing
 * 
 */
@Service
public class RoleService extends BaseService<Role, String>{

	@Autowired
	public void setSuperDao(RoleDao roleDao) {
		super.setDao(roleDao);
	}

}
