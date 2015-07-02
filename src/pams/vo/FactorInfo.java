package pams.vo;

public class FactorInfo {
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
	public float getGuanghe() {
		return guanghe;
	}
	public void setGuanghe(float guanghe) {
		this.guanghe = guanghe;
	}
	public float getCoc() {
		return coc;
	}
	public void setCoc(float coc) {
		this.coc = coc;
	}
	public float getDaqishidu() {
		return daqishidu;
	}
	public void setDaqishidu(float daqishidu) {
		this.daqishidu = daqishidu;
	}
	public float getDaqiwendu() {
		return daqiwendu;
	}
	public void setDaqiwendu(float daqiwendu) {
		this.daqiwendu = daqiwendu;
	}
	public float getTurangshuifen() {
		return turangshuifen;
	}
	public void setTurangshuifen(float turangshuifen) {
		this.turangshuifen = turangshuifen;
	}
	public float getDadiwendu() {
		return dadiwendu;
	}
	public void setDadiwendu(float dadiwendu) {
		this.dadiwendu = dadiwendu;
	}
	
}
