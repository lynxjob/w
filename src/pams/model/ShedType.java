package pams.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
/**
 *  大棚类型
 *	树形结构
 */
@Entity
public class ShedType implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String des;
	private Set<ShedType> children=new HashSet<ShedType>();
	private ShedType parent;
	
	public ShedType()
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
	
	public String getName()
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	
	@Column(updatable=false)
	public String getDes()
	{
		return des;
	}
	public void setDes(String des) 
	{
		this.des = des;
	}
	
	//默认的fetch是lazy。修改为eager，即时加载
	//1对多默认的lazy延迟加载
	@OneToMany(cascade=CascadeType.ALL,mappedBy="parent",fetch=FetchType.EAGER)
	public Set<ShedType> getChildren() 
	{
		return children;
	}
	public void setChildren(Set<ShedType> children)
	{
		this.children = children;
	}
	
	@ManyToOne
	@JoinColumn(name="parentId")
	public ShedType getParent() 
	{
		return parent;
	}
	public void setParent(ShedType parent) 
	{
		this.parent = parent;
	}
	
	public String toString()
	{
		return "{id:"+id+",name:'"+name+"',des:'"+des+"'},";
	}
}