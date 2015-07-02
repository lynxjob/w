package pams.dao.Impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import pams.dao.ShedDao;
import pams.model.Shed;
import pams.model.ShedType;
import pams.model.User;
/**
 * 大棚Dao实现
 * @author 恶灵骑士
 *
 */
@Component("shedDaoImpl")
public class ShedDaoImpl extends SuperImpl implements ShedDao {
	/**
	 * 某大棚类型下的大棚数量
	 */
	@Override
	public Long getCount(final int shedTypeId) {
		//shedTypeId == 0 所有大棚类型下的大棚数量
		//shadTypeId != 0 相应大棚类型下的大棚数量
		return (Long)this.getHibernateTemplate().execute(new HibernateCallback(){

			@Override
			public Long doInHibernate(Session session)
					throws HibernateException, SQLException {
				String hql = "";
				if(shedTypeId == 0){
					hql = "select count(*) from Shed";
				}else{
					hql = "select count(*) from Shed s where s.shedType.id = "+shedTypeId;	
				}
				Long rowCount = (Long)session.createQuery(hql).uniqueResult();
				return rowCount;
			}
			
		});
	}
	/**
	 * 某大棚类型下的大棚列表
	 * shedTypeId == 0 取出所有大棚类型下的大棚信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Shed> list(int shedTypeId) {
		if(shedTypeId == 0){
			return this.getHibernateTemplate().find("from Shed");
		}
		String hql = "select s from Shed s where s.shedType.id = ?";
		return this.getHibernateTemplate().find(hql,shedTypeId);
	}
	/**
	 * 检查是否重名
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean checkExistByName(String name) {
		boolean result = false;
		List<Shed> sheds = this.getHibernateTemplate().find("from Shed s where s.name = '"+name+"'");
		if(sheds!=null && sheds.size()>0)
			result = true;
		
		return result;
	}
	/**
	 * 检查是否重名
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean checkExistByName(int shedId,String name) {
		boolean result = false;
		List<Shed> sheds = this.getHibernateTemplate().find("from Shed s where s.name = '"+name+"' and s.id != "+shedId);
		if(sheds!=null && sheds.size()>0)
			result = true;
		
		return result;
	}
	/**
	 * 获取整棵shed树的json字符串
	 * checked:true 用于用户账户模块
	 * checked:false 用于大棚监测模块
	 */
	@Override
	public String getJsonString(List<Shed> sheds,boolean checked) {
		StringBuffer jsonString = new StringBuffer();
		jsonString.append("[");
		Set<ShedType> shedTypes = this.getShedType(sheds);
		for(ShedType shedType : shedTypes){
			jsonString.append("{id:'"+shedType.getId()+"',text:'"+shedType.getName());
			this.toJsonStr(shedType, jsonString,sheds,checked);
		}
		
		jsonString.deleteCharAt(jsonString.length()-1);
		jsonString.append("]");
		
		return jsonString.toString();
	}
	//根据大棚列表查找相应的大棚类型列表
	private Set<ShedType> getShedType(List<Shed> sheds){
		Set<ShedType> shedTypes = new HashSet<ShedType>();
		for(Shed shed : sheds){
			shedTypes.add(shed.getShedType());
		}
		return shedTypes;
	}
	//根据大棚类型查找相应的大棚列表
	private List<Shed> getShed(ShedType shedType,List<Shed> oldSheds){
		if(oldSheds == null)
			return null;
		List<Shed> sheds = new ArrayList<Shed>();
		for(Shed shed : oldSheds){
			if(shed.getShedType() == shedType)
				sheds.add(shed);
		}
		return sheds;
	}
	//拼接字符串
	private void toJsonStr(ShedType shedType,StringBuffer jsonString,List<Shed> oldSheds,boolean checked){
		if(shedType.getChildren().size()==0)
		{
			
			List<Shed> sheds = getShed(shedType,oldSheds);
			if(sheds != null && sheds.size()>0){
				for(int i=0; i< sheds.size(); i++){
					Shed shed = sheds.get(i);
					if(i==0){
						if(checked){
							jsonString.append("',children:[{id:'"+shed.getId()+"',text:'"+shed.getName()+"',leaf:true,checked:false},");
						}else{
							jsonString.append("',children:[{id:'"+shed.getId()+"',text:'"+shed.getName()+"',leaf:true},");
						}
					}	
					else {
						if(checked){
							jsonString.append("{id:'"+shed.getId()+"',text:'"+shed.getName()+"',leaf:true,checked:false},");
						}else{
							jsonString.append("{id:'"+shed.getId()+"',text:'"+shed.getName()+"',leaf:true},");
						}				
					}
				}
				jsonString.deleteCharAt(jsonString.length()-1);
				jsonString.append("]},");
			}else{
				jsonString.append("',leaf:true},");
			}
			
		}
		else
		{
			Object[] orgs= shedType.getChildren().toArray();
			ShedType temp=null;
			for(int i=0;i< orgs.length;i++)
			{
				temp=(ShedType)orgs[i];
				if(i==0)
				{
					jsonString.append("',children:[{id:'"+temp.getId()+"',text:'"+temp.getName());
					this.toJsonStr(temp,jsonString,oldSheds,checked);
				}
				else 
				{
					jsonString.append("{id:'"+temp.getId()+"',text:'"+temp.getName());
					this.toJsonStr(temp,jsonString,oldSheds,checked);
				}
			}
			jsonString.deleteCharAt(jsonString.length()-1);
			jsonString.append("]},");
		}
	}
	
	
	/**
	 * 获取某用户所管理的大棚列表
	 * @param userId 用户ID
	 * @return 大棚列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Shed> loadByUserId(int userId) {
		List<User> users =  this.getHibernateTemplate().find("from User where id = ?",userId);
		List<Shed> sheds = new ArrayList<Shed>();
		if(users!=null && users.size()>0){
			for(User u : users){
				if(u.getSheds().size()>0){
					for(Shed shed : u.getSheds())
					sheds.add(shed);
				}
			}
		}
		return sheds;
	}
}

