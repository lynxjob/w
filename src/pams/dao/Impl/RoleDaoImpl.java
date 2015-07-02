package pams.dao.Impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import pams.dao.RoleDao;
import pams.model.PowerUsersRoles;
import pams.model.Role;
import pams.model.TPower;
import pams.web.form.RoleForm;

/**
 * @author cjl
 * 角色Dao的实现
 */
@Component("roleDaoImpl")
public class RoleDaoImpl extends SuperImpl implements RoleDao
{
	/**
	 * 获取所有角色，用于Ext Grid的形式
	 */
	@Override
	public List<RoleForm> getUserRoles(int start,int limit) 
	{
		List<Role> roles=this.list(Role.class,start,limit);
		List<RoleForm> roleForms=new ArrayList<RoleForm>();
		RoleForm temp=null;
		for(Role role : roles)
		{
			temp=new RoleForm();
			temp.setId(role.getId());
			temp.setName(role.getName());
			temp.setDes(role.getDes());
			roleForms.add(temp);
		}
		return roleForms;
	}

	@Override
	public void addRolePower(int roleId, int powerId)
	{
		PowerUsersRoles pur=new PowerUsersRoles();
		pur.setPower((TPower)this.getHibernateTemplate().get(TPower.class,powerId));
		pur.setFlag(1);
		pur.setObjectId(roleId);
		
		this.getHibernateTemplate().save(pur);
	}

	@Override
	public void deleteRolePower(int roleId, int powerId) 
	{
		this.getHibernateTemplate().delete(this.getRolePowerByPOId(roleId,powerId));
	}

	/**
	 * 列出角色的权限记录集合
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> listRolePowerIds(int roleId) 
	{
		return (List<Integer>)this.getHibernateTemplate().find("select pur.power.id from PowerUsersRoles pur where pur.objectId = ? and flag=1", roleId);
	}
	
	/**
	 * 根据角色ID和权限值ID获取PowerUserRoles对象
	 * 标识变量flag=1
	 */
	private PowerUsersRoles getRolePowerByPOId(final int roleId, final int powerId)
	{
		return (PowerUsersRoles) this.getHibernateTemplate().execute(new HibernateCallback(){
			@Override
			public PowerUsersRoles doInHibernate(Session session) throws HibernateException,SQLException {
				PowerUsersRoles pur=(PowerUsersRoles)session.createQuery(
					"select pur from PowerUsersRoles pur where pur.objectId = ? and pur.power.id = ? and flag=1")
					.setParameter(0,roleId)
					.setParameter(1, powerId)
					.uniqueResult();
				return pur;
			}
		});
	}
}