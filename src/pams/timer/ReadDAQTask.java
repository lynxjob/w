package pams.timer;

import java.util.Date;

import pams.events.AbstractEvent;
import pams.events.DaqEvent;
import pams.model.DaqData;
import pams.socket.service.ICommunicator;
import pams.util.DaqDataBuffer;
/**
 * 读取数据计时器
 * @author 恶灵骑士
 * 
 */
public class ReadDAQTask extends CTimerTask {
	//数据缓存区
	private DaqDataBuffer daqBuffer = null;
	//Tcp适配器
	private ICommunicator cmnct = null;
	//调用者daqClient
	private String clientName = null;
	
	public ReadDAQTask(ICommunicator cmnct,int shedId){
		daqBuffer = new DaqDataBuffer(shedId);
		this.cmnct = cmnct;
	}
	/**
	 * 设置串口同步周期
	 */
	public void startTimer()
	{ 
		this.startTimer(0, this.daqBuffer.getComm_seconds());
	}
	
	/**
	 * 具体执行任务
	 * 定时访问数据采集仪获取数据
	 * 数据达到一定量或时间的话,
	 * 存储到数据库
	 */
	@Override
	public void run() {
		AbstractEvent daqEvent = new DaqEvent(11);
		this.clientName = this.cmnct.getClientName();
		System.out.println("1."+daqEvent.toString()+":"+clientName);
		byte[] msg = this.cmnct.getData(clientName, daqEvent);
//		System.out.println("test1"+msg);
//		System.out.println("test"+(DaqData)daqEvent.resolveData(msg));
		if(msg != null)
		{
//			System.out.println("010100100");
			DaqData daqData = (DaqData)daqEvent.resolveData(msg);
			System.out.println("风向: "+daqData.getSfengxiang()+"  采集时间: "+new Date());
			try {
				daqBuffer.put(daqData);
//				System.out.println("987"+daqBuffer);
				System.out.println("采集仪最新数据:"+daqBuffer.getLastData());
			} catch (IllegalArgumentException e) {
				System.out.println("参数不可用-->"+e.getMessage());
			} catch (IllegalAccessException e) {
				System.out.println("引用不可用-->"+e.getMessage());
			} catch (ClassNotFoundException e) {
				System.out.println("类定义未找到-->"+e.getMessage());
			}
			
		}
	}
	
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	public DaqDataBuffer getDaqBuffer(){
		return this.daqBuffer;
	}

}
