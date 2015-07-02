package pams.dao.Impl;

import java.util.List;

import org.springframework.stereotype.Component;

import pams.dao.TPowerDao;
import pams.model.TPower;

/**
 * @author cjl
 *实现权限操作：权限记录表格TPower的操作不交给前台操作
 */
@Component("tpowerDaoImpl")
public class TPowerDaoImpl extends SuperImpl implements TPowerDao 
{
	/**
	 * parentId=0 ,取一节节点
	 * parentId=-1，取出所有节点
	 * 以外，取出该节点的所有子节点
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TPower> list(int parentId) 
	{
		if(parentId == 0)
		{
			return (List<TPower>)this.getHibernateTemplate().find("select o from TPower o where o.parent is null");
		}
		else if(parentId==-1)
		{
			return (List<TPower>)this.getHibernateTemplate().find("from TPower");
		}
		String hql = "select o from TPower o where o.parent.id = ?";
		return (List<TPower>)this.getHibernateTemplate().find(hql, parentId);
	}
}