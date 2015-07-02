package pams.dao;

import java.util.List;

import pams.model.DaqData;

/**
 * 数据采集仪Dao层接口
 * @author 恶灵骑士
 *
 */
public interface DaqDao extends SuperDao{
	/**
	 * 保存数据采集仪数据,供DaqDataBuffer用
	 * @param daqdata 
	 */
	public void save(DaqData daqdata);

	public long getTotal(String startDate, String endDate);

	public List<DaqData> list(String startDate, String endDate, int start, int limit);
}
