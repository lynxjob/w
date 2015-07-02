package pams.dao.Impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import pams.dao.UserDao;
import pams.model.PowerUsersRoles;
import pams.model.Role;
import pams.model.TPower;
import pams.model.User;
import pams.model.UserRoles;


/**
 * @author cjl
 * 用户DAO实现
 */
@SuppressWarnings("unchecked")
@Component("userDaoImpl")
public class UserDaoImpl extends SuperImpl implements UserDao 
{
	public void delete(String idsStr){
		for(String ids : idsStr.split(",")){
			int id = Integer.parseInt(ids);
			this.delete(User.class, id);
		}
	}
	/**
	 * 根据shedId获取用户
	 * shedId=0:获取全部用户
	 * shedId!=0:获取相应大棚Id的用户
	 */
	@Override
	public List<User> list(final int start, final int limit, final int shedId) 
	{
		final StringBuffer sb=new StringBuffer();
		if(shedId==0){
			sb.append("from User u");
		}else{
//			sb.append("select u from User as u inner join fetch u.sheds as s where s.id = "+shedId); //after hibernate 3.2
			sb.append("select u from User u inner join u.sheds s where s.id = "+shedId);
		}
	
		return 
			this.getHibernateTemplate().executeFind(new HibernateCallback(){
				public List<User> doInHibernate(Session session)throws HibernateException {
					Query query = session.createQuery(sb.toString());
					query.setFirstResult(start);
					query.setMaxResults(limit);
					return query.list();
				}
			});
	}
	
