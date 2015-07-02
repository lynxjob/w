package pams.vo;
/**
 * @author cjl
 * 对应web表单的数据
 * 不需要分页,故没有从BaseInfo继承
 */
public class ShedTypeInfo 
{
	private Integer id;
	private String name;
	private String des;
	private int parentId=0;
	
	public int getId()
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
	
	public int getParentId() 
	{
		return parentId;
	}
	public void setParentId(int parentId)
	{
		this.parentId = parentId;
	}
}