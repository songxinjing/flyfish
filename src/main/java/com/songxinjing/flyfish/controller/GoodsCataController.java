package com.songxinjing.flyfish.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.songxinjing.flyfish.controller.base.BaseController;
import com.songxinjing.flyfish.domain.GoodsCata;
import com.songxinjing.flyfish.form.GoodsCataForm;
import com.songxinjing.flyfish.service.GoodsCataService;

/**
 * 域名管理控制类
 * 
 * @author songxinjing
 * 
 */
@Controller
public class GoodsCataController extends BaseController {

	@Autowired
	private GoodsCataService goodsCataService;

	@RequestMapping(value = "goodscata/getnodes", method = RequestMethod.GET)
	@ResponseBody
	public List<GoodsCataForm> getnodes(Long id) {
		if (id == null) {
			id = 0l;
		}

		Map<String, Object> paraMap = new HashMap<String, Object>();
		String hql = "from GoodsCata where parentId = :parentId ";
		paraMap.put("parentId", id);
		@SuppressWarnings("unchecked")
		List<GoodsCata> list = (List<GoodsCata>) goodsCataService.findHql(hql, paraMap);
		List<GoodsCataForm> nodes = new ArrayList<GoodsCataForm>();
		for (GoodsCata cata : list) {
			GoodsCataForm form = new GoodsCataForm();
			form.setId(cata.getId());
			form.setName(cata.getName());
			if(cata.getIsLeaf() == 0){
				form.setIsParent(true);
				form.setNocheck(true);
			}
			form.setFullName(cata.getFullName());
			nodes.add(form);
		}

		return nodes;
	}

}
