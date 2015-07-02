package pams.vo;

public class EnvirInfo extends BaseInfo {
	//大棚ID
	private Integer shedId;
	//继电器路数
	private String relay_id;
	//状态标志
	private String relay_status;
	//命令类型
	private String relay_type;
	
	public String getRelay_id() {
		return relay_id;
	}
	public void setRelay_id(String relayId) {
		relay_id = relayId;
	}
	public String getRelay_status() {
		return relay_status;
	}
	public void setRelay_status(String relayStatus) {
		relay_status = relayStatus;
	}
	public String getRelay_type() {
		return relay_type;
	}
	public void setRelay_type(String relayType) {
		relay_type = relayType;
	}
	public void setShedId(Integer shedId) {
		this.shedId = shedId;
	}
	public Integer getShedId() {
		return shedId;
	}
	
	
}
