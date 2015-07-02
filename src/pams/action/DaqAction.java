package pams.action;

import java.util.List;

import javax.annotation.Resource;

import pams.model.DaqData;
import pams.service.Impl.DaqManager;
import pams.vo.DaqInfo;

import com.opensymphony.xwork2.ModelDriven;
/**
 * 数据采集仪-环境监测
 * @author 恶灵骑士
 *
 */
public  class DaqAction extends BaseAction implements ModelDriven<DaqInfo>{

	private static final long serialVersionUID = 1L;
	private DaqManager daqManager;
	private DaqInfo daqInfo = new DaqInfo();
	
	@Resource(name="daqManager")
	public void setDaqManager(DaqManager daqManager) {
		this.daqManager = daqManager;
	}

	@Override
	public DaqInfo getModel() {
		return this.daqInfo;
	}

	public String list(){
		Long rowCount = null;
		List<DaqData> daqdatas = null;
		if(daqInfo.getStartDate()==null||daqInfo.getEndDate()==null||
				"".equals(daqInfo.getStartDate())||"".equals(daqInfo.getEndDate())){
			rowCount = this.daqManager.getTotal();
			daqdatas = this.daqManager.list(this.daqInfo.getStart(), this.daqInfo.getLimit());
			this.jsonString = "{rowCount:"+rowCount+",result:"+this.daqManager.getJsonString(daqdatas)+"}";
					
		}else{
			rowCount = this.daqManager.getTotal(daqInfo.getStartDate(),daqInfo.getEndDate());
			daqdatas = this.daqManager.list(daqInfo.getStartDate(),daqInfo.getEndDate(),this.daqInfo.getStart(), this.daqInfo.getLimit());
			this.jsonString = "{rowCount:"+rowCount+",result:"+this.daqManager.getJsonString(daqdatas)+"}";
		}
		System.out.println(daqInfo.getStartDate()+"asdfasd");
		return "list";
	}
	
	
}
