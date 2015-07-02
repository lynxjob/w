package pams.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
/**
 * @author cjl
 * 中间表
 * 用户角色：多对一与一个用户
 * 			      多对一与一个角色
 * roleId，userId都在这个表格中维护
 */
@Entity
public class UserRoles implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Role role;
	private User user;
	
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
	
	@ManyToOne
	@JoinColumn(name="roleId")
	public Role getRole() 
	{
		return role;
	}
	public void setRole(Role role)
	{
		this.role = role;
	}
	
	@ManyToOne
	@JoinColumn(name="userId")
	public User getUser() 
	{
		return user;
	}
	public void setUser(User user)
	{
		this.user = user;
	}
	
	@Override
	public String toString()
	{
		return "["+id+" , "+role.getId()+" , "+user.getId()+"]";
	}
}