package pams.dao.Impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import pams.dao.ShedTypeDao;
import pams.model.ShedType;

/**
 * @author cjl
 * 实现树形结构的方法
 */
@Component("shedTypeDaoImpl")
public class ShedTypeDaoImpl extends SuperImpl implements ShedTypeDao
{
	/**
	 * parentId=0，没有父节点
	 */
	@Override
	public void save(ShedType shed, int parentId)
	{
		if(parentId!=0)
		{
			shed.setParent((ShedType) this.getHibernateTemplate().get(ShedType.class,parentId));
		}
		this.getHibernateTemplate().save(shed);
	}

	@Override
	public boolean delete(int entityId) 
	{
		ShedType shed=(ShedType) this.getHibernateTemplate().get(ShedType.class,entityId);
		if(shed.getChildren().size()>0)
		{
			//存在子节点不予删除
			System.out.println("存在子节点,不予删除！");
			return false;
		}
		else if(shed.getParent()!=null)
		{
			//解除关联关系
			shed.getParent().getChildren().remove(shed);
			shed.setParent(null);
			this.getHibernateTemplate().delete(shed);
			return true;
		}else{
			this.getHibernateTemplate().delete(shed);
			return true;
		}
	}

	/**
	 * parentId=0 ,取一节节点
	 * parentId=-1，取出所有节点
	 * 其他正常
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ShedType> list(int parentId) 
	{
		if(parentId == 0)
		{
			return (List<ShedType>)this.getHibernateTemplate().find("select s from ShedType s where s.parent is null");
		}
		else if(parentId==-1)
		{
			return (List<ShedType>)this.getHibernateTemplate().find("from ShedType");
		}
		String hql = "select s from ShedType s where s.parent.id = ?";
		return (List<ShedType>)this.getHibernateTemplate().find(hql, parentId);
	}

	/**
	 * 数据库获取一级目录，获取整棵树的json字符串
	 */
	@Override
	public String getJsonString() 
	{
		StringBuffer jsonString=new StringBuffer();
		jsonString.append("[");
		List<ShedType> orgs=this.list(0);
		for(ShedType org : orgs)
		{
			jsonString.append("{id:'"+org.getId()+"',text:'"+org.getName());
			this.toJsonStr(org,jsonString);
		}
		jsonString.deleteCharAt(jsonString.length()-1);
		jsonString.append("]");
		return jsonString.toString();
	}
	/**
	 * 递归，遍历树，拼Json串
	 * @param org
	 * @param orgsJson
	 */
	private void toJsonStr(ShedType org,StringBuffer jsonString)
	{
		if(org.getChildren().size()==0)
		{
			jsonString.append("',leaf:true},");
		}
		else
		{
			Object[] orgs= org.getChildren().toArray();
			ShedType temp=null;
			for(int i=0;i< orgs.length;i++)
			{
				temp=(ShedType)orgs[i];
				if(i==0)
				{
					jsonString.append("',children:[{id:'"+temp.getId()+"',text:'"+temp.getName());
					this.toJsonStr(temp,jsonString);
				}
				else 
				{
					jsonString.append("{id:'"+temp.getId()+"',text:'"+temp.getName());
					this.toJsonStr(temp,jsonString);
				}
			}
			jsonString.deleteCharAt(jsonString.length()-1);
			jsonString.append("]},");
		}
	}

	@Override
	public Long getCount(int parentId) 
	{
		final StringBuffer sb=new StringBuffer();;
		if(parentId==0){
			sb.append("select count(*) from ShedType s where s.parent is null");
		}
		else{
			sb.append("select count(*) from ShedType s where s.parent.id="+parentId);
		}
		return (Long) this.getHibernateTemplate().execute(new HibernateCallback(){
			@Override
			public Long doInHibernate(Session session) throws HibernateException,SQLException {
				Long rowCount = (Long) session.createQuery(sb.toString()).uniqueResult();
				return rowCount;
			}
		});
	}

	/**
	 * 检查是否重名
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean checkExistByName(String name) {
		boolean result = false;
		List<ShedType> shedtypes = this.getHibernateTemplate().find("from ShedType s where s.name = '"+name+"'");
		if(shedtypes!=null && shedtypes.size()>0)
			result = true;
		
		return result;
	}
}