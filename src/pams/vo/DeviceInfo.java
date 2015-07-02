package pams.vo;
/**
 * 
 * @author 恶灵骑士 
 * 一个大棚对应一套设备
 * device_type:设备属性  [0 1 2]
 * 0:系统设置[(property->)1:采集仪端口号 2:继电器端口号]  
 * 1:采集仪[1:串口数据同步周期  2:数据库同步周期  3:缓冲区大小]  
 * 2:摄像头[1:刻录机IP 2:刻录机端口号 3:账户 4:密码]
 */
public class DeviceInfo extends BaseInfo{
	
	private Integer shed_id;
	private int device_type;
	private String property1;
	private String property2;
	private String property3;
	private String property4;
	
	public Integer getShed_id() {
		return shed_id;
	}
	public void setShed_id(Integer shedId) {
		shed_id = shedId;
	}
	public int getDevice_type() {
		return device_type;
	}
	public void setDevice_type(int deviceType) {
		device_type = deviceType;
	}
	public String getProperty1() {
		return property1;
	}
	public void setProperty1(String property1) {
		this.property1 = property1;
	}
	public String getProperty2() {
		return property2;
	}
	public void setProperty2(String property2) {
		this.property2 = property2;
	}
	public String getProperty3() {
		return property3;
	}
	public void setProperty3(String property3) {
		this.property3 = property3;
	}
	public String getProperty4() {
		return property4;
	}
	public void setProperty4(String property4) {
		this.property4 = property4;
	}
	
}
