package pams.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import pams.dao.Return_SaleDao;
import pams.model.Return_Sale;


@Component("return_SaleManager")
public class Return_SaleManager {

	private Return_SaleDao return_SaleDao;
	
	public Return_SaleDao getReturn_SaleDao() {
		return return_SaleDao;
	}
	@Resource(name="return_SaleDao")
	public void setReturn_SaleDao(Return_SaleDao returnSaleDao) {
		return_SaleDao = returnSaleDao;
	}
	/*
	 * 保存商品销售退单信息
	 * */
	public void save(Return_Sale return_Sale)
	{
		this.return_SaleDao.save(return_Sale);
	}
	/*
	 * 更新商品销售退单信息
	 * */
	public void update(Return_Sale return_Sale)
	{
		this.return_SaleDao.update(return_Sale);
	}
	/*
	 * 删除商品销售退单信息
	 * */
	public void delete(int entityId)
	{
		this.return_SaleDao.delete(Return_Sale.class, entityId);
	}
	
	/*
	 * 批量删除商品销售退单信息
	 * */
	public void delete(String idsStr)
	{
		String temp=(String) idsStr.subSequence(1,idsStr.length()-1);
		String []datas = temp.split(",");
		for(String sdata : datas)
		{
			int data = Integer.parseInt(sdata);
			this.return_SaleDao.delete(Return_Sale.class, data);
		}
	}
	/*
	 * 获取商品销售退单信息总数
	 * */
	public long getTotal(){
		return this.return_SaleDao.getTotal(Return_Sale.class);
	}

	/*
	 * 获取商品销售退单信息列表
	 * @param start
	 * @param limit
	 * */
	public List<Return_Sale> list(final int start,final int limit){
		return this.return_SaleDao.list(Return_Sale.class, start, limit);
	}
	/**
	 * 获取商品销售退单信息列表,支持排序
	 * @param start
	 * @param limit
	 * @param orderBy
	 * @return
	 */
	public List<Return_Sale> list(final int start,final int limit,String orderBy){
		return this.return_SaleDao.list(Return_Sale.class, start, limit,orderBy);
	}
	/**
	 * 根据商品销售退单ID获取作物
	 * @param entityId
	 * @return
	 */
	public Return_Sale get(int entityId){
		return this.return_SaleDao.get(Return_Sale.class, entityId);
	}
	public String load(){
		List<Return_Sale> return_Sales = this.return_SaleDao.load();
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(Return_Sale return_Sale : return_Sales){
			sb.append(return_Sale.toString());
		}
		if(return_Sales.size()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("]");
		return sb.toString();
	}
	/**
	 * 获取商品销售退单json字符串
	 * @param clients
	 * @return
	 */
	public String getJsonStr(List<Return_Sale> return_Sales){
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(Return_Sale return_Sale : return_Sales){
			sb.append("{");
			sb.append("id:"+return_Sale.getId());
			sb.append(",fullName:'"+return_Sale.getFullName());
			sb.append("',goodsName:'"+return_Sale.getGoodsName());
			sb.append("',price:'"+return_Sale.getPrice());
			sb.append("',total:'"+return_Sale.getTotal());
			sb.append("',handlerName:'"+return_Sale.getHandlerName());
			sb.append("',amount:'"+return_Sale.getAmount());
			sb.append("',linkmanName:'"+return_Sale.getLinkmanName());
			sb.append("',date:'"+return_Sale.getDate());
			sb.append("',des:'"+return_Sale.getDes());
			sb.append("'},");
		}
		if(return_Sales.size()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("]");
		
		
		return sb.toString();
	}

}
