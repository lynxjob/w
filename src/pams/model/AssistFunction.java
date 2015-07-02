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

/*
 *  辅助功能
 *  树形结构
 * */
@Entity
public class AssistFunction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String des;
	private Set<AssistFunction> children = new HashSet<AssistFunction>();
	private AssistFunction parent;
	private String value;
	
	public AssistFunction(){
		
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(updatable=false)
	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}
	@OneToMany(cascade=CascadeType.ALL,mappedBy="parent",fetch=FetchType.EAGER)
	public Set<AssistFunction> getChildren() {
		return children;
	}

	public void setChildren(Set<AssistFunction> children) {
		this.children = children;
	}
	@ManyToOne
	@JoinColumn(name="parentId")
	public AssistFunction getParent() {
		return parent;
	}

	public void setParent(AssistFunction parent) {
		this.parent = parent;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	@Override
	public String toString() {
		return "AssistFunction [des=" + des + ", id=" + id + ", name=" + name
				+ "]";
	}
}
