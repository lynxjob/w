package pams.action;

import java.util.Date;

import javax.annotation.Resource;

import pams.events.AbstractEvent;
import pams.events.RelayEvent;
import pams.model.DaqData;
import pams.model.Factor;
import pams.model.Shed;
import pams.service.Impl.CropManager;
import pams.service.Impl.ShedManager;
import pams.servlet.SysInitServlet;
import pams.socket.TcpManager;
import pams.util.DateUtil;
import pams.util.JsonUtil;
import pams.vo.EnvirInfo;
import pams.web.form.XMLOper;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 控制与环境监控业务层
 * @author 恶灵骑士
 *
 */
public class EnviroMonitAction extends BaseAction implements ModelDriven<EnvirInfo>{

	private static final long serialVersionUID = 1L;
	private EnvirInfo envirinfo = new EnvirInfo(); 
	private CropManager cropManager;
	private ShedManager shedManager;
    String s[]={"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16"};
	//static String status[]={"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"};
    static String status[]={"1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1"};
    @Override
	public EnvirInfo getModel() {
		return this.envirinfo;
	}
	@Resource(name="cropManager")
	public void setCropManager(CropManager cropManager) {
		this.cropManager = cropManager;
	}
	@Resource(name="shedManager")
	public void setShedManager(ShedManager shedManager){
		this.shedManager = shedManager;
	}
	/**
	 * 处理继电器操作请求
	 */
	public String loadRelayData(){
		int i;
		System.out.println(this.envirinfo.getRelay_id()+"--"+this.envirinfo.getRelay_type()+"--"+this.envirinfo.getRelay_status());
		try {
			String dir = SysInitServlet.getSpringContext().getServletContext().getRealPath("/");
		for( i=0;i<16;i++){
			if(s[i].equals(this.envirinfo.getRelay_id())){
				status[i]=this.envirinfo.getRelay_status();
			//	System.out.println(this.envirinfo.getRelay_id()+"is"+status[i]);
				}
			}
			XMLOper.getInstance().createXml(status,dir);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(i=0;i<16;i++){
			System.out.print(status[i]);
		}
		System.out.println("");
		
		String clientName = SysInitServlet.getShedId2Strs().get("realy_"+this.envirinfo.getShedId());
		//System.out.println(this.envirinfo.getShedId());
		//System.out.println(clientName);
		if(TcpManager.clientBuffer.getClient(clientName) != null)
		{
			AbstractEvent relayEvent = new RelayEvent(this.envirinfo.getRelay_type(), this.envirinfo.getRelay_id(), this.envirinfo.getRelay_status());
			byte[] rData = SysInitServlet.cmnct.getData(clientName, relayEvent);
			System.out.println("发送成功");
			this.jsonString = (String)relayEvent.resolveData(rData);
		}else {
			this.jsonString = "{rs:'close'}";			
		}
		return "loadRelayData";
	}
	/**
	 * 处理数据采集仪操作请求--模拟
	 */
	/*public String loadDaqData(){
		 String tempdata = 
			"{sampleDate:'"+DateUtil.getInstance().getDateString3(new Date())+"',coc:"+Math.random()*100+",daqishidu:"+Math.random()*1000+",turangshuifen:"+Math.random()*1000+",guanghe:"+Math.random()*1000+",daqiwendu:"+Math.random()*100+",dadiwendu:"+Math.random()*1000
			  +",fcoc:"+Math.random()*100+",fdaqishidu:"+Math.random()*1000+",fturangshuifen:"+Math.random()*1000+",fguanghe:"+Math.random()*1000+",fdaqiwendu:"+Math.random()*100+",fdadiwendu:"+Math.random()*1000+"}";
		 System.out.println("jsonString:"+tempdata);
		this.jsonString = tempdata;
		System.out.println("data:"+tempdata);
		return "loadDaqData";
	}*/
	/**
	 * 处理数据采集仪操作请求
	 */
	public String loadDaqData(){
	    String clientName = SysInitServlet.getShedId2Strs().get("daq_"+this.envirinfo.getShedId());
	    Shed shed = shedManager.get(this.envirinfo.getShedId());
	    if(shed == null){
	    	this.jsonString = "{success:false,msg:'载入大棚信息出错!'}";
	    	return "loadDaqData";
	    }
	    //factor与作物的属性有关以及和作物的加入时间与当前时间的差在作物的那个生长时间也有关
        Factor factor = cropManager.getCondition(shed.getCrop().getId());
		SysInitServlet.cmnct.setClientName(clientName);
		System.out.println("客户端名称："+clientName);
       	if(TcpManager.clientBuffer.getClient(clientName) == null)
		{
       		System.out.println("客户端获取失败!");
			this.jsonString="{success:false,msg:'采集仪串口未开启!'}";
		}else if(SysInitServlet.cmnct.getRdt() != null)
		{
			System.out.println("asdfaf-=-===================");
			DaqData rDatas = (DaqData)SysInitServlet.cmnct.getRdt().getDaqBuffer().getLastData();
			System.out.println("date==========================");
			System.out.println("最新数据："+rDatas.getDadiwendu()+"---"+rDatas.getCoc());
			
			this.jsonString = JsonUtil.getInstance().enviroToJson(rDatas, factor);
			System.out.println("jsonString:"+jsonString);
		} else {
			this.jsonString = "{success:false,msg:'定时器没有开启!'}";
		}
	
		return "loadDaqData";
	}
	
	/**
	 * 开启定时器
	 * @return
	 */
	public String startTimer(){
		SysInitServlet.cmnct.startTimer();
		this.jsonString = "{success:true,msg:'开启定时器成功!'}";
		return "startTimer";
	}
	/**
	 * 关闭定时器
	 * @return
	 */
	public String stopTimer(){
		SysInitServlet.cmnct.stopTimer();
		this.jsonString = "{success:true,msg:'关闭定时器成功!'}";
		return "stopTimer";
	}
	/**
	 * 设置自动控制
	 * @return
	 */
	public String setAuto(){
		String key = "auto_"+this.envirinfo.getShedId();
		Boolean autoTag = SysInitServlet.getShedId2Auto().get(key);
		if(autoTag != null && !autoTag.booleanValue())
		{
			SysInitServlet.getShedId2Auto().remove(key);
			SysInitServlet.getShedId2Auto().put(key, true);
			this.jsonString = "{success:true,msg:'自动控制设置成功!'}";
		}
		
		return "setAuto";
	}
	/**
	 * 设置手动控制
	 * @return
	 */
	public String setManual()throws Exception{
		String dir = SysInitServlet.getSpringContext().getServletContext().getRealPath("/");
		String key = "auto_"+this.envirinfo.getShedId();
		Boolean autoTag = SysInitServlet.getShedId2Auto().get(key);
		if(autoTag != null && autoTag.booleanValue())
		{
			SysInitServlet.getShedId2Auto().remove(key);
			SysInitServlet.getShedId2Auto().put(key, false);
			this.jsonString = "{success:true,msg:'手动控制设置成功!'}";
		}
		for(int  i=0;i<16;i++){
			status[i]="1";
			}
		System.out.println("开始手动控制");
			XMLOper.getInstance().createXml(status,dir);
		return "setManual";
	}
}
