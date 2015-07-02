package pams.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import pams.dao.DaqDao;
import pams.model.DaqData;
/**
 * 数据采集仪服务类
 * @author 恶灵骑士
 *
 */
@Component("daqManager")
public class DaqManager {
	
	private DaqDao daqdao;
	
	@Resource(name="daqDaoImpl")
	public void setDaqdao(DaqDao daqdao) {
		this.daqdao = daqdao;
	}
	/**
	 * 保存数据采集仪数据
	 * @param daqdata 
	 */
	public void save(DaqData daqdata){
		this.daqdao.save(daqdata);
	}
	/**
	 * 获取总记录数
	 * @param endDate 
	 * @param startDate 
	 * @return 总记录数
	 */
	public long getTotal(String startDate, String endDate){
		return daqdao.getTotal(startDate,endDate);
	}
	/**
	 * 分页显示
	 * @param endDate 
	 * @param startDate 
	 * @param start 分页起始位置
	 * @param limit 每页数量
	 * @return 特定页数据
	 */ 
	public List<DaqData> list(String startDate, String endDate, final int start,final int limit){
		return daqdao.list(startDate,endDate,start, limit);
	}
	/**
	 * 获取List<DaqData>的json String
	 * @return
	 */
	public String getJsonString(List<DaqData> daqdatas){
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(DaqData daqdata : daqdatas){
			sb.append(daqdata.toString());
		}
		if(daqdatas !=null && daqdatas.size()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("]");
		
		return sb.toString();
	}
	public List<DaqData> list(Integer start, int limit) {
		return daqdao.list(DaqData.class, start, limit);
	}
	public Long getTotal() {
		return daqdao.getTotal(DaqData.class);
	}
}
