package com.songxinjing.flyfish.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.songxinjing.flyfish.dao.GoodsDao;
import com.songxinjing.flyfish.dao.GoodsPlatDao;
import com.songxinjing.flyfish.domain.Goods;
import com.songxinjing.flyfish.domain.GoodsPlat;
import com.songxinjing.flyfish.service.base.BaseService;

/**
 * 配置信息服务类
 * 
 * @author songxinjing
 * 
 */
@Service
public class GoodsPlatService extends BaseService<GoodsPlat, String> {

	@Autowired
	GoodsDao goodsDao;

	@Autowired
	public void setSuperDao(GoodsPlatDao dao) {
		super.setDao(dao);
	}

	/**
	 * 新增
	 * 
	 * @param entity
	 *            实体对象
	 * @return 主键值
	 */
	@Transactional
	public String save(final GoodsPlat entity) {

		if (goodsDao.find(entity.getSku()) == null) {
			Goods goods = new Goods();
			goods.setSku(entity.getSku());
			goods.setParentSku(entity.getParentSku());
			goodsDao.save(goods);
		}
		return (String) dao.save(entity);
	}

	/**
	 * 修改
	 * 
	 * @param entity
	 *            实体对象
	 * @return 主键值
	 */
	@Transactional
	public void update(final GoodsPlat entity) {

		if (goodsDao.find(entity.getSku()) == null) {
			Goods goods = new Goods();
			goods.setSku(entity.getSku());
			goods.setParentSku(entity.getParentSku());
			goodsDao.save(goods);
		}
		dao.update(entity);
	}

}
