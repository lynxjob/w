package pams.action;

import javax.annotation.Resource;

import pams.model.Led;
import pams.model.Shed;
import pams.service.Impl.LedManage;
import pams.service.Impl.ShedManager;
import pams.servlet.SysInitServlet;
import pams.vo.LedInfo;

import com.opensymphony.xwork2.ModelDriven;

public class LedAction extends BaseAction implements ModelDriven<LedInfo>{

	private LedManage ledManage;
	private ShedManager shedManager;
	private LedInfo ledinfo = new LedInfo();
	
	@Override
	public LedInfo getModel() {
		return this.ledinfo;
	}
	@Resource(name="ledManage")
	public void setLedManage(LedManage ledManage) {
		this.ledManage = ledManage;
	}
	@Resource(name="shedManager")
	public void setShedManager(ShedManager shedManager) {
		this.shedManager = shedManager;
	}
	
	public String save()
	{
		Led led=this.ledManage.load(this.ledinfo.getShed_id(), this.ledinfo.getPinghao());
		boolean save_tag = false;
		if(led == null){
			led = new Led();
			led.setPinghao(this.ledinfo.getPinghao());
			Shed shed = this.shedManager.get(this.ledinfo.getShed_id());
			led.setShed(shed);
			save_tag = true;
		}
		led.setIP(this.ledinfo.getIP());
		led.setpWidth(this.ledinfo.getpWidth());
		led.setpHeight(this.ledinfo.getpHeight());
		led.setColor(this.ledinfo.getColor());
		led.setStart(this.ledinfo.getStart());
		led.setEnd(this.ledinfo.getEnd());
		led.setWidth(this.ledinfo.getWidth());
		led.setHeight(this.ledinfo.getHeight());
		led.setSendText(this.ledinfo.getSendText());
		if(save_tag){
			ledManage.save(led);
		}else{
			ledManage.update(led);
			save_tag = false;
		}
//		if(this.ledinfo.getled_type() == 1)
//			SysInitServlet.cmnct.restartTimer();
		this.jsonString = "{success:true,msg:'保存成功!'}";
		return "save";
		
	}
	
}
