package pams.dao.Impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import pams.dao.AssistFunctionDao;
import pams.model.AssistFunction;
@Component("assistFunctionDaoImpl")
public class AssistFunctionDaoImpl extends SuperImpl implements AssistFunctionDao{
	/*
	 * parentId=0,表示没有父节点
	 * */
	@Override
	public void save(AssistFunction function, int parentId) {
		if(parentId!=0)
		{
			function.setParent((AssistFunction) this.getHibernateTemplate().get(AssistFunction.class,parentId));
		}
		this.getHibernateTemplate().save(function);
	}
	@Override
	public String getJsonString() {
		StringBuffer jsonString=new StringBuffer();
		jsonString.append("[");
		List<AssistFunction> orgs=this.list(0);
		for(AssistFunction org : orgs)
		{
			jsonString.append("{id:'"+org.getId()+"',text:'"+org.getName());
			this.toJsonStr(org,jsonString);
		}
		jsonString.deleteCharAt(jsonString.length()-1);
		jsonString.append("]");
		return jsonString.toString();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<AssistFunction> list(int parentId) {
		if(parentId == 0)
		{
			return (List<AssistFunction>)this.getHibernateTemplate().find("select s from AssistFunction s where s.parent is null");
		}
		else if(parentId==-1)
		{
			return (List<AssistFunction>)this.getHibernateTemplate().find("from AssistFunction");
		}
		String hql = "select s from AssistFunction s where s.parent.id = ?";
		return (List<AssistFunction>)this.getHibernateTemplate().find(hql, parentId);
	}
	/**
	 * 递归，遍历树，拼Json串
	 * @param org
	 * @param orgsJson
	 */
	private void toJsonStr(AssistFunction org,StringBuffer jsonString)
	{
		if(org.getChildren().size()==0)
		{
			jsonString.append("',leaf:true},");
		}
		else
		{
			Object[] orgs= org.getChildren().toArray();
			AssistFunction temp=null;
			for(int i=0;i< orgs.length;i++)
			{
				temp=(AssistFunction)orgs[i];
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
	public Long getCount(int parentId) {
		final StringBuffer sb=new StringBuffer();;
		if(parentId==0){
			sb.append("select count(*) from AssistFunction s where s.parent is null");
		}
		else{
			sb.append("select count(*) from AssistFunction s where s.parent.id="+parentId);
		}
		return (Long) this.getHibernateTemplate().execute(new HibernateCallback(){
			@Override
			public Long doInHibernate(Session session) throws HibernateException,SQLException {
				Long rowCount = (Long) session.createQuery(sb.toString()).uniqueResult();
				return rowCount;
			}
		});
	}

	@Override
	public boolean checkExistByName(String name) {
		boolean result = false;
		List<AssistFunction> assistfunctions = this.getHibernateTemplate().find("from AssistFunction s where s.name = '"+name+"'");
		if(assistfunctions!=null && assistfunctions.size()>0)
			result = true;
		
		return result;
	}

	@Override
	public boolean delete(int entityId) {
		AssistFunction function=(AssistFunction) this.getHibernateTemplate().get(AssistFunction.class,entityId);
		if(function.getChildren().size()>0)
		{
			//存在子节点不予删除
			System.out.println("存在子节点,不予删除！");
			return false;
		}
		else
		{
			this.getHibernateTemplate().delete(function.getChildren());
			return true;
		}
	}
	@Override
	public String list() {
		StringBuffer sb =  new StringBuffer();
		sb.append("[");
		List<AssistFunction> afs = list(0);
		for(AssistFunction af : afs){
			sb.append("{id:'"+af.getId()+"',text:'"+af.getName()+"',");
			Set<AssistFunction> children = af.getChildren();
			if(children.size()>0){
				sb.append("children:[");
			}
			for(AssistFunction child : children){
				sb.append(child.getValue()+",");
			}
			if(children.size()>0){
				sb.deleteCharAt(sb.length()-1);
				sb.append("]},");
			}else{
				sb.deleteCharAt(sb.length()-1);
				sb.append("},");
			}
		}
		if(afs.size()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("]");
		return sb.toString();
	}

	
}
