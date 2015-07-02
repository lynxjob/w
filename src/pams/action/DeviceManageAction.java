package pams.action;

import javax.annotation.Resource;

import pams.model.Device;
import pams.model.Shed;
import pams.service.Impl.DeviceManage;
import pams.service.Impl.ShedManager;
import pams.servlet.SysInitServlet;
import pams.util.JsonUtil;
import pams.vo.DeviceInfo;

import com.opensymphony.xwork2.ModelDriven;

public class DeviceManageAction extends BaseAction implements ModelDriven<DeviceInfo>{

	private static final long serialVersionUID = 1L;
	
	private DeviceManage deviceManage;
	private ShedManager shedManager;
	private DeviceInfo deviceinfo = new DeviceInfo();
	
	public DeviceInfo getModel(){
		return this.deviceinfo;
	}
	@Resource(name="deviceManage")
	public void setDeviceManage(DeviceManage deviceManage){
		this.deviceManage = deviceManage;
	}
	@Resource(name="shedManager")
	public void setShedManager(ShedManager shedManager){
		this.shedManager = shedManager;
	}
	
	
	/**
	 * 保存设备数据
	 * @return
	 */
	public String saveOrupdate(){
		Device device =this.deviceManage.load(this.deviceinfo.getShed_id(), this.deviceinfo.getDevice_type());
		boolean save_tag = false;
		if(device == null){
			device = new Device();
			device.setDevice_type(this.deviceinfo.getDevice_type());
			Shed shed = this.shedManager.get(this.deviceinfo.getShed_id());
			device.setShed(shed);
			save_tag = true;
		}
		device.setProperty1(this.deviceinfo.getProperty1());
		device.setProperty2(this.deviceinfo.getProperty2());
		device.setProperty3(this.deviceinfo.getProperty3());
		device.setProperty4(this.deviceinfo.getProperty4());
		if(save_tag){
			deviceManage.save(device);
		}else{
			deviceManage.update(device);
			save_tag = false;
		}
		if(this.deviceinfo.getDevice_type() == 1)
			SysInitServlet.cmnct.restartTimer();
		this.jsonString = "{success:true,msg:'设置成功!'}";
		
		return "saveOrupdate";
	}
	
	/**
	 * 加载设备信息
	 * @return
	 */
	public String load(){
		System.out.println(this.deviceinfo.getShed_id()+"-->"+this.deviceinfo.getDevice_type());
		Device device =this.deviceManage.load(this.deviceinfo.getShed_id(), this.deviceinfo.getDevice_type());
		if(device != null){
			this.jsonString = JsonUtil.getInstance().deviceToJson(device);
		}else{
			this.jsonString = "{success:false,msg:'载入数据失败!'}";
		}
		
		return "load";
	}
	
}
