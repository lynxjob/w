package pams.model.Test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pams.model.Device;
import pams.model.Shed;
import pams.service.Impl.DeviceManage;
import pams.service.Impl.ShedManager;

public class DeviceTest {
	private static ClassPathXmlApplicationContext ctx;
	private static DeviceManage deviceManage;
	private static ShedManager shedManager;
	
	@BeforeClass
	public static void before(){
		ctx = new ClassPathXmlApplicationContext("beans.xml");
		deviceManage = (DeviceManage)ctx.getBean("deviceManage");
		shedManager = (ShedManager)ctx.getBean("shedManager");
	}
	@Test
	public void save(){
		Device device = new Device();
		Shed shed = shedManager.get(1);
		device.setShed(shed);
		deviceManage.save(device);
	}
	
	@AfterClass
	public static void after(){
		ctx.destroy();
	}
}
