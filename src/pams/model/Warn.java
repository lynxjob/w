package pams.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 报警信息
 * @author 恶灵骑士
 *
 */
@Entity
public class Warn implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	//警报类型[1:CO2浓度过高 2:CO2浓度过低 3:光照强度过高 4:光照强度过低 5:大棚温度过高 6:大棚温度过低]
	//[7:大棚湿度过高 8:大棚湿度过低 9:土壤温度过高 10:土壤温度过低 11:土壤湿度过高 12:土壤湿度过低 13:其他类型]
	private int type;
	//警报严重等级[1:非常严重 2:严重 3:不严重]
	private int level;
	//警报具体信息
	private String detail;
	//警报是否被处理[0:处理 1:未处理]
	private int deal;
	//警报发生时间
	private Date date;
	//所属大棚
	private Shed shed;
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setShed(Shed shed) {
		this.shed = shed;
	}
	@ManyToOne
	@JoinColumn(name="shedId")
	public Shed getShed() {
		return shed;
	}
	//@Column(unique=true,nullable=false)
	@Column(nullable=false)
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	@Column(length=50)
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public int getDeal() {
		return deal;
	}
	public void setDeal(int deal) {
		this.deal = deal;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String toString(){
		return "";
	}
}
