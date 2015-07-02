package pams.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * 作物:一种作物可以种植在多个大棚内
 * @author 恶灵骑士
 *
 */
@Entity
public class Crop implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	//作物描述
	private String des;
	//作物图片
	private String img_url;
	//生长周期
	private int total;
	//所属大棚
	private Set<Shed> sheds = new HashSet<Shed>();
	//生长条件
	private List<Factor> factors = new ArrayList<Factor>();
	//种植日期
	private Date plantDate;
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setSheds(Set<Shed> sheds) {
		this.sheds = sheds;
	}
	@OneToMany(fetch=FetchType.EAGER,mappedBy="crop")
	public Set<Shed> getSheds() {
		return sheds;
	}
	public void setFactors(List<Factor> conditions) {
		this.factors = conditions;
	}
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER,mappedBy="crop")
	public List<Factor> getFactors() {
		return factors;
	}
	@Column(length=50,nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String imgUrl) {
		img_url = imgUrl;
	}
	
	
	
	public void setTotal(int total) {
		this.total = total;
	}
	public int getTotal() {
		return total;
	}
	public void setPlantDate(Date plantDate) {
		this.plantDate = plantDate;
	}
	public Date getPlantDate() {
		return plantDate;
	}
	public String toString(){
		return "["+id+",'"+name+"'],";
	}
	
	
}
