package pams.dao.Impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Entity;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import pams.dao.SuperDao;

/**
 * @author cjl
 * 实现抽象接口的公用方法
 */
@Component
public class SuperImpl implements SuperDao
{
	private HibernateTemplate hibernateTemplate; 
	
	public HibernateTemplate getHibernateTemplate() 
	{
		return hibernateTemplate;
	}
	//spring注入hibernateTemplate
	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) 
	{
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public void save(Object entity) 
	{
		this.getHibernateTemplate().save(entity);
	}

	@Override
	public void update(Object entity) 
	{
		this.getHibernateTemplate().update(entity);
	}

	@Override
	public <T> void delete(Class<T> entityClass, int entityId) 
	{
		this.getHibernateTemplate().delete(this.get(entityClass,entityId));
	}

	//删除多个对象，ids字符串
	@Override
	public <T> void delete(Class<T> entityClass,String idsStr) 
	{
		String entityName=this.getEntityName(entityClass);
		final String sqlStr = "delete from "+entityName+" where id in (" + idsStr+")";
		System.out.println(sqlStr);
		this.getHibernateTemplate().execute(new HibernateCallback(){
			@Override
			public T doInHibernate(Session session) throws HibernateException,SQLException {
				int rowCount = session.createQuery(sqlStr).executeUpdate();
				System.out.println("删除行数:"+rowCount);
				return null;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Class<T> entityClass, int entityId)
	{
		return (T) this.getHibernateTemplate().get(entityClass,entityId);
	}
	
	//记录总数
	@Override
	public <T> Long getTotal(Class<T> entityClass)
	{
		String entityName=this.getEntityName(entityClass);
		final String sqlStr="select count(*) from "+entityName;
		return (Long) this.getHibernateTemplate().execute(new HibernateCallback(){
			@Override
			public Long doInHibernate(Session session) throws HibernateException,SQLException {
				Long rowCount = (Long) session.createQuery(sqlStr).uniqueResult();
				return rowCount;
			}
		});
	}
	
	//获取分页数据，实体class，start，limit，排列依据
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> list(Class<T> entityClass,final int start,final int limit,String orderBy) 
	{
		String entityName=this.getEntityName(entityClass);
		final String sqlStr="from "+entityName+" u "+orderBy;
		//System.out.println(sqlStr);
		return this.getHibernateTemplate().executeFind(new HibernateCallback(){
			public List<T> doInHibernate(Session session)throws HibernateException {
				Query query = session.createQuery(sqlStr);
				query.setFirstResult(start);
				query.setMaxResults(limit);
				return query.list();
			}
		});
	}
	
	@Override
	public <T> List<T> list(Class<T> entityClass,final int start,final int limit) 
	{
		return this.list(entityClass, start, limit,"");
	}
	
	//根据entityClass获取实体类的名字
	protected <T> String getEntityName(Class<T> entityClass)
	{
		String entityName=entityClass.getSimpleName();
		Entity entity=entityClass.getAnnotation(Entity.class);
		if(entity.name()!=null && !"".equals(entity.name()))
		{
			entityName=entity.name();
		}
		return entityName;
	}
}