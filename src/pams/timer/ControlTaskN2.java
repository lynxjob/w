package pams.timer;

import java.util.Date;
import java.util.List;
import pams.events.AbstractEvent;
import pams.events.DaqEvent;
import pams.events.RelayEvent;
import pams.model.Crop;
import pams.model.DaqData;
import pams.model.Factor;
import pams.model.Shed;
import pams.model.Warn;
import pams.service.Impl.CropManager;
import pams.service.Impl.ShedManager;
import pams.service.Impl.WarnManager;
import pams.servlet.SysInitServlet;
import pams.socket.service.ICommunicator;
import pams.web.form.XMLOper;
import pams.model.*;
/**
 * 大棚决策控制
 * @author 恶灵骑士
 *
 */
public class ControlTaskN2 extends CTimerTask {
	//作物信息管理器
	private CropManager cropManager;
	//报警信息管理器
	private WarnManager warnManager;
	//大棚信息管理器
	private ShedManager shedManager;
	//Tcp控制器
	private ICommunicator cmnct;
	//控制大棚
	private List<Shed> sheds;
	
	//private MHContolInput mhContolInput;
	 //MHContolInput mhContolInput;
	int cindex[][]=new int[][]{{1,1,2,1,1},{1,2,2,2,1},{3,3,3,3,3},{4,4,4,4,5},{5,4,4,4,5}};
	String a[]={"很高","较高","正常","较低","很低 "};
	String b[]={"变化正大","变化正小 ","不变 ","变化负小","变化负大 "};	
	String parName[]={"co2","光照","温度","湿度"};
	float diff[]={0,0,0,0};
	float change[]={0,0,0,0};
	float value1[]={0,0,0,0};
	//float value2[]={0,0,0,0};
	boolean c=false;
	Date date1=new Date();
	Date date2=new Date();
	
    //String s[]={"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16"};
	static String status[]={"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"};
	
	/*String doC1[][]={{},};
	String doC1[][]={{},};
	String doC1[][]={{},};
	String doC1[][]={{},};
	String doC1[][]={{},};*/
	
