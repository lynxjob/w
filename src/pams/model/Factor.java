package pams.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 作物生长环境因素environment factors
 * @author 恶灵骑士
 *
 */
@Entity
public class Factor implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	//生长阶段
	private int level;
	//起始时间(天)
	private int start;
	//结束时间(天)
	private int end;
	//总辐射（光照）
	private float guanghe;
	//热通量(CO2)
	private float coc;
	//大气湿度
	private float daqishidu;
	//大气温度
	private float daqiwendu;
	//土壤水分
	private float turangshuifen;
	//土壤温度
	private float dadiwendu;
	//所属作物
	private Crop crop;
	
	public void setId(Integer id) {
		this.id = id;
	}
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setCrop(Crop crop) {
		this.crop = crop;
	}
	@ManyToOne
	@JoinColumn(name="cropId")
	public Crop getCrop() {
		return crop;
	}
	
	@Column(nullable =true)
	public float getGuanghe() {
		return guanghe;
	}
	public void setGuanghe(float guanghe) {
		this.guanghe = guanghe;
	}
	
	@Column(nullable =true)
	public float getCoc() {
		return coc;
	}
	public void setCoc(float coc) {
		this.coc = coc;
	}
	
	@Column(nullable =true)
	public float getDaqishidu() {
		return daqishidu;
	}
	public void setDaqishidu(float daqishidu) {
		this.daqishidu = daqishidu;
	}
	
	@Column(nullable =true)
	public float getDaqiwendu() {
		return daqiwendu;
	}
	public void setDaqiwendu(float daqiwendu) {
		this.daqiwendu = daqiwendu;
	}
	
	@Column(nullable =true)
	public float getTurangshuifen() {
		return turangshuifen;
	}
	public void setTurangshuifen(float turangshuifen) {
		this.turangshuifen = turangshuifen;
	}
	
	@Column(nullable =true)
	public float getDadiwendu() {
		return dadiwendu;
	}
	public void setDadiwendu(float dadiwendu) {
		this.dadiwendu = dadiwendu;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
}
