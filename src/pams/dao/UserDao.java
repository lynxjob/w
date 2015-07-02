package pams.dao;

import java.util.List;

import pams.model.User;
import pams.model.UserRoles;

/**
 * @author cjl
 * 用户DAO
 */
public interface UserDao extends SuperDao 
{
	public void delete(String ids);
	/**
	 * 根据shedId获取用户列表
	 * 0获取全部；其他正常获取
	 */
	public List<User> list(int start,int limit,int shedId);
	/**
	 * 根据greenHouseId获取用户数量
	 */
	public Long getTotal(int shedId);
	/**
	 * 添加用户角色
	 */
	public void addUserRole(int userId,int roleId);
	
	/**
	 * 删除用户某个用户角色
	 */
	public void deleteUserRole(int userId,int roleId);
	
	/**
	 * 列出用户的所有角色，List<UserRoles>
	 */
	public List<UserRoles> listUserRoles(int userId);
	
	/**
	 * 列出用户所有角色的id，List<int>
	 */
	public List<Integer> listUserRoleIds(int userId);
	
	/**
	 * 根据name获取user，用于登录
	 * 存在返回user对象；不存在或禁用用户，返回null
	 */
	public User getUserByName(String name);
	
	/**
	 * 判断用户名是否存在
	 * 存在，返回true；不存在，false
	 */
	public boolean checkUserExistsByName(String name);
	
	/**
	 * 添加用户的一条额外权限
	 */
	public void addUserPower(int userId,int powerId);
	
	/**
	 * 删除用户的一条额外权限
	 */
	public void deleteUserPower(int userId,int powerId);
	
	/**
	 * 获取单个用户拥有全部额外权限
	 */
	public List<Integer> listUserPowerIds(int userId);
	
	/**
	 * 根据用户Id生成菜单，获取所有菜单String
	 */
	public List<String> listMenus(int userId);
	
	/**
	 * 根据用户Id生成按钮，获取所有控制按钮显示的String
	 */
	public List<String> listButtons(int userId);
	
	
	/**
	 * 仅仅测试
	 */
	public void temp(int userId);
	/**
	 * save user who has those shed
	 */
}