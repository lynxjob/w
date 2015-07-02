package pams.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
/**
 * @author cjl
 * 系统公告: 自增Id，标题，内容，发表时间，用户Id，用户Name(冗余)，公告级别【排名】
 */
@Entity
public class Notice implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String title;
	private String content;
	private Date cdate;
	private Integer creatorId;
	private String creatorName;
	//默认是1
	private int level=1;
	
	public Notice()
	{
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	
	@Column(length=100,nullable=false)
	public String getTitle() 
	{
		return title;
	}
	public void setTitle(String title) 
	{
		this.title = title;
	}
	
	@Column(length=2000,nullable=false)
	public String getContent() 
	{
		return content;
	}
	public void setContent(String content) 
	{
		this.content = content;
	}
	
	@Column(updatable=false)
	public Date getCdate()
	{
		return cdate;
	}
	public void setCdate(Date cdate) 
	{
		this.cdate = cdate;
	}
	
	@Column(updatable=false)
	public Integer getCreatorId() 
	{
		return creatorId;
	}
	public void setCreatorId(Integer creatorId) 
	{
		this.creatorId = creatorId;
	}

	@Column(updatable=false)
	public String getCreatorName()
	{
		return creatorName;
	}
	public void setCreatorName(String creatorName)
	{
		this.creatorName = creatorName;
	}
	
	/**
	 * 公告等级level
	 * 1---一般
	 * 2---重要
	 * 3---紧急
	 * 4---置顶
	 */
	public int getLevel() 
	{
		return level;
	}
	public void setLevel(int level) 
	{
		this.level = level;
	}
	
	public String toString()
	{
		return "["+id+" , "+title+" , "+content+" , "+cdate.toString()+" , "+level+"]";
	}
}