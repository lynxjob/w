package pams.model;
/**
 * @author cjl
 * 备份的数据库文件信息，不存数据库
 */
public class DbInfo 
{
	private String name;
	private String size;
	private String cdate;
	
	public String getName() 
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public String getSize() 
	{
		return size;
	}
	public void setSize(String size) 
	{
		this.size = size;
	}
	
	public String getCdate()
	{
		return cdate;
	}
	public void setCdate(String cdate)
	{
		this.cdate = cdate;
	}
}