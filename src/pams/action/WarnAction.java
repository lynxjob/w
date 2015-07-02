package pams.action;

import java.util.List;


import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import pams.model.Warn;
import pams.service.Impl.WarnManager;
import pams.util.JsonUtil;
import pams.timer.ControlTaskN;
public class WarnAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	@Resource(name="warnManager")
	private WarnManager warnManager;
	
	/**
	 * 获取报警信息 
	 */
	public String list(){
		/*List<Warn> warns = warnManager.list(0, 10);
		//this.jsonString="大棚工作情况正常,暂无异常信息....";
		if(warns==null||warns.size()==0){
			this.jsonString="大棚工作情况正常,暂无异常信息....";
		}else{
			this.jsonString = JsonUtil.getInstance().objectToJson(Warn.class, warns);
		}*/
		this.jsonString=ControlTaskN.autoMessage;
		return "list";
	}
}
