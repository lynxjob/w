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
 * 用户权限记录， 角色权限记录
 */
@Entity
public class PowerUsersRoles implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private TPower power;
	/**
	 * 0-用户权限(如果添加用户权限即额外权限则将用户ID存入数据库)
	 * 1-角色权限(如果为用户添加角色权限则创建角色于此表，并将角色
	 * 与用户ID添加到userroles表中)
	 */
	private int flag;
	private Integer objectId;
	
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
	
	/**
	 * 权限记录Id
	 */
	@ManyToOne
	@JoinColumn(name="powerId")
	public TPower getPower()
	{
		return power;
	}
	public void setPower(TPower power)
	{
		this.power = power;
	}
	
	/**
	 * 1-角色权限
	 * 0-用户权限
	 */
	public int getFlag() 
	{
		return flag;
	}
	public void setFlag(int flag) 
	{
		this.flag = flag;
	}
	
	/**
	 * 角色或用户的ID
	 */
	public Integer getObjectId() 
	{
		return objectId;
	}
	public void setObjectId(Integer objectId)
	{
		this.objectId = objectId;
	}
}