package pams.vo;

/**
 * fields:['coc','dadiwendu','daqishidu','daqiwendu','daqiyali','guanghe','jiangyu','turangshuifen','zhengfa','fengli','sfengxiang','sampleDate']
 * @author 恶灵骑士
 *
 */
public class DaqInfo extends BaseInfo{
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	private String startDate;
	private String endDate;
}
