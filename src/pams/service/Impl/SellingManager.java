package pams.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import pams.dao.SellingDao;
import pams.model.Selling;

@Component("sellingManager")
public class SellingManager {

	private SellingDao sellingDao;
	
	public SellingDao getSellingDao() {
		return sellingDao;
	}
	@Resource(name="sellingDao")
	public void setSellingDao(SellingDao sellingDao) {
		this.sellingDao = sellingDao;
	}
	/*
	 * 保存商品销售单信息
	 * */
	public void save(Selling selling)
	{
		this.sellingDao.save(selling);
	}
	/*
	 * 更新商品销售单信息
	 * */
	public void update(Selling selling)
	{
		this.sellingDao.update(selling);
	}
	/*
	 * 删除商品销售单信息
	 * */
	public void delete(int entityId)
	{
		this.sellingDao.delete(Selling.class, entityId);
	}
	
	/*
	 * 批量删除商品销售单信息
	 * */
	public void delete(String idsStr)
	{
		String temp=(String) idsStr.subSequence(1,idsStr.length()-1);
		String []datas = temp.split(",");
		for(String sdata : datas)
		{
			int data = Integer.parseInt(sdata);
			this.sellingDao.delete(Selling.class, data);
		}
	}
	/*
	 * 获取商品销售单信息总数
	 * */
	public long getTotal(){
		return this.sellingDao.getTotal(Selling.class);
	}

	/*
	 * 获取商品销售单信息列表
	 * @param start
	 * @param limit
	 * */
	public List<Selling> list(final int start,final int limit){
		return this.sellingDao.list(Selling.class, start, limit);
	}
	/**
	 * 获取商品销售单信息列表,支持排序
	 * @param start
	 * @param limit
	 * @param orderBy
	 * @return
	 */
	public List<Selling> list(final int start,final int limit,String orderBy){
		return this.sellingDao.list(Selling.class, start, limit,orderBy);
	}
	/**
	 * 根据商品销售单ID获取作物
	 * @param entityId
	 * @return
	 */
	public Selling get(int entityId){
		return this.sellingDao.get(Selling.class, entityId);
	}
	public String load(){
		List<Selling> sellings = this.sellingDao.load();
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(Selling selling : sellings){
			sb.append(selling.toString());
		}
		if(sellings.size()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("]");
		return sb.toString();
	}
	/**
	 * 获取商品销售单json字符串
	 * @param clients
	 * @return
	 */
	public String getJsonStr(List<Selling> sellings){
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(Selling selling : sellings){
			sb.append("{");
			sb.append("id:"+selling.getId());
			sb.append(",fullName:'"+selling.getFullName());
			sb.append("',goodsName:'"+selling.getGoodsName());
			sb.append("',price:'"+selling.getPrice());
			sb.append("',total:'"+selling.getTotal());
			sb.append("',handlerName:'"+selling.getHandlerName());
			sb.append("',amount:'"+selling.getAmount());
			sb.append("',linkmanName:'"+selling.getLinkmanName());
			sb.append("',date:'"+selling.getDate());
			sb.append("'},");
		}
		if(sellings.size()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("]");
		
		return sb.toString();
	}

}
