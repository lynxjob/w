package pams.vo;
/**
 * @author cjl
 * 主页操作web传值
 */
public class ManageInfo 
{
	private String username;
	private String password;
	private String oldPassword;
	private String newPassword;
	
	public String getUsername() 
	{
		return username;
	}
	public void setUsername(String username) 
	{
		this.username = username;
	}
	
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public String getOldPassword()
	{
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) 
	{
		this.oldPassword = oldPassword;
	}
	
	public String getNewPassword()
	{
		return newPassword;
	}
	public void setNewPassword(String newPassword)
	{
		this.newPassword = newPassword;
	}
}