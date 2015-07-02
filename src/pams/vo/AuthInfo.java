package pams.vo;
/**
 * @author cjl
 * 权限web数据
 */
public class AuthInfo extends BaseInfo 
{
	private Integer userId;
	private Integer roleId;
	private Integer powerId;
	
	public Integer getUserId() 
	{
		return userId;
	}
	public void setUserId(Integer userId)
	{
		this.userId = userId;
	}
	
	public Integer getRoleId()
	{
		return roleId;
	}
	public void setRoleId(Integer roleId)
	{
		this.roleId = roleId;
	}
	
	public Integer getPowerId() 
	{
		return powerId;
	}
	public void setPowerId(Integer powerId)
	{
		this.powerId = powerId;
	}
}