package pams.vo;
/**
 * @author cjl
 * 公用web元素:start，limit，id，ids
 */
public class BaseInfo 
{
	private Integer id;
	private int start;
	private int limit;
	private String ids;
	
	public Integer getStart() 
	{
		return start;
	}
	public void setStart(Integer start) 
	{
		this.start = start;
	}
	
	public int getLimit() 
	{
		return limit;
	}
	public void setLimit(int limit)
	{
		this.limit = limit;
	}
	
	public Integer getId() 
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	
	public String getIds()
	{
		return ids;
	}
	public void setIds(String ids) 
	{
		this.ids = ids;
	}
}