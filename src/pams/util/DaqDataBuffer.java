package pams.util;

import java.util.ArrayList;
import java.util.Date;

import pams.model.DaqData;
import pams.model.Device;
import pams.service.Impl.DaqManager;
import pams.service.Impl.DeviceManage;
import pams.servlet.SysInitServlet;

/**
 * 数据采集仪数据缓存区
 * @author 恶灵骑士
 *
 */
public class DaqDataBuffer {
	    //采集仪数据管理器
		private DaqManager daqManager;
		//设备管理器
		private DeviceManage deviceManage;
		//最新采集仪数据
		private volatile Object lastData = null;
		//时间戳
		private long timestamp = 0L;
		//数据库同步周期
		private long seconds = 0L;
		//串口同步周期
		private long comm_seconds = 0L;
		//缓存区
		private ArrayList<Object> cacheList = new ArrayList<Object>();
		//缓存大小
		private int bufferSize = 0;
		
        public DaqDataBuffer(Integer shedId){
        	daqManager = (DaqManager)SysInitServlet.getSpringContext().getBean("daqManager");
        	deviceManage = (DeviceManage)SysInitServlet.getSpringContext().getBean("deviceManage");
        	Device device = deviceManage.load(shedId, 1);
        	if(device == null){
        		this.bufferSize = 10;
        		this.seconds = 60000;
        		this.comm_seconds = 1000;
        		this.timestamp = new Date().getTime();
        		return;
        	}
        	this.comm_seconds = Long.parseLong(device.getProperty1());
        	this.seconds = Long.parseLong(device.getProperty2());
        	this.bufferSize = Integer.parseInt(device.getProperty3());
        	this.timestamp = new Date().getTime();
        }
        
		public void put(Object obj) throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException
		{
			cacheList.add(obj);
			lastData = obj;
			long now = (new Date()).getTime();
			//达到一定长度或时间，存储数据
			if(cacheList.size() == this.bufferSize || (now-timestamp) >= seconds)
			{
				saveBuffer();
			}
		}
		
		public void saveBuffer() throws ClassNotFoundException, IllegalAccessException
		{
			//存储数据
			if(cacheList!=null && cacheList.size()>0){
				for(Object obj:cacheList){
					daqManager.save((DaqData)obj);
				}	
				//清空缓存
				timestamp = (new Date()).getTime();;
				cacheList.clear();
			}
			
		}

		public Object getLastData() {
			return lastData;
		}

		public long getComm_seconds() {
			return comm_seconds;
		}
	}

