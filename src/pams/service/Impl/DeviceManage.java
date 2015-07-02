package pams.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import pams.dao.DeviceDao;
import pams.model.Device;

@Component("deviceManage")
public class DeviceManage {
	private DeviceDao devicedao;
	
	@Resource(name="deviceDaoImpl")
	public void setDevicedao(DeviceDao devicedao) {
		this.devicedao = devicedao;
	}

	public DeviceDao getDevicedao() {
		return devicedao;
	}
	/**
	 * 获取设备信息
	 * @param entityId
	 * @return
	 */
	public Device get(int entityId){
		return this.devicedao.get(Device.class, entityId);
	}
	/**
	 * 保存设备信息
	 * @param device
	 */
	public void save(Device device){
		this.devicedao.save(device);
	}
	/**
	 * 更新设备信息
	 * @param device
	 */
	public void update(Device device){
		this.devicedao.update(device);
	}
	/**
	 * 根据shed_id和device_type获取设备信息
	 * @param shed_id
	 * @param device_type
	 * @return
	 */
	public Device load(int shed_id,int device_type){
		return this.devicedao.load(shed_id, device_type);
	}
	/**
	 * 根据device_type获取设备信息
	 * @param device_type
	 * @return
	 */
	public List<Device> load(int device_type){
		return this.devicedao.load(device_type);
	}
}
