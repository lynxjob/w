package pams.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import pams.dao.StockDao;
import pams.model.Stock;

@Component("stockManager")
public class StockManager {
     private StockDao stockDao;
	
	public StockDao getStockDao() {
		return stockDao;
	}
	@Resource(name="stockDao")
	public void setStockDao(StockDao stockDao) {
		this.stockDao = stockDao;
	}
	/*
	 * 保存进幼苗单信息
	 * */
	public void save(Stock stock)
	{
		this.stockDao.save(stock);
	}
	/*
	 * 更新进幼苗单信息
	 * */
	public void update(Stock stock)
	{
		this.stockDao.update(stock);
	}
	/*
	 * 删除进幼苗单信息
	 * */
	public void delete(int entityId)
	{
		this.stockDao.delete(Stock.class, entityId);
	}
	
	/*
	 * 批量删除进幼苗单信息
	 * */
	public void delete(String idsStr)
	{
		String temp=(String) idsStr.subSequence(1,idsStr.length()-1);
		String []datas = temp.split(",");
		for(String sdata : datas)
		{
			int data = Integer.parseInt(sdata);
			this.stockDao.delete(Stock.class, data);
		}
	}
	/*
	 * 获取进幼苗单信息总数
	 * */
	public long getTotal(){
		return this.stockDao.getTotal(Stock.class);
	}

	/*
	 * 获取进幼苗单信息列表
	 * @param start
	 * @param limit
	 * */
	public List<Stock> list(final int start,final int limit){
		return this.stockDao.list(Stock.class, start, limit);
	}
	/**
	 * 获取进幼苗单信息列表,支持排序
	 * @param start
	 * @param limit
	 * @param orderBy
	 * @return
	 */
	public List<Stock> list(final int start,final int limit,String orderBy){
		return this.stockDao.list(Stock.class, start, limit,orderBy);
	}
	/**
	 * 根据进幼苗单ID获取作物
	 * @param entityId
	 * @return
	 */
	public Stock get(int entityId){
		return this.stockDao.get(Stock.class, entityId);
	}
	public String load(){
		List<Stock> stocks = this.stockDao.load();
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(Stock stock : stocks){
			sb.append(stock.toString());
		}
		if(stocks.size()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("]");
		return sb.toString();
	}
	/**
	 * 获取进幼苗单json字符串
	 * @param clients
	 * @return
	 */
	public String getJsonStr(List<Stock> stocks){
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(Stock stock : stocks){
			sb.append("{");
			sb.append("id:'"+stock.getId());
			sb.append("',fullName:'"+stock.getFullName());
			sb.append("',seedingName:'"+stock.getSeedingName());
			sb.append("',price:'"+stock.getPrice());
			sb.append("',field:'"+stock.getField());
			sb.append("',total:'"+stock.getTotal());
			sb.append("',handlerName:'"+stock.getHandlerName());
			sb.append("',amount:'"+stock.getAmount());
			sb.append("',linkmanName:'"+stock.getLinkmanName());
			sb.append("',date:'"+stock.getDate());
			sb.append("'},");
		}
		if(stocks.size()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("]");
		
		return sb.toString();
	}

}
