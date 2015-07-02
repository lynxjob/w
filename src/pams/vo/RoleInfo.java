package pams.vo;
/**
 * @author cjl
 * 角色web元素
 */
public class RoleInfo extends BaseInfo
{
	private String name;
	private String des;
	
	public String getName() 
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getDes() 
	{
		return des;
	}
	public void setDes(String des) 
	{
		this.des = des;
	}
}