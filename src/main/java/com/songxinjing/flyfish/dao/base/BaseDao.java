package com.songxinjing.flyfish.dao.base;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.util.Assert;

import com.songxinjing.flyfish.util.ReflectionUtil;

/**
 * 数据访问对象基础实现类
 * 
 * @author songxinjing
 * 
 */
public abstract class BaseDao<T, PK extends Serializable> extends HibernateDaoSupport {

	protected static Logger logger = LoggerFactory.getLogger(BaseDao.class);

	protected Class<T> entityClass;

	public BaseDao() {
		entityClass = ReflectionUtil.getSuperClassGenricType(getClass(), 0);
	}

	@Autowired
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	/**
	 * 新增
	 * 
	 * @param entity
	 *            实体对象
	 * @return 主键值
	 */
	public Serializable save(final T entity) {
		logger.info("新增数据：" + entity.getClass().getSimpleName());
		Assert.notNull(entity, "对象不能为空！");
		return getHibernateTemplate().save(entity);
	}

	/**
	 * 修改
	 * 
	 * @param entity
	 *            实体对象
	 */
	public void update(final T entity) {
		logger.info("更新数据：" + entityClass.getSimpleName());
		Assert.notNull(entity, "对象不能为空！");
		getHibernateTemplate().update(entity);
	}
	
	/**
	 * 新增或修改
	 * 
	 * @param entity
	 *            实体对象
	 */
	public void saveOrUpdate(final T entity) {
		logger.info("新增或更新数据：" + entityClass.getSimpleName());
		Assert.notNull(entity, "对象不能为空！");
		getHibernateTemplate().saveOrUpdate(entity);
	}

	/**
	 * 删除单个对象
	 * 
	 * @param entity
	 *            实体对象
	 */
	public void delete(final T entity) {
		logger.info("删除数据：" + entityClass.getSimpleName());
		Assert.notNull(entity, "对象不能为空！");
		getHibernateTemplate().delete(entity);
	}

	/**
	 * 删除一组对象
	 * 
	 * @param list
	 *            实体对象List
	 */
	public void delete(final Collection<T> entities) {
		logger.info("批量删除数据：" + entityClass.getSimpleName());
		Assert.notNull(entities, "对象不能为空！");
		getHibernateTemplate().deleteAll(entities);
	}

	/**
	 * 查询全部对象
	 * 
	 * @return 实体对象List
	 */
	public List<T> find() {
		logger.info("查询全部数据：" + entityClass.getSimpleName());
		return getHibernateTemplate().loadAll(entityClass);
	}

	/**
	 * 通过主键ID查询对象.
	 * 
	 * @param id
	 *            主键
	 * @return 实体对象
	 */
	public T find(final PK id) {
		logger.info("查询单个数据：" + entityClass.getSimpleName() + " 主键：" + id);
		Assert.notNull(id, "对象不能为空！");
		return getHibernateTemplate().get(entityClass, id);
	}

	/**
	 * HQL查询
	 * 
	 * @param hql
	 * @return
	 */
	public List<?> findHql(final String hql) {
		logger.info("HQL查询：" + hql);
		return getHibernateTemplate().find(hql);
	}

	/**
	 * HQL查询
	 * 
	 * @param hql
	 * @param paraMap
	 * @return
	 */
	public List<?> findHql(final String hql, final Map<String, Object> paraMap) {
		logger.info("HQL查询：" + hql + " 参数：" + paraMap);
		Assert.hasText(hql, "对象不能为空！");
		String[] paramNames = new String[paraMap.keySet().size()];
		Object[] values = new Object[paraMap.keySet().size()];
		int i = 0;
		for (String key : paraMap.keySet()) {
			paramNames[i] = key;
			values[i] = paraMap.get(key);
			i++;
		}
		return getHibernateTemplate().findByNamedParam(hql, paramNames, values);
	}

	/**
	 * 分页查询
	 * 
	 * @param hql
	 * @param from
	 * @param size
	 * @param values
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findPage(final String hql, final int from, final int size, final Map<String, Object> paraMap) {
		return (List<T>) this.findPage(hql, from, size, paraMap, entityClass);
	}

	/**
	 * 分页查询
	 * 
	 * @param hql
	 * @param from
	 * @param size
	 * @param values
	 * @return
	 */
	public List<?> findPage(final String hql, final int from, final int size, final Map<String, Object> paraMap,
			Class<?> clazz) {
		logger.info("HQL分页查询：" + hql + " from=" + from + " size=" + size + " 参数：" + paraMap);
		Assert.hasText(hql, "对象不能为空！");
		return createQuery(hql, paraMap, clazz).setFirstResult(from).setMaxResults(size).getResultList();
	}

	/**
	 * 创建Query对象
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	public Query<?> createQuery(String hql, Map<String, Object> paraMap, Class<?> clazz) {
		Assert.hasText(hql, "对象不能为空！");
		Query<?> query = this.currentSession().createQuery(hql, clazz);
		if (paraMap != null) {
			for (String key : paraMap.keySet()) {
				Object para = paraMap.get(key);
				if (para instanceof Collection) {
					query.setParameterList(key, (Collection<?>) para);
				} else if (para instanceof Object[]) {
					query.setParameterList(key, (Object[]) para);
				} else {
					query.setParameter(key, para);
				}
			}
		}
		return query;
	}

}