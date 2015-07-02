package pams.dao;

import java.util.List;

import pams.model.Device;

public interface DeviceDao extends SuperDao {
	/**
	 * 根据device_type加载对应类型的设备数据
	 * @param device_type
	 * @return
	 */
	public Device load(int shed_id,int device_type);
	/**
	 * 根据device_type加载对应类型的所有设备
	 */
	public List<Device> load(int device_type);
}