	public ControlTaskN2(ICommunicator cmnct){
		this.cmnct = cmnct;
		cropManager = (CropManager)SysInitServlet.getSpringContext().getBean("cropManager");
		warnManager = (WarnManager)SysInitServlet.getSpringContext().getBean("warnManager");
		shedManager = (ShedManager)SysInitServlet.getSpringContext().getBean("shedManager");		
	}
	/**
	 * 设置校验时间
	 */
	public void startTimer(long period){
		this.startTimer(0, period);
	}
	@Override
	public void run(){
		int i;
		MHContolInput mhContolInput=new MHContolInput();
		System.out.println("run");
		AbstractEvent daqEvent = new DaqEvent(11);
		DaqData daqData=null;
		Factor factor=null;
		if(sheds == null){
			sheds = shedManager.list(0);
		}
		for(Shed shed : sheds){
		System.out.println(shed.getId());
		String clientName = SysInitServlet.getShedId2Strs().get("daq_"+shed.getId());
		boolean autoTag = SysInitServlet.getShedId2Auto().get("auto_"+shed.getId());
//			System.out.println("wo"+shed.getId());
		System.out.println("ClientName-->"+clientName);
		System.out.println("autoTag-->"+autoTag);
		byte[] msg = this.cmnct.getData(clientName, daqEvent);
		System.out.println("hello!"+msg);
		Crop crop = shed.getCrop();
		if(msg!=null)
			{
			daqData = (DaqData)daqEvent.resolveData(msg);
			System.out.println("解析成功");
			System.out.println("co2"+daqData.getCoc());
			}
		//System.out.println(daqData);
		if(crop!=null){
			factor = (Factor)cropManager.getCondition(crop.getId());
		}
		try{
			if(autoTag && msg!=null && crop!=null){
			//计算出采集的温度/湿度/CO2/光照 的 差值和变化率，并传给MHContol
			System.out.println("正常!正常!正常!正常!正常!正常!");
			diff[0]=daqData.getCoc()-factor.getCoc();
			diff[1]=daqData.getGuanghe()-factor.getGuanghe();
			diff[2]=daqData.getDaqiwendu()-factor.getDaqiwendu();
			diff[3]=daqData.getDaqishidu()-factor.getDaqishidu();
			
			if(!c)
			{
				value1[0]=daqData.getCoc();
				value1[1]=daqData.getGuanghe();
				value1[2]=daqData.getDaqiwendu();
				value1[3]=daqData.getDaqishidu();
				date1=new Date();
/*				for(int k=0;k<4;k++){
					System.out.println(value1[k]);
				}*/
			
				c=true;
			}
			else
			{
				date2=new Date();
				if(jisuan(date2,date1)>10){//判断与10分钟前的参数差值
					change[0]=daqData.getCoc()-value1[0];
					change[1]=daqData.getGuanghe()-value1[1];
					change[2]=daqData.getDaqiwendu()-value1[2];
					change[3]=daqData.getDaqishidu()-value1[3];
					value1[0]=daqData.getCoc();
					value1[1]=daqData.getGuanghe();
					value1[2]=daqData.getDaqiwendu();
					value1[3]=daqData.getDaqishidu();
					for(int k=0;k<4;k++){
						System.out.println(value1[k]);
					}
					date1=new Date();
				}
			}
			//Factor factor = (Factor)cropManager.getCondition(crop.getId());
			for(i=1;i<5;i++)
			{
				mhContolInput.setId(i);
				mhContolInput.setName(parName[i-1]);
				mhContolInput.setDif(diff[i-1]);
				mhContolInput.setChange(change[i-1]);
				doControl(mhContolInput,shed);
				System.out.println(parName[i-1]);
				System.out.println(diff[i-1]);
				System.out.println(change[i-1]);
			}
			System.out.print("继电器状态：");
			for(i=0;i<16;i++){
				System.out.print(status[i]);
			}
			String dir = SysInitServlet.getSpringContext().getServletContext().getRealPath("/");
			XMLOper.getInstance().createXml(status,dir);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
	/*
	 * 策略控制(Factor里存的是平均值,
	 * 上下波动范围为5%)
	 * @param daqData
	 * @param factor
	 */
	private void doControl(MHContolInput mhContolInput,Shed shed){
		int aindex,bindex;
		//String c;
//		int cindex[][]=new int[][]{{1,1,2,1,1},{1,2,2,2,1},{3,3,3,3,3},{4,4,4,4,5},{5,4,4,4,5}};
//		String a[]={"很高","较高","正常","较低","很低 "};
//		String b[]={"变化正大","变化正小 ","不变 ","变化负小","变化负大 "};
		System.out.println("docontrol");
		aindex=isValueField(mhContolInput.getDif());
		bindex=isValueField(mhContolInput.getChange());
		//c=mhContolInput.getName()+a[aindex];
		System.out.println(mhContolInput.getName()+a[aindex-1]);
		warnsRecord(0,2,mhContolInput.getName()+a[aindex-1],1,shed);
		warnsRecord(0,2,mhContolInput.getName()+b[aindex-1],1,shed);
		System.out.println(mhContolInput.getDif()+","+mhContolInput.getChange());
		doC(mhContolInput.getId(),cindex[aindex-1][bindex-1],shed);
	}
	//type=1 co2 type=2 光照  type=3 温度 type=4 湿度
	private void doC(int type,int status,Shed shed){
		System.out.println("进入doc");
		doStrategy(shed,"6",true);
		if(type==1)
		{
		doC1(status,shed);
		}
		else if(type==2)
		{
			doC2(status,shed);
		}
		else if(type==3)
		{
			doC2(status,shed);
		}
		else if(type==4)
		{
			doC4(status,shed);
		}
		
	}
	public void doC1(int status,Shed shed)
	{
		if(status==1)
		{
			doStrategy(shed,"6",true);
			ControlTaskN.status[5]="1";
			warnsRecord(0,2,"外侧窗收拢 ",1,shed);
			doStrategy(shed,"8",false);
			ControlTaskN.status[7]="0";
			warnsRecord(0,2,"风机停止 ",1,shed);
		}
		else if(status==2)
		{
			doStrategy(shed,"8",false);
			ControlTaskN.status[7]="0";
			warnsRecord(0,2,"风机停止 ",1,shed);	
		}
		else if(status==3)
		{
			//正常不用动作
		}
		else if(status==4)
		{
			doStrategy(shed,"8",false);
			ControlTaskN.status[7]="0";
			warnsRecord(0,2,"风机启动 ",1,shed);	
		}
		else if(status==5)
		{
			doStrategy(shed,"5",true);
			ControlTaskN.status[4]="1";
			warnsRecord(0,2,"外侧窗打开 ",1,shed);
			doStrategy(shed,"8",false);
			ControlTaskN.status[7]="0";
			warnsRecord(0,2,"风机启动 ",1,shed);	
		}
	}
	public void doC2(int status,Shed shed)
	{
		if(status==1)
		{
			doStrategy(shed,"1",true);
			ControlTaskN.status[0]="1";
			warnsRecord(0,2,"外光帘打开 ",1,shed);
			doStrategy(shed,"3",true);
			ControlTaskN.status[2]="1";
			warnsRecord(0,2,"内光帘打开 ",1,shed);
			doStrategy(shed,"10",true);
			ControlTaskN.status[9]="1";
			warnsRecord(0,2,"补光灯打开 ",1,shed);
		}
		else if(status==2)
		{
			doStrategy(shed,"10",true);
			ControlTaskN.status[9]="1";
			warnsRecord(0,2,"补光灯打开 ",1,shed);
		}
		else if(status==3)
		{
			//正常，不动作
		}
		else if(status==4)
		{
			doStrategy(shed,"10",false);
			ControlTaskN.status[9]="0";
			warnsRecord(0,2,"补光灯关闭 ",1,shed);
		}
		else if(status==5)
		{
			doStrategy(shed,"1",false);
			ControlTaskN.status[0]="0";
			warnsRecord(0,2,"外光帘关闭 ",1,shed);
			doStrategy(shed,"3",false);
			ControlTaskN.status[2]="0";
			warnsRecord(0,2,"内光帘关闭 ",1,shed);
			doStrategy(shed,"10",false);
			ControlTaskN.status[9]="0";
			warnsRecord(0,2,"补光灯关闭 ",1,shed);
		}
	}
	public void doC3(int status,Shed shed)
	{
		if(status==1)
		{
			doStrategy(shed,"1",true);
			ControlTaskN.status[0]="1";
			warnsRecord(0,2,"外光帘打开 ",1,shed);
			doStrategy(shed,"3",true);
			ControlTaskN.status[2]="1";
			warnsRecord(0,2,"内光帘打开 ",1,shed);
			doStrategy(shed,"6",true);
			ControlTaskN.status[5]="1";
			warnsRecord(0,2,"外侧窗收拢 ",1,shed);
			doStrategy(shed,"7",false);
			ControlTaskN.status[6]="0";
			warnsRecord(0,2,"湿帘关闭 ",1,shed);
			doStrategy(shed,"8",false);
			ControlTaskN.status[7]="0";
			warnsRecord(0,2,"风机停止 ",1,shed);
			
		}
		else if(status==2)
		{
			doStrategy(shed,"6",true);
			ControlTaskN.status[5]="1";
			warnsRecord(0,2,"外侧窗收拢 ",1,shed);	
			doStrategy(shed,"8",false);
			ControlTaskN.status[7]="0";
			warnsRecord(0,2,"风机停止 ",1,shed);	
		}
		else if(status==3)
		{
		//正常不用动作	
		}
		else if(status==4)
		{
			doStrategy(shed,"2",true);
			ControlTaskN.status[1]="1";
			warnsRecord(0,2,"外光帘收拢 ",1,shed);	
			doStrategy(shed,"4",true);
			ControlTaskN.status[3]="1";
			warnsRecord(0,2,"内光帘收拢 ",1,shed);	
			doStrategy(shed,"6",true);
			ControlTaskN.status[5]="1";
			warnsRecord(0,2,"外侧窗收拢 ",1,shed);			
		}
		else if(status==5)
		{
			doStrategy(shed,"2",true);
			ControlTaskN.status[1]="1";
			warnsRecord(0,2,"外光帘收拢 ",1,shed);	
			doStrategy(shed,"4",true);
			ControlTaskN.status[3]="1";
			warnsRecord(0,2,"内光帘收拢 ",1,shed);	
			doStrategy(shed,"6",true);
			ControlTaskN.status[5]="1";
			warnsRecord(0,2,"外侧窗收拢 ",1,shed);	
			doStrategy(shed,"7",true);
			ControlTaskN.status[6]="1";
			warnsRecord(0,2,"湿帘打开 ",1,shed);	
			doStrategy(shed,"8",true);
			ControlTaskN.status[7]="1";
			warnsRecord(0,2,"风机打开 ",1,shed);	
			
		}
	}
	public void doC4(int status,Shed shed)
	{
		if(status==1)
		{
			doStrategy(shed,"9",true);
			ControlTaskN.status[8]="1";
			warnsRecord(0,2,"水泵打开 ",1,shed);
			doStrategy(shed,"7",true);
			ControlTaskN.status[6]="1";
			warnsRecord(0,2,"湿帘打开 ",1,shed);		
			doStrategy(shed,"11",true);
			ControlTaskN.status[10]="1";
			warnsRecord(0,2,"第1路喷灌打开 ",1,shed);	
			doStrategy(shed,"12",true);
			ControlTaskN.status[11]="1";
			warnsRecord(0,2,"第2路喷灌打开 ",1,shed);	
			doStrategy(shed,"13",true);
			ControlTaskN.status[12]="1";
			warnsRecord(0,2,"第3路喷灌打开 ",1,shed);	
			doStrategy(shed,"14",true);
			ControlTaskN.status[13]="1";
			warnsRecord(0,2,"第4路喷灌打开 ",1,shed);	
			doStrategy(shed,"15",true);
			ControlTaskN.status[14]="1";
			warnsRecord(0,2,"滴灌打开 ",1,shed);	
			doStrategy(shed,"16",true);
			ControlTaskN.status[15]="1";
			warnsRecord(0,2,"雾喷打开 ",1,shed);



		}
		else if(status==2)
		{
			doStrategy(shed,"9",true);
			ControlTaskN.status[8]="1";
			warnsRecord(0,2,"水泵打开 ",1,shed);
			doStrategy(shed,"7",true);
			ControlTaskN.status[6]="1";
			warnsRecord(0,2,"湿帘打开 ",1,shed);		
			doStrategy(shed,"15",true);
			ControlTaskN.status[14]="1";
			warnsRecord(0,2,"滴灌打开 ",1,shed);	
			doStrategy(shed,"16",true);
			ControlTaskN.status[15]="1";
			warnsRecord(0,2,"雾喷打开 ",1,shed);

			
		}
		else if(status==3)
		{
		      //正常不用动作
			doStrategy(shed,"9",false);
			ControlTaskN.status[8]="0";
			warnsRecord(0,2,"水泵关闭 ",1,shed);
			doStrategy(shed,"7",false);
			ControlTaskN.status[6]="0";
			warnsRecord(0,2,"湿帘关闭 ",1,shed);		
			doStrategy(shed,"11",false);
			ControlTaskN.status[10]="0";
			warnsRecord(0,2,"第1路喷灌关闭 ",1,shed);	
			doStrategy(shed,"12",false);
			ControlTaskN.status[11]="0";
			warnsRecord(0,2,"第2路喷灌关闭 ",1,shed);	
			doStrategy(shed,"13",false);
			ControlTaskN.status[12]="0";
			warnsRecord(0,2,"第3路喷灌关闭 ",1,shed);	
			doStrategy(shed,"14",false);
			ControlTaskN.status[13]="0";
			warnsRecord(0,2,"第4路喷灌关闭",1,shed);	
			doStrategy(shed,"15",false);
			ControlTaskN.status[14]="0";
			warnsRecord(0,2,"滴灌关闭",1,shed);	
			doStrategy(shed,"16",false);
			ControlTaskN.status[15]="0";
			warnsRecord(0,2,"喷灌关闭",1,shed);
		}
		else if(status==4)
		{
			doStrategy(shed,"9",false);
			ControlTaskN.status[8]="1";
			warnsRecord(0,2,"水泵关闭 ",1,shed);
			doStrategy(shed,"7",false);
			ControlTaskN.status[6]="1";
			warnsRecord(0,2,"湿帘关闭 ",1,shed);		
			doStrategy(shed,"11",false);
			ControlTaskN.status[10]="0";
			warnsRecord(0,2,"第1路喷灌关闭 ",1,shed);	
			doStrategy(shed,"12",false);
			ControlTaskN.status[11]="0";
			warnsRecord(0,2,"第2路喷灌关闭 ",1,shed);	
			doStrategy(shed,"13",false);
			ControlTaskN.status[12]="0";
			warnsRecord(0,2,"第3路喷灌关闭 ",1,shed);	
			doStrategy(shed,"14",false);
			ControlTaskN.status[13]="0";
			warnsRecord(0,2,"第4路喷灌关闭",1,shed);	
			doStrategy(shed,"15",false);
			ControlTaskN.status[14]="0";
			warnsRecord(0,2,"滴灌关闭",1,shed);	
			doStrategy(shed,"16",false);
			ControlTaskN.status[15]="0";
			warnsRecord(0,2,"喷灌关闭",1,shed);
			doStrategy(shed,"5",true);
			ControlTaskN.status[4]="1";
			warnsRecord(0,2,"外侧窗打开 ",1,shed);
			//doStrategy(shed,"8",true);
			//warnsRecord(0,2,"风机启动",1,shed);
		}
		else if(status==5)
		{
			doStrategy(shed,"9",false);
			ControlTaskN.status[8]="0";
			warnsRecord(0,2,"水泵关闭 ",1,shed);
			doStrategy(shed,"7",false);
			ControlTaskN.status[6]="0";
			warnsRecord(0,2,"湿帘关闭 ",1,shed);		
			doStrategy(shed,"11",false);
			ControlTaskN.status[10]="0";
			warnsRecord(0,2,"第1路喷灌关闭 ",1,shed);	
			doStrategy(shed,"12",false);
			ControlTaskN.status[11]="0";
			warnsRecord(0,2,"第2路喷灌关闭 ",1,shed);	
			doStrategy(shed,"13",false);
			ControlTaskN.status[12]="01";
			warnsRecord(0,2,"第3路喷灌关闭 ",1,shed);	
			doStrategy(shed,"14",false);
			ControlTaskN.status[13]="0";
			warnsRecord(0,2,"第4路喷灌关闭",1,shed);	
			doStrategy(shed,"15",false);
			ControlTaskN.status[14]="0";
			warnsRecord(0,2,"滴灌关闭",1,shed);	
			doStrategy(shed,"16",false);
			ControlTaskN.status[15]="0";
			warnsRecord(0,2,"喷灌关闭",1,shed);
			doStrategy(shed,"5",true);
			ControlTaskN.status[4]="1";
			warnsRecord(0,2,"外侧窗打开 ",1,shed);
			doStrategy(shed,"8",true);
			ControlTaskN.status[7]="1";
			warnsRecord(0,2,"风机启动",1,shed);
			
		}
	}
	
	private int isValueField(float value){
		if(value>=0.05)
		return 5;
		else if(value>=0.01)
			return 5;
		else if(value>=-0.01)
			return 3;
		else if(value>=-0.05)
			return 2;
		else
		return 1;
	}
	
	private void doControl1(DaqData daqData,Factor factor,Shed shed){
		//CO2浓度
		if(daqData.getCoc() > factor.getCoc()*105/100){
			System.out.println("降CO2浓度策略");
			warnsRecord(0,2,"CO2浓度过高",1,shed);
			doStrategy(shed,"5",false);
		}else if(daqData.getCoc() < factor.getCoc()*105/100){
			System.out.println("升CO2浓度策略");
			warnsRecord(0,2,"CO2浓度过低",2,shed);
			doStrategy(shed,"5",true);
		}
		//光照强度
		if(daqData.getGuanghe() > factor.getGuanghe()*105/100){
			System.out.println("降光照强度策略");
			warnsRecord(0,2,"光照强度过高",3,shed);
			doStrategy(shed,"4",false);
		}else if(daqData.getGuanghe() < factor.getGuanghe()*105/100){
			System.out.println("升光照强度策略");
			warnsRecord(0,2,"光照强度过低",4,shed);
			doStrategy(shed,"4",true);
		}
		//土壤温度
		if(daqData.getDadiwendu() > factor.getDadiwendu()*105/100){
			System.out.println("降土壤温度策略");
			warnsRecord(0,1,"土壤温度过高",9,shed);
			doStrategy(shed,"8",false);
		}else if(daqData.getDadiwendu() < factor.getDadiwendu()*105/100){
			System.out.println("升土壤温度策略");
			warnsRecord(0,1,"土壤温度过低",10,shed);
			doStrategy(shed,"8",true);
		}
		//土壤湿度
		if(daqData.getTurangshuifen() > factor.getTurangshuifen()*105/100){
			System.out.println("降土壤湿度策略");
			warnsRecord(0,1,"土壤湿度过高",11,shed);
			doStrategy(shed,"10",false);
			doStrategy(shed,"11",false);
			doStrategy(shed,"12",false);
			doStrategy(shed,"13",false);
		}else if(daqData.getTurangshuifen() < factor.getTurangshuifen()*105/100){
			System.out.println("升土壤湿度策略");
			warnsRecord(0,1,"土壤湿度过低",12,shed);
			doStrategy(shed,"10",true);
			doStrategy(shed,"11",true);
			doStrategy(shed,"12",true);
			doStrategy(shed,"13",true);
		}
		//大棚温度
		if(daqData.getDaqiwendu() > factor.getDaqiwendu()*105/100){
			System.out.println("降大棚温度策略");
			warnsRecord(0,1,"大棚温度过高",5,shed);
			doStrategy(shed,"7",false);
		}else if(daqData.getDaqiwendu() < factor.getDaqiwendu()*105/100){
			System.out.println("升大棚温度策略");
			warnsRecord(0,1,"大棚温度过低",6,shed);
			doStrategy(shed,"7",true);
		}
		//大棚湿度
		if(daqData.getDaqishidu() > factor.getDaqishidu()*105/100){
			System.out.println("降大棚湿度策略");
			warnsRecord(0,1,"大棚湿度过高",7,shed);
			doStrategy(shed,"9",false);
		}else if(daqData.getDaqishidu() < factor.getDaqishidu()*105/100){
			System.out.println("升大棚湿度策略");
			warnsRecord(0,1,"大棚湿度过低",8,shed);
			doStrategy(shed,"9",true);
		}
	}
	/*
	 * 报警信息记录
	 * @param deal : [0:处理 1:未处理]
	 * @param level : [1:非常严重 2:严重 3:不严重]
	 * @param detail
	 * @param type : [1:CO2浓度过高 2:CO2浓度过低 3:光照强度过高 4:光照强度过低 5:大棚温度过高 6:大棚温度过低]
	 * [7:大棚湿度过高 8:大棚湿度过低 9:土壤温度过高 10:土壤温度过低 11:土壤湿度过高 12:土壤湿度过低 13:其他类型]
	 * @param shed
	 */
	private void warnsRecord(int deal,int level,String detail,int type,Shed shed){
		/*Warn warn = warnManager.get(shed.getId(), type);
		boolean updateable;
		if(warn != null){
			updateable = true;
		}else{
			updateable = false;
		}
		warn.setDeal(deal);
		warn.setLevel(level);
		warn.setDetail(detail);
		warn.setType(type);
		warn.setShed(shed);
		warn.setDate(new Date());
		if(updateable){
			warnManager.update(warn);
		}else{
			warnManager.save(warn);
		}*/
	}
	/*
	 * 策略执行
	 */
	private void doStrategy(Shed shed,String id,boolean open){
		AbstractEvent cEvent = null;
		String clientName = SysInitServlet.getShedId2Strs().get("realy_"+shed.getId());
		if(open){
			cEvent = new RelayEvent("realControl",id,"open");
			System.out.println("dostrategy"+id+"open");
		}else{
			cEvent = new RelayEvent("realControl",id,"close");
			System.out.println("dostrategy"+id+"close");
		}

		/*byte msg[]=SysInitServlet.cmnct.getData(clientName, cEvent);
		//byte[] msg = this.cmnct.getData(clientName, cEvent);
		System.out.println("处理结果--> "+cEvent.resolveData(msg));
		System.out.println("处理结果-->出来了 ");*/
	}
	
	public static int jisuan(Date date1, Date date2) throws Exception {
		long cha = date1.getTime() - date2.getTime();
		double result = cha * 1.0 / (1000 * 60);
		return (int)result;
	}
	
}
