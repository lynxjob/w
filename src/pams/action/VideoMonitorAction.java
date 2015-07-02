package pams.action;

import javax.annotation.Resource;

import pams.model.Device;
import pams.service.Impl.DeviceManage;
import pams.util.JsonUtil;

public class VideoMonitorAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private int shedId;
	private DeviceManage deviceManage;
	
	public void setShedId(int shedId) {
		this.shedId = shedId;
	}
	public int getShedId() {
		return shedId;
	}
	@Resource(name="deviceManage")
	public void setDeviceManage(DeviceManage deviceManage){
		this.deviceManage = deviceManage;
	}
	
	public String load(){
		System.out.println("shedId: "+shedId);
		Device device = this.deviceManage.load(shedId, 2);
		if(device != null){
			this.jsonString = JsonUtil.getInstance().deviceToJson(device);
		}else{
			this.jsonString = "{success:false,msg:'加载摄像头参数失败!<br/>可能该大棚未安装摄像头!'}";
		}
		System.out.println(this.jsonString);
		return "load";
	}

}
