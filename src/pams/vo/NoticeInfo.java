package pams.vo;
/**
 * @author cjl
 * 公告web参数
 */
public class NoticeInfo extends BaseInfo
{
	private String title;
	private String content;
	private Integer level;
	private String orderBy="order by id desc,level desc";
	
	public String getTitle() 
	{
		return title;
	}
	public void setTitle(String title) 
	{
		this.title = title;
	}
	
	public String getContent() 
	{
		return content;
	}
	public void setContent(String content) 
	{
		this.content = content;
	}
	
	public Integer getLevel() 
	{
		return level;
	}
	public void setLevel(Integer level) 
	{
		this.level = level;
	}
	
	public String getOrderBy() 
	{
		return orderBy;
	}
	public void setOrderBy(String orderBy) 
	{
		this.orderBy = orderBy;
	}
}