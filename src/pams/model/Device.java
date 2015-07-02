package pams.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
/**
 * 
 * @author 恶灵骑士 
 * 一个大棚对应一套设备
 * device_type:设备属性  [0 1 2]
 * 0:系统设置[(property->)1:采集仪端口号 2:继电器端口号]  
 * 1:采集仪[1:串口数据同步周期  2:数据库同步周期 3:缓冲区大小]  
 * 2:摄像头[1:刻录机IP 2:刻录机端口号 3:账户 4:密码]
 */
@Entity
public class Device implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Shed shed;
	private int device_type;
	private String property1;
	private String property2;
	private String property3;
	private String property4;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@OneToOne
	@JoinColumn(name="shedId")
	public Shed getShed() {
		return shed;
	}
	public void setShed(Shed shed) {
		this.shed = shed;
	}
	public int getDevice_type() {
		return device_type;
	}
	public void setDevice_type(int deviceType) {
		device_type = deviceType;
	}
	@Column(length=50)
	public String getProperty1() {
		return property1;
	}
	public void setProperty1(String property1) {
		this.property1 = property1;
	}
	@Column(length=50)
	public String getProperty2() {
		return property2;
	}
	public void setProperty2(String property2) {
		this.property2 = property2;
	}
	@Column(length=50)
	public String getProperty3() {
		return property3;
	}
	public void setProperty3(String property3) {
		this.property3 = property3;
	}
	@Column(length=50)
	public String getProperty4() {
		return property4;
	}
	public void setProperty4(String property4) {
		this.property4 = property4;
	}

}
//摄像头参数
/*var szDevIp = '192.168.1.212'; 
var szDevPort = '8000'; 
var szDevUser = 'admin'; 
var szDevPwd = '54321'; */