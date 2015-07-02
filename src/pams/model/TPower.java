package pams.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
/**
 * @author cjl
 * 权限记录
 */
@Entity
public class TPower implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	/**
	 * 1--菜单面板(是否显示)
	 * 2--按钮(是否显示)
	 */
	private int flag=0;
	/**
	 * 菜单字符串-value(菜单面板的类型,ID等信息,用于创建菜单面板)
	 * 按钮
	 */
	private String value;
	private boolean checked=false;
	private Set<TPower> children=new HashSet<TPower>();
	private TPower parent;
	
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
	
	public int getFlag()
	{
		return flag;
	}
	public void setFlag(int flag)
	{
		this.flag = flag;
	}
	
	public String getValue()
	{
		return value;
	}
	public void setValue(String value) 
	{
		this.value = value;
	}
	
	public boolean isChecked() 
	{
		return checked;
	}
	public void setChecked(boolean checked) 
	{
		this.checked = checked;
	}
	@OneToMany(cascade=CascadeType.ALL, mappedBy="parent",fetch=FetchType.EAGER)
	public Set<TPower> getChildren() 
	{
		return children;
	}
	public void setChildren(Set<TPower> children)
	{
		this.children = children;
	}
	
	@ManyToOne
	@JoinColumn(name="parentId")
	public TPower getParent()
	{
		return parent;
	}
	public void setParent(TPower parent) 
	{
		this.parent = parent;
	}
	
	@Override
	public String toString()
	{
		return "[ "+id+" , "+name+" , "+checked+"]";
	}
}