	/**
	 * 获取某个机构人员的数量
	 * shedId=0：获取全部人员数量
	 * shedId!=0：获取相应大棚ID人员数量
	 */
	@Override
	public Long getTotal(int shedId) 
	{
		final StringBuffer sb=new StringBuffer();;
		if(shedId==0){
			sb.append("select count(*) from User u");
		}
		else{
			//can't add fetch
			sb.append("select count(*) from User u inner join u.sheds s where s.id = "+shedId);
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
	 * 根据userId和roleId，拿到角色-用户的记录
	 */
	private UserRoles getUserRoles(final int userId,final int roleId)
	{
		return (UserRoles) this.getHibernateTemplate().execute(new HibernateCallback(){
			@Override
			public UserRoles doInHibernate(Session session) throws HibernateException,SQLException {
				UserRoles userRoles=(UserRoles)session.createQuery(
					"select ur from UserRoles ur where ur.role.id = ? and ur.user.id = ?")
					.setParameter(0, roleId)
					.setParameter(1, userId)
					.uniqueResult();
				return userRoles;
			}
		});
	}
	@Override
	public void addUserRole(int userId, int roleId) 
	{
		UserRoles ur=new UserRoles();
		ur.setRole((Role)this.getHibernateTemplate().get(Role.class,roleId));
		ur.setUser((User)this.getHibernateTemplate().get(User.class,userId));
		this.getHibernateTemplate().save(ur);
	}	

	@Override
	public void deleteUserRole(int userId, int roleId) 
	{
		this.getHibernateTemplate().delete(this.getUserRoles(userId,roleId));
	}

	@Override
	public List<UserRoles> listUserRoles(int userId)
	{
		return (List<UserRoles>)this.getHibernateTemplate().find("select ur from UserRoles ur where ur.user.id = ?", userId);
	}
	
	@Override
	public List<Integer> listUserRoleIds(int userId) 
	{
		return (List<Integer>)this.getHibernateTemplate().find("select ur.role.id from UserRoles ur where ur.user.id = ?", userId);
	}

	@Override
	public User getUserByName(String name) 
	{
		User user=null;
		List<User> users = this.getHibernateTemplate().find("from User u where u.name = '" + name + "' and u.state=1");
		if(users.size()>0)
		{
			user=users.get(0);
		}
		return user;
	}

	
	@Override
	public boolean checkUserExistsByName(String name) 
	{
		List<User> users = (List<User>)this.getHibernateTemplate().find("from User u where u.name = '" + name + "'");
		if(users != null && users.size() > 0) 
		{
			return true;
		}
		return false;
	}
	
	@Override
	public void addUserPower(int userId, int powerId) 
	{
		PowerUsersRoles pur=new PowerUsersRoles();
		pur.setPower((TPower)this.getHibernateTemplate().get(TPower.class,powerId));
		pur.setFlag(0);
		pur.setObjectId(userId);
		
		this.getHibernateTemplate().save(pur);
	}
	@Override
	public void deleteUserPower(int userId, int powerId) 
	{
		this.getHibernateTemplate().delete(this.getUserPowerByPOId(userId,powerId));
	}
	
	/**
	 * 列出用户的权限值集合
	 */
	@Override
	public List<Integer> listUserPowerIds(int userId)
	{
		return (List<Integer>)this.getHibernateTemplate().find("select pur.power.id from PowerUsersRoles pur where pur.objectId = ? and flag=0", userId);
	}
	
	/**
	 * 根据权限值Id和用户Id，获取PowerUsersRoles对象
	 * 标识变量flag=0
	 */
	private PowerUsersRoles getUserPowerByPOId(final int userId, final int powerId)
	{
		return (PowerUsersRoles) this.getHibernateTemplate().execute(new HibernateCallback(){
			@Override
			public PowerUsersRoles doInHibernate(Session session) throws HibernateException,SQLException {
				PowerUsersRoles pur=(PowerUsersRoles)session.createQuery(
					"select pur from PowerUsersRoles pur where pur.objectId = ? and pur.power.id = ? and flag=0")
					.setParameter(0,userId)
					.setParameter(1, powerId)
					.uniqueResult();
				return pur;
			}
		});
	}
	
	
	/**
	 * 因hibernate不支持union查询，合并在代码中实现
	 */
	@Override
	public List<String> listMenus(final int userId) 
	{
		final String roleSql;
		List<String> roleMenus=new ArrayList<String>();
		final String userSql="select pur.power.value from PowerUsersRoles pur where pur.objectId=? and pur.flag=0 and pur.power.flag=1";
		List<String> userMenus=new ArrayList<String>();
		
		//用户拥有角色Ids
		List<Integer> roleIds=this.listUserRoleIds(userId);
		StringBuffer roleIdsStr=new StringBuffer();
		roleIdsStr.append("(");
		for(Integer roleId : roleIds)
		{
			roleIdsStr.append(roleId+",");
		}
		roleIdsStr.deleteCharAt(roleIdsStr.length()-1);
		roleIdsStr.append(")");
		
		//所有权限的菜单
		if(roleIdsStr.length()>1){
			roleSql="select distinct pur.power.value from PowerUsersRoles pur where pur.objectId in "+roleIdsStr.toString()+" and pur.flag=1 and pur.power.flag=1";
			roleMenus= 
				(List<String>) this.getHibernateTemplate().execute(new HibernateCallback(){
					@Override
					public List<String> doInHibernate(Session session) throws HibernateException,SQLException {
						return session.createQuery(roleSql).list();
					}
				});
		}
		
		//用户额外的菜单
		userMenus= 
			(List<String>) this.getHibernateTemplate().execute(new HibernateCallback(){
				@Override
				public List<String> doInHibernate(Session session) throws HibernateException,SQLException {
					return session.createQuery(userSql).setParameter(0,userId).list();
				}
			});
		
		if(roleMenus.size()>0){
			for(String roleMenu : roleMenus){
				if(!userMenus.contains(roleMenu)){
					userMenus.add(roleMenu);
				}
			}
		}
		
		return userMenus;
	}
	
	@Override
	public List<String> listButtons(final int userId) 
	{
		final String roleSql;
		List<String> roleButtons=new ArrayList<String>();
		final String userSql="select pur.power.value from PowerUsersRoles pur where pur.objectId=? and pur.flag=0 and pur.power.flag=2";
		List<String> userButtons=new ArrayList<String>();
		
		//用户拥有角色Ids
		List<Integer> roleIds=this.listUserRoleIds(userId);
		StringBuffer roleIdsStr=new StringBuffer();
		roleIdsStr.append("(");
		for(Integer roleId : roleIds)
		{
			roleIdsStr.append(roleId+",");
		}
		roleIdsStr.deleteCharAt(roleIdsStr.length()-1);
		roleIdsStr.append(")");
		
		//要判断roleIdsStr是否>1，有角色再下一步操作
		//所有角色的按钮
		if(roleIdsStr.length()>1){
			roleSql="select distinct pur.power.value from PowerUsersRoles pur where pur.objectId in "+roleIdsStr.toString()+" and pur.flag=1 and pur.power.flag=2";
			roleButtons=
				(List<String>) this.getHibernateTemplate().execute(new HibernateCallback(){
					@Override
					public List<String> doInHibernate(Session session) throws HibernateException,SQLException {
						return session.createQuery(roleSql).list();
					}
				});
		}
	
		//用户额外的按钮
		userButtons=
			(List<String>) this.getHibernateTemplate().execute(new HibernateCallback(){
				public List<String> doInHibernate(Session session) throws HibernateException,SQLException {
					return session.createQuery(userSql).setParameter(0,userId).list();
				}
		});
		
		if(roleButtons.size()>0){
			for(String roleButton : roleButtons){
				if(!userButtons.contains(roleButton)){
					userButtons.add(roleButton);
				}
			}
		}
		
		return userButtons;
	}
	
	@Override
	public void temp(final int userId) 
	{
		final String roleSql;
		List<String> roleMenus=null;
		final String userSql="select pur.power.value from PowerUsersRoles pur where pur.objectId=? and pur.flag=0 and pur.power.flag=1";
		List<String> userMenus=null;
		
		//用户拥有角色Ids
		List<Integer> roleIds=this.listUserRoleIds(userId);
		StringBuffer roleIdsStr=new StringBuffer();
		roleIdsStr.append("(");
		for(Integer roleId : roleIds)
		{
			roleIdsStr.append(roleId+",");
		}
		roleIdsStr.deleteCharAt(roleIdsStr.length()-1);
		roleIdsStr.append(")");
		
		if(roleIdsStr.length()>1){
			roleSql="select distinct pur.power.value from PowerUsersRoles pur where pur.objectId in "+roleIdsStr.toString()+" and pur.flag=1 and pur.power.flag=1";
			roleMenus= 
				(List<String>) this.getHibernateTemplate().execute(new HibernateCallback(){
					@Override
					public List<String> doInHibernate(Session session) throws HibernateException,SQLException {
						return session.createQuery(roleSql).list();
					}
				});
		}
		
		userMenus= 
			(List<String>) this.getHibernateTemplate().execute(new HibernateCallback(){
				@Override
				public List<String> doInHibernate(Session session) throws HibernateException,SQLException {
					return session.createQuery(userSql).setParameter(0,userId).list();
				}
			});
		
		for(String userMenu : userMenus){
			System.out.println(userMenu);
		}
		System.out.println("----------------------");
		for(String roleMenu : roleMenus){
			System.out.println(roleMenu);
		}
		
		if(roleMenus.size()>0){
			for(String roleMenu : roleMenus){
				if(!userMenus.contains(roleMenu)){
					userMenus.add(roleMenu);
				}
			}
		}
		for(String userMenu : userMenus){
			System.err.println(userMenu);
		}
	}

}