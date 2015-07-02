package pams.dao;

import java.util.List;

import pams.web.form.RoleForm;

/**
 * 角色Dao
 */
public interface RoleDao extends SuperDao 
{
	/**
	 * 角色web表格数据
	 */
	public List<RoleForm> getUserRoles(int start,int limit);
	
	/**
	 * 给角色添加一条权限记录
	 */
	public void addRolePower(int roleId,int powerId);
	
	/**
	 * 删除角色的一条权限记录
	 */
	public void deleteRolePower(int roleId,int powerId);
	
	/**
	 * 获取单个角色的所有权限
	 */
	public List<Integer> listRolePowerIds(int roleId);
}