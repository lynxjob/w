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
 * 角色：id，名字，描述，创建日期，创建者id，创建者名字
 */
@Entity
public class Role implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private String des;
	private Date cdate;
	private Integer creatorId;
	private String creatorName;
	
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
	
	@Column(length=40,nullable=false)
	public String getName() 
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	
	@Column(length=100,nullable=false)
	public String getDes() 
	{
		return des;
	}
	public void setDes(String des)
	{
		this.des = des;
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
	
	@Override
	public String toString()
	{
		return "["+id+" , "+name+" , "+des+" , "+cdate.toString()+"]";
	}
}