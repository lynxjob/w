package pams.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import pams.dao.ReserveDao;
import pams.model.Reserve;


@Component("reserveManager")
public class ReserveManager {
	private ReserveDao  reserveDao;
	
	public ReserveDao getReserveDao() {
		return reserveDao;
	}
	@Resource(name="reserveDao")
	public void setReserveDao(ReserveDao reserveDao) {
		this.reserveDao = reserveDao;
	}
	/*
	 * 保存库存信息
	 * */
	public void save(Reserve reserve)
	{
		this.reserveDao.save(reserve);
	}
	/*
	 * 更新库存信息
	 * */
	public void update(Reserve reserve)
	{
		this.reserveDao.update(reserve);
	}
	/*
	 * 删除库存信息
	 * */
	public void delete(int entityId)
	{
		this.reserveDao.delete(Reserve.class, entityId);
	}
	
	/*
	 * 批量删除库存信息
	 * */
	public void delete(String idsStr)
	{
		String temp=(String) idsStr.subSequence(1,idsStr.length()-1);
		String []datas = temp.split(",");
		for(String sdata : datas)
		{
			int data = Integer.parseInt(sdata);
			this.reserveDao.delete(Reserve.class, data);
		}
	}
	/*
	 * 获取库存信息总数
	 * */
	public long getTotal(){
		return this.reserveDao.getTotal(Reserve.class);
	}

	/*
	 * 获取库存信息列表
	 * @param start
	 * @param limit
	 * */
	public List<Reserve> list(final int start,final int limit){
		return this.reserveDao.list(Reserve.class, start, limit);
	}
	/**
	 * 获取库存信息列表,支持排序
	 * @param start
	 * @param limit
	 * @param orderBy
	 * @return
	 */
	public List<Reserve> list(final int start,final int limit,String orderBy){
		return this.reserveDao.list(Reserve.class, start, limit,orderBy);
	}
	/**
	 * 根据库存ID获取作物
	 * @param entityId
	 * @return
	 */
	public Reserve get(int entityId){
		return this.reserveDao.get(Reserve.class, entityId);
	}
	public String load(){
		List<Reserve> reserves = this.reserveDao.load();
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(Reserve reserve : reserves){
			sb.append(reserve.toString());
		}
		if(reserves.size()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("]");
		return sb.toString();
	}
	/**
	 * 获取库存json字符串
	 * @param clients
	 * @return
	 */
	public String getJsonStr(List<Reserve> reserves){
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(Reserve reserve : reserves){
			sb.append("{");
			sb.append("id:"+reserve.getId());
			sb.append(",goodsName:'"+reserve.getGoodsName());
			sb.append("',price:'"+reserve.getPrice());
			sb.append("',amount:'"+reserve.getAmount());
			sb.append("',remainder:'"+reserve.getRemainder());
			sb.append("',total:'"+reserve.getTotal());
			sb.append("'},");
		}
		if(reserves.size()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("]");
		
		return sb.toString();
	}
	/*
	 * 获取商品名称
	 * @return
	 * */
	public List<String> getName()
	{
		return this.reserveDao.getName();
	}
	/*
	 * 根据名字获取商品剩余数量
	 * */
	public double getRemainder(String name)
	{
		return this.reserveDao.getRemainder(name);
	}
	/*
	 * 根据名称获取商品的总输入
	 * */
	public double getTotal(String name)
	{
		return this.reserveDao.getTotal(name);
	}
	public int getId(String name)
	{
		return this.reserveDao.getBh(name);
	}
}
