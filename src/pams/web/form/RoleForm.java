package pams.web.form;
/**
 * @author cjl
 * web用户角色需要的model
 */
public class RoleForm 
{
	private Integer id;
	private String name;
	private String des;
	private boolean check=false;
	
	public Integer getId() 
	{
		return id;
	}
	public void setId(Integer id) 
	{
		this.id = id;
	}
	
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
	
	public boolean isCheck()
	{
		return check;
	}
	public void setCheck(boolean check) 
	{
		this.check = check;
	}
}