package pams.dao.Impl;

import java.util.List;

import org.springframework.stereotype.Component;

import pams.dao.DeviceDao;
import pams.model.Device;
@Component("deviceDaoImpl")
public class DeviceDaoImpl extends SuperImpl implements DeviceDao {

	@SuppressWarnings("unchecked")
	@Override
	public Device load(int shed_id,int deviceType){
		Device device = null;
		List<Device> devices = this.getHibernateTemplate().find("from Device d where d.shed = "+shed_id+" and d.device_type = "+deviceType);
		if(devices != null && devices.size()>0){
			device = devices.get(0);
		}
		return device;
	}
    /**
     * 根据设备类型ID 读取设备类型。
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<Device> load(int deviceType) {
		
		return (List<Device>)this.getHibernateTemplate().find("from Device d where d.device_type = "+deviceType);
	}

}
