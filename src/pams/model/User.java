package pams.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * @author cjl
 * 用户表：id,姓名,密码,年龄,联系电话,状态[1-正常;0-禁用],创建日期,创建者Id,创建者Name,所管理的大棚集合
 * 所属部门
 */
@Entity
public class User implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String password;
	private Integer age;
	private String tel;
	private Integer state=1;
	private Date cdate;
	private Integer creatorId;
	private String creatorName;
	private Set<Shed> sheds = new HashSet<Shed>();
	public User()
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
	
	@Column(length=50,unique=true,nullable=false)
	public String getName() 
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public String getPassword() 
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}

	public Integer getAge()
	{
		return age;
	}
	public void setAge(Integer age) 
	{
		this.age = age;
	}

	@Column(length=20,nullable=false)
	public String getTel() 
	{
		return tel;
	}
	public void setTel(String tel) 
	{
		this.tel = tel;
	}
	
	public Integer getState()
	{
		return state;
	}
	public void setState(Integer state)
	{
		this.state = state;
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

	public void setSheds(Set<Shed> sheds) {
		this.sheds = sheds;
	}
	
	@ManyToMany(fetch=FetchType.EAGER,mappedBy="users")
	public Set<Shed> getSheds() {
		return sheds;
	}

	@Override
	public String toString() 
	{
		return "{id:"+id+",name:'"+name+"',password:'"+password+"',age:'"+age+"',state:"+state+",tel:'"+tel+"',cdate:'"+cdate+"',creatorName:'"+creatorName+"'},";
	}
}