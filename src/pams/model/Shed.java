package pams.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
/**
 * 大棚 : 一个大棚只能种植一种作物
 * @author 恶灵骑士
 *
 */
@Entity
public class Shed implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	//名称
	private String name;
	//描述
	private String des;
	//创建日期
	private Date cdate;
	//创建者ID
	private Integer creatorId;
	//创建者名称
	private String creatorName;
	//大棚类型
	private ShedType shedType;
	//大棚作物
	private Crop crop;
	//大棚使用人群
	private Set<User> users = new HashSet<User>(); 
	//大棚警报信息
	private Set<Warn> warns = new HashSet<Warn>();
	
	public Shed(){
		
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="user_shed",
		joinColumns = {@JoinColumn(name="shedId")},
		inverseJoinColumns = {@JoinColumn(name="userId")}
	)
	public Set<User> getUsers() {
		return users;
	}

	public void setWarns(Set<Warn> warns) {
		this.warns = warns;
	}
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER,mappedBy="shed")
	public Set<Warn> getWarns() {
		return warns;
	}

	@Column(length=40,nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(length=100,nullable=false)
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	
	@Column(updatable = false)
	public Date getCdate() {
		return cdate;
	}
	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}
	
	@Column(updatable = false)
	public Integer getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}
	
	@Column(updatable=false)
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	
	@ManyToOne
	@JoinColumn(name="shedTypeId")
	public ShedType getShedType() {
		return shedType;
	}
	public void setShedType(ShedType shedType) {
		this.shedType = shedType;
	}
		
	public void setCrop(Crop crop) {
		this.crop = crop;
	}
	@ManyToOne
	@JoinColumn(name="cropId")
	public Crop getCrop() {
		return crop;
	}

	@Override
	public String toString(){
		String result = "";
		String cropname = "";
		if(crop==null){
			cropname = "暂未分配大棚作物";
		}else{
			cropname = crop.getName();
		}
		if(shedType != null){
			result = "{id:"+id+",name:'"+name+"',des:'"+des+"',shedTypeName:'"+shedType.getName()+"',cropName:'"+cropname+"',creatorName:'"+creatorName+"',cdate:'"+cdate+"'},";
		}else{
			result = "{id:"+id+",name:'"+name+"',des:'"+des+"',shedTypeName:暂未分配大棚类型'"+"',cropName:暂未分配大棚作物'"+"',creatorName:'"+creatorName+"',cdate:'"+cdate+"'},";
		}
		return result;
	}
}
