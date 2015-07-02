package pams.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import pams.dao.UserDao;
import pams.model.User;
import pams.model.UserRoles;

/**
 * @author cjl
 * 用户操作
 */
@Component("userManager")
public class UserManager
{
	private UserDao userDao;
	
	@Resource(name="userDaoImpl")
	public void setUserDao(UserDao userDao) 
	{
		this.userDao = userDao;
	}
	
	/**
	 * 用户CRUD常用操作
	 */
	public List<User> list(int start,int limit,int orgId)
	{
		return this.userDao.list(start, limit,orgId);
	}
	public Long getTotal(int orgId)
	{
		return this.userDao.getTotal(orgId);
	}
	public void save(User user)
	{
		this.userDao.save(user);
	}
	public void delete(int entityId)
	{
		this.userDao.delete(User.class, entityId);
	}
	public void delete(String idsStr)
	{
		String temp=(String) idsStr.subSequence(1,idsStr.length()-1);
		this.userDao.delete(temp);
	}
	public User get(int entityId)
	{
		return this.userDao.get(User.class,entityId);
	}
	public void update(User user)
	{
		this.userDao.update(user);
	}
	
	/**
	 * 检验用户名是否存在
	 */
	public boolean checkUserExistsByName(String name)
	{
		return this.userDao.checkUserExistsByName(name);
	}
	/**
	 * 根据用户名获取用户对象
	 */
	public User getUserByName(String name)
	{
		return this.userDao.getUserByName(name);
	}
	
	/**
	 * 添加，删除用户的角色
	 */
	public void addUserRole(int userId, int roleId)
	{
		this.userDao.addUserRole(userId,roleId);
	}
	public void deleteUserRole(int userId,int roleId)
	{
		this.userDao.deleteUserRole(userId, roleId);
	}
	
	/**
	 * 根据用户Id获取角色列表信息
	 */
	public List<UserRoles> getUserRoles(int userId)
	{
		return this.userDao.listUserRoles(userId);
	}
	public List<Integer> getUserRoleIds(int userId)
	{
		return this.userDao.listUserRoleIds(userId);
	}
	
	/**
	 * 添加和删除，用户权限的1条记录
	 */
	public void addUserPower(int userId, int powerId)
	{
		this.userDao.addUserPower(userId,powerId);
	}
	public void deleteUserPower(int userId, int powerId)
	{
		this.userDao.deleteUserPower(userId,powerId);
	}
	public List<Integer> getUserPowerIds(int userId)
	{
		return this.userDao.listUserPowerIds(userId);
	}
	
	
	/**
	 * 根据用户Id生成菜单，获取所有菜单String
	 */
	public List<String> listMenus(int userId)
	{
		return this.userDao.listMenus(userId);
	}
	
	/**
	 * 根据用户Id生成按钮，获取所有控制按钮显示的String
	 */
	public List<String> listButtons(int userId)
	{
		return this.userDao.listButtons(userId);
	}
	/**
	 * json string
	 */
	public String getJsonString(List<User> users){
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(User user : users){
			sb.append(user.toString());
		}
		if(users.size()>0)
		sb.deleteCharAt(sb.length()-1);
		sb.append("]");
		return sb.toString();
	}
}