

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

/**
 * 大棚决策控制
 * @author 恶灵骑士
 *
 */
public class ControlTaskTest extends CTimerTask {
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
	
	public ControlTask(ICommunicator cmnct){
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
	public void run() {
		AbstractEvent daqEvent = new DaqEvent(11);
		if(sheds == null){
			sheds = shedManager.list(0);
		}
		for(Shed shed : sheds){
//			System.out.println(shed.getId());
			String clientName = SysInitServlet.getShedId2Strs().get("daq_"+shed.getId());
			boolean autoTag = SysInitServlet.getShedId2Auto().get("auto_"+shed.getId());
//			System.out.println("wo"+shed.getId());
			System.out.println("ClientName-->"+clientName);
			System.out.println("autoTag-->"+autoTag);
			byte[] msg = this.cmnct.getData(clientName, daqEvent);
//			System.out.println("hello!"+msg);
			Crop crop = shed.getCrop();
			if(autoTag && msg!=null && crop!=null){
				DaqData daqData = (DaqData)daqEvent.resolveData(msg);
				Factor factor = (Factor)cropManager.getCondition(crop.getId());
				doControl(daqData,factor,shed);
			}
		}
	}
	/*
	 * 策略控制(Factor里存的是平均值,
	 * 上下波动范围为5%)
	 * @param daqData
	 * @param factor
	 */
	private void doControl(DaqData daqData,Factor factor,Shed shed){
		//CO2浓度
		if(daqData.getCoc() > factor.getCoc()*105/100){
			System.out.println("降CO2浓度策略");
			warnsRecord(0,2,"CO2浓度过高",1,shed);
			doStrategy(shed,"8",false);
		}else if(daqData.getCoc() < factor.getCoc()*105/100){
			System.out.println("升CO2浓度策略");
			warnsRecord(0,2,"CO2浓度过低",2,shed);
			doStrategy(shed,"8",true);
		}
		//光照强度
		if(daqData.getGuanghe() > factor.getGuanghe()*105/100){
			System.out.println("降光照强度策略");
			warnsRecord(0,2,"光照强度过高",3,shed);
			doStrategy(shed,"10",false);
		}else if(daqData.getGuanghe() < factor.getGuanghe()*105/100){
			System.out.println("升光照强度策略");
			warnsRecord(0,2,"光照强度过低",4,shed);
			doStrategy(shed,"10",true);
		}
		//土壤温度
		if(daqData.getDadiwendu() > factor.getDadiwendu()*105/100){
			System.out.println("降土壤温度策略");
			warnsRecord(0,1,"土壤温度过高",9,shed);
			doStrategy(shed,"15",false);
		}else if(daqData.getDadiwendu() < factor.getDadiwendu()*105/100){
			System.out.println("升土壤温度策略");
			warnsRecord(0,1,"土壤温度过低",10,shed);
			doStrategy(shed,"15",true);
		}
		//土壤湿度
		if(daqData.getTurangshuifen() > factor.getTurangshuifen()*105/100){
			System.out.println("降土壤湿度策略");
			warnsRecord(0,1,"土壤湿度过高",11,shed);
			doStrategy(shed,"11",false);
			doStrategy(shed,"12",false);
			doStrategy(shed,"13",false);
			doStrategy(shed,"14",false);
		}else if(daqData.getTurangshuifen() < factor.getTurangshuifen()*105/100){
			System.out.println("升土壤湿度策略");
			warnsRecord(0,1,"土壤湿度过低",12,shed);
			doStrategy(shed,"11",true);
			doStrategy(shed,"12",true);
			doStrategy(shed,"13",true);
			doStrategy(shed,"14",true);
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
			doStrategy(shed,"16",false);
		}else if(daqData.getDaqishidu() < factor.getDaqishidu()*105/100){
			System.out.println("升大棚湿度策略");
			warnsRecord(0,1,"大棚湿度过低",8,shed);
			doStrategy(shed,"16",true);
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
		Warn warn = warnManager.get(shed.getId(), type);
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
		}
	}
	/*
	 * 策略执行
	 */
	private void doStrategy(Shed shed,String id,boolean open){
		AbstractEvent cEvent = null;
		String clientName = SysInitServlet.getShedId2Strs().get("realy_"+shed.getId());
		if(open){
			cEvent = new RelayEvent("realControl",id,"open");
		}else{
			cEvent = new RelayEvent("realControl",id,"close");
		}
		byte[] msg = this.cmnct.getData(clientName, cEvent);
		System.out.println("处理结果--> "+cEvent.resolveData(msg));
	}
}
