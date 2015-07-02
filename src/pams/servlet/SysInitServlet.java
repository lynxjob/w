package pams.servlet;

import java.io.IOException;



import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import pams.model.Device;
import pams.service.Impl.DeviceManage;
import pams.socket.TcpManager;
import pams.socket.TcpServer;
import pams.socket.service.TcpCommunictor;
import pams.timer.ControlTask;
import pams.timer.ControlTaskN2;
import pams.timer.ControlTaskN;
/**
 * 系统初始化
 * @author 恶灵骑士
 * @TIME 2015年6月25日18:36:14
 * @look Lynx
 * TcpServer中serverID为:
 * 设备类型[采集仪daq 继电器realy]+大棚ID+端口号
 */
public class SysInitServlet implements ServletContextListener {
	//获取spring注入的bean对象
	private static WebApplicationContext springContext;
	//设备管理器
	private DeviceManage deviceManager;
    //服务映射集合 
	private HashMap<String,TcpServer> tcpServers = new HashMap<String,TcpServer>();
	//大棚ID到ServerID映射集合
	private static HashMap<String,String> shedId2Strs = new HashMap<String,String>();
	//自动控制标识
	private static HashMap<String,Boolean> shedId2Auto = new HashMap<String,Boolean>();
	//Tcp连接管理器
	private TcpManager tcpManager;
	//策略控制Task
	//private ControlTask ct;
	private ControlTaskN ct;
	//Tcp控制器       TCP连接控制器。
	public static TcpCommunictor cmnct = new TcpCommunictor();
    //初始化系统
	public SysInitServlet(){
		super();
	}
	/**
	 *应用程序退出时调用
	 */
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		serverDestroyed();
		System.out.println("应用程序关闭!");
		System.exit(0);
	}
	
	/**
	 * 应用程序加载时调用
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		springContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		if(springContext == null){
			System.out.println("获取应用程序上下文失败!");
			return;
		}
		
		deviceManager = (DeviceManage)springContext.getBean("deviceManage");
		
		System.out.println("初始化系统服务!");
		
		serverInitialized();
		
		System.out.println("系统服务初始化完成!");

	}
	
	/**
	 * 系统服务初始化
	 * device_type:由于只需要知道采集仪,继电器端口号
	 * 所以device_type设置为0
	 */
	public void serverInitialized(){
		if(deviceManager == null){
			System.out.println("获取设备管理器失败!");
			return;
		}
		List<Device> devices = this.deviceManager.load(0);
		//获取server信息
		System.out.println("获取设备管理器成功！当前已经安装的设备数量为:");
		System.err.println(devices.size());
		for(Device device : devices){
			System.out.println("设备ID-->"+device.getId());
			System.out.println("采集仪端口号-->"+device.getProperty1());
			System.out.println("继电器端口号-->"+device.getProperty2());
			System.out.println("所属大棚-->"+device.getShed().getName());
			
			Integer shedId= device.getShed().getId();
			String daqServerId = "daq_"+shedId+"_"+device.getProperty1();
			TcpServer daqServer = new TcpServer(Integer.parseInt(device.getProperty1()));
			daqServer.setServerId(daqServerId);
			String realyServerId = "realy_"+shedId+"_"+device.getProperty2();
			TcpServer realyServer = new TcpServer(Integer.parseInt(device.getProperty2()));
			realyServer.setServerId(realyServerId);
			
			tcpServers.put(daqServerId, daqServer);
			tcpServers.put(realyServerId, realyServer);
			shedId2Strs.put("daq_"+shedId, daqServerId);
			shedId2Strs.put("realy_"+shedId, realyServerId);
			shedId2Auto.put("auto_"+shedId, false);
			System.out.println("daq_1"+shedId);
			System.out.println(shedId2Strs.get("daq_"+shedId));
			System.out.println(shedId2Auto.get("auto_"+shedId));
		}
		//开启server监听线程
		tcpManager = new TcpManager();
		Iterator<String> it = tcpServers.keySet().iterator();
		while(it.hasNext()){
			TcpServer server = (TcpServer)tcpServers.get(it.next());
			if(server != null){
				new Thread(server).start();
			}
		}
		new Thread(tcpManager).start();
		//ct = new ControlTask(cmnct);
		ct = new ControlTaskN(cmnct);
		//1分钟校验一次
		ct.startTimer(6000);
	}
	
	/**
	 * 系统服务注销
	 */
	public void serverDestroyed(){
		Iterator<String> it = tcpServers.keySet().iterator();
		while(it.hasNext()){
			TcpServer server = (TcpServer)tcpServers.get(it.next());
			if(server != null){
				try {
					server.setRunning(false);
					server.getServer().close();
				} catch (IOException e) {
					System.out.println("服务线程关闭失败-->"+e.getMessage());
				}
			}
		}
		tcpManager.setRunning(false);
		if(ct!=null){
			ct.stopTimer();
		}
	}
	/**
	 * 获取大棚ID到ServerID映射集合
	 * @return
	 */
	public static HashMap<String,String> getShedId2Strs() {
		return shedId2Strs;
	}
	/**
	 * 获取springContext
	 * @return
	 */
	public static WebApplicationContext getSpringContext() {
		return springContext;
	}
	/**
	 * 获取自动控制标识
	 * 默认为手动控制
	 * @return
	 */
	public static HashMap<String,Boolean> getShedId2Auto() {
		return shedId2Auto;
	}
}
