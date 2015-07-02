package pams.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import pams.dao.RoleDao;
import pams.model.Role;
import pams.web.form.RoleForm;

/**
 * @author cjl
 * 角色的业务逻辑实现
 */
@Component("roleManager")
public class RoleManager
{
	private RoleDao roleDao;

	@Resource(name="roleDaoImpl")
	public void setRoleDao(RoleDao roleDao)
	{
		this.roleDao = roleDao;
	}
	
	/**
	 * 角色CRUD常用操作
	 */
	public List<Role> list(int start,int limit)
	{
		return this.roleDao.list(Role.class,start,limit);
	}
	public Long getTotal()
	{
		return this.roleDao.getTotal(Role.class);
	}
	public void save(Role role)
	{
		this.roleDao.save(role);
	}
	public void delete(String idsStr)
	{
		String temp=(String) idsStr.subSequence(1,idsStr.length()-1);
		this.roleDao.delete(Role.class,temp);
	}
	public Role get(int entityId)
	{
		return this.roleDao.get(Role.class,entityId);
	}
	public void update(Role role)
	{
		this.roleDao.update(role);
	}
	
	/**
	 * 获取所有角色的信息，用于Ext Grid呈现
	 */
	public List<RoleForm> getUserRoles(int start,int limit)
	{
		return this.roleDao.getUserRoles(start,limit);
	}
	
	/**
	 * 添加和删除角色对应的1条权限记录
	 */
	public void addRolePower(int roleId, int powerId)
	{
		this.roleDao.addRolePower(roleId,powerId);
	}
	public void deleteRolePower(int roleId, int powerId)
	{
		this.roleDao.deleteRolePower(roleId,powerId);
	}
	public List<Integer> getRolePowerIds(int roleId)
	{
		return this.roleDao.listRolePowerIds(roleId);
	}
}