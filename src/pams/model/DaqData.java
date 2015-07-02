package pams.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
/**
 * 数据采集仪字段 
 * fields:[
 * 'coc','dadiwendu','daqishidu','daqiwendu','daqiyali','guanghe','jiangyu',
 * 'turangshuifen','zhengfa','fengli','sfengxiang','sampleDate'
 * ]
 * @author 恶灵骑士
 *
 */
@Entity
public class DaqData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	//蒸发
	public static final byte ZHENGFA = 0x00;
	//大气湿度
	public static final byte DAQISHIDU = 0x01;
	//大气压力
	public static final byte DAQIYALI = 0x02;
	//总辐射（光照）
	public static final byte GUANGHE = 0x03;
	//土壤水分
	public static final byte TURANGSHUIFEN = 0x04;
	//热通量
	public static final byte COC = 0x05;
	//风速
	public static final byte FENGSU = 0x06;
	//风向
	public static final byte FENGXIANG = 0x07;
	//大气温度
	public static final byte DAQIWENDU = 0x08;
	//大地温度
	public static final byte DADIWENDU = 0x09;
	//雨量
	public static final byte JIANGYU = 0x0A;
	//读取所有值
	public static final byte ALL = 0x0B;
	//单位
	public static final String[] UNITS = {"mm", "RH", "百帕", "W", "%", "ppm", "米/秒", "度", "摄氏度", "摄氏度", "mm"};
	//单位修正值
	public static final float[] UVALUE = {0.1f, 1f, 0.1f, 1f, 1f, 1f, 0.1f, 1f, 0.1f, 0.1f, 0.1f};
	
	
	private Integer id;
	//蒸发
	private float zhengfa;
	//大气湿度
	private float daqishidu;
	//大气压力
	private float daqiyali;
	//总辐射（光照）
	private float guanghe;
	//土壤水分
	private float turangshuifen;
	//热通量
	private float coc;
	//风速
	private float fengsu;
	//风力
	private String fengli;
	//风向
	private float fengxiang;
	//风向
	private String sfengxiang;
	//大气温度
	private float daqiwendu;
	//大地温度
	private float dadiwendu;
	//降雨
	private float jiangyu;
	//时间
	private Date sampleDate;
	
	public DaqData(){
		
	}
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public float getZhengfa() {
		return zhengfa;
	}

	public void setZhengfa(float zhengfa) {
		this.zhengfa = zhengfa;
	}

	public float getDaqishidu() {
		return daqishidu;
	}

	public void setDaqishidu(float daqishidu) {
		this.daqishidu = daqishidu;
	}

	public float getDaqiyali() {
		return daqiyali;
	}

	public void setDaqiyali(float daqiyali) {
		this.daqiyali = daqiyali;
	}

	public float getGuanghe() {
		return guanghe;
	}

	public void setGuanghe(float guanghe) {
		this.guanghe = guanghe;
	}

	public float getTurangshuifen() {
		return turangshuifen;
	}

	public void setTurangshuifen(float turangshuifen) {
		this.turangshuifen = turangshuifen;
	}

	public float getCoc() {
		return coc;
	}

	public void setCoc(float coc) {
		this.coc = coc;
	}

	public float getFengsu() {
		return fengsu;
	}

	public void setFengsu(float fengsu) {
		this.fengsu = fengsu;
	}

	public float getFengxiang() {
		return fengxiang;
	}

	public void setFengxiang(float fengxiang) {
		this.fengxiang = fengxiang;
	}

	public float getDaqiwendu() {
		return daqiwendu;
	}

	public void setDaqiwendu(float daqiwendu) {
		this.daqiwendu = daqiwendu;
	}

	public float getDadiwendu() {
		return dadiwendu;
	}

	public void setDadiwendu(float dadiwendu) {
		this.dadiwendu = dadiwendu;
	}

	public float getJiangyu() {
		return jiangyu;
	}

	public void setJiangyu(float jiangyu) {
		this.jiangyu = jiangyu;
	}
	@Column(length=50)
	public String getSfengxiang() {
		return sfengxiang;
	}

	public void setSfengxiang(String sfengxiang) {
		this.sfengxiang = sfengxiang;
	}

	public String getFengli() {
		return fengli;
	}

	public void setFengli(String fengli) {
		this.fengli = fengli;
	}
	
	public Date getSampleDate() {
		return sampleDate;
	}

	public void setSampleDate(Date sampleDate) {
		this.sampleDate = sampleDate;
	}
	
	
	@Override
	public String toString(){
		return "{id:"+id+",coc:"+coc+",dadiwendu:"+dadiwendu+",daqiwendu:"+daqiwendu+",daqiyali:"+daqiyali+",guanghe:"+guanghe+
		         ",jiangyu:"+jiangyu+",turangshuifen:"+turangshuifen+",zhengfa:"+zhengfa+",fengli:'"+fengli+"',sfengxiang:'"+sfengxiang+
		         "',sampleDate:'"+sampleDate+"'},";
	}
}
