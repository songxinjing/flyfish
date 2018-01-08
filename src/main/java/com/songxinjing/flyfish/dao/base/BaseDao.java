package com.songxinjing.flyfish.dao.base;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
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
		Assert.notNull(entity, "对象不能为空！");
		getHibernateTemplate().update(entity);
	}

	/**
	 * 删除单个对象
	 * 
	 * @param entity
	 *            实体对象
	 */
	public void delete(final T entity) {
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
		Assert.notNull(entities, "对象不能为空！");
		getHibernateTemplate().deleteAll(entities);
	}

	/**
	 * 查询全部对象
	 * 
	 * @return 实体对象List
	 */
	public List<T> find() {
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
		Assert.notNull(id, "对象不能为空！");
		return getHibernateTemplate().get(entityClass, id);
	}

	/**
	 * 通过含有部分属性的对象查询符合该属性的对象List.
	 * 
	 * @param entity
	 *            模板实体对象
	 * @return 实体对象List
	 */
	public List<T> find(final T entity) {
		Assert.notNull(entity, "对象不能为空！");
		return getHibernateTemplate().findByExample(entity);
	}

	/**
	 * HQL查询
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	public List<T> findHql(final String hql, final Object... values) {
		Assert.hasText(hql, "对象不能为空！");
		return createQuery(hql, values).getResultList();
	}

	/**
	 * HQL查询
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	public List<T> findHql(final String hql, final Map<String, Object> paraMap) {
		Assert.hasText(hql, "对象不能为空！");
		return createQuery(hql, paraMap).getResultList();
	}

	/**
	 * HQL查询
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	public List<Object> findHqlObject(final String hql, final Object... values) {
		Assert.hasText(hql, "对象不能为空！");
		return createQueryObject(hql, values).getResultList();
	}

	/**
	 * HQL查询
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	public List<Object> findHqlObject(final String hql, final Map<String, Object> paraMap) {
		Assert.hasText(hql, "对象不能为空！");
		return createQueryObject(hql, paraMap).getResultList();
	}

	/**
	 * HQL查询
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	public Object findHqlAObject(final String hql, final Object... values) {
		List<Object> list = findHqlObject(hql, values);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * HQL查询
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	public Object findHqlAObject(final String hql, final Map<String, Object> paraMap) {
		List<Object> list = findHqlObject(hql, paraMap);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
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
	public List<T> findPage(final String hql, final int from, final int size, final Object... values) {
		Assert.hasText(hql, "对象不能为空！");
		return createQuery(hql, values).setFirstResult(from).setMaxResults(size).getResultList();
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
	public List<T> findPage(final String hql, final int from, final int size, final Map<String, Object> paraMap) {
		Assert.hasText(hql, "对象不能为空！");
		return createQuery(hql, paraMap).setFirstResult(from).setMaxResults(size).getResultList();
	}

	/**
	 * 创建Query对象
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	public Query<T> createQuery(String hql, Object... values) {
		Assert.hasText(hql, "对象不能为空！");
		Query<T> query = this.currentSession().createQuery(hql, entityClass);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * 创建Query对象
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Query<T> createQuery(String hql, Map<String, Object> paraMap) {
		Assert.hasText(hql, "对象不能为空！");
		Query<T> query = this.currentSession().createQuery(hql, entityClass);
		if (paraMap != null) {
			for (String key : paraMap.keySet()) {
				Object para = paraMap.get(key);
				if (para instanceof List) {
					query.setParameterList(key, (List<Object>) para);
				} else {
					query.setParameter(key, para);
				}
			}
		}
		return query;
	}

	/**
	 * 创建Query对象
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	public Query<Object> createQueryObject(String hql, Object... values) {
		Assert.hasText(hql, "对象不能为空！");
		Query<Object> query = this.currentSession().createQuery(hql, Object.class);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * 创建Query对象
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Query<Object> createQueryObject(String hql, Map<String, Object> paraMap) {
		Assert.hasText(hql, "对象不能为空！");
		Query<Object> query = this.currentSession().createQuery(hql, Object.class);
		if (paraMap != null) {
			for (String key : paraMap.keySet()) {
				Object para = paraMap.get(key);
				if (para instanceof List) {
					query.setParameterList(key, (List<Object>) para);
				} else {
					query.setParameter(key, para);
				}
			}
		}
		return query;
	}

}