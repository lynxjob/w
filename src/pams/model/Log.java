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
 * 系统日志信息
 */
@Entity
public class Log implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Integer id;
	//用户名
	private String userName;
	//IP地址
	private String ip;
	//操作类型
	private String oType;
	//操作时间
	private Date oDate=new Date();
	//操作明细
	private String oDetail;
	
	public Log()
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

	@Column(nullable = false)
	public String getUserName() 
	{
		return userName;
	}
	public void setUserName(String userName) 
	{
		this.userName = userName;
	}

	@Column(nullable = false)
	public String getIp()
	{
		return ip;
	}
	public void setIp(String ip)
	{
		this.ip = ip;
	}

	@Column(nullable = false)
	public String getoType() 
	{
		return oType;
	}
	public void setoType(String oType) 
	{
		this.oType = oType;
	}

	@Column(nullable = false)
	public Date getoDate() 
	{
		return oDate;
	}
	public void setoDate(Date oDate) 
	{
		this.oDate = oDate;
	}

	@Column(nullable = false)
	public String getoDetail()
	{
		return oDetail;
	}
	public void setoDetail(String oDetail)
	{
		this.oDetail = oDetail;
	}
}