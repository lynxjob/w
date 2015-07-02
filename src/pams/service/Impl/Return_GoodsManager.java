package pams.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import pams.dao.Return_GoodsDao;
import pams.model.Return_Goods;

@Component("return_GoodsManager")
public class Return_GoodsManager {
	
	private Return_GoodsDao return_GoodsDao;
	
	public Return_GoodsDao getReturn_GoodsDao() {
		return return_GoodsDao;
	}
	@Resource(name="return_GoodsDao")
	public void setReturn_GoodsDao(Return_GoodsDao returnGoodsDao) {
		return_GoodsDao = returnGoodsDao;
	}
	/*
	 * 保存商品退货单信息
	 * */
	public void save(Return_Goods return_Goods)
	{
		this.return_GoodsDao.save(return_Goods);
	}
	/*
	 * 更新商品退货单信息
	 * */
	public void update(Return_Goods return_Goods)
	{
		this.return_GoodsDao.update(return_Goods);
	}
	/*
	 * 删除商品退货单信息
	 * */
	public void delete(int entityId)
	{
		this.return_GoodsDao.delete(Return_Goods.class, entityId);
	}
	
	/*
	 * 批量删除商品退货单信息
	 * */
	public void delete(String idsStr)
	{
		String temp=(String) idsStr.subSequence(1,idsStr.length()-1);
		String []datas = temp.split(",");
		for(String sdata : datas)
		{
			int data = Integer.parseInt(sdata);
			this.return_GoodsDao.delete(Return_Goods.class, data);
		}
	}
	/*
	 * 获取商品退货单信息总数
	 * */
	public long getTotal(){
		return this.return_GoodsDao.getTotal(Return_Goods.class);
	}

	/*
	 * 获取商品退货单信息列表
	 * @param start
	 * @param limit
	 * */
	public List<Return_Goods> list(final int start,final int limit){
		return this.return_GoodsDao.list(Return_Goods.class, start, limit);
	}
	/**
	 * 获取商品退货单信息列表,支持排序
	 * @param start
	 * @param limit
	 * @param orderBy
	 * @return
	 */
	public List<Return_Goods> list(final int start,final int limit,String orderBy){
		return this.return_GoodsDao.list(Return_Goods.class, start, limit,orderBy);
	}
	/**
	 * 根据商品退货单ID获取作物
	 * @param entityId
	 * @return
	 */
	public Return_Goods get(int entityId){
		return this.return_GoodsDao.get(Return_Goods.class, entityId);
	}
	public String load(){
		List<Return_Goods> goods = this.return_GoodsDao.load();
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(Return_Goods good : goods){
			sb.append(good.toString());
		}
		if(goods.size()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("]");
		return sb.toString();
	}
	/**
	 * 获取商品退货单json字符串
	 * @param clients
	 * @return
	 */
	public String getJsonStr(List<Return_Goods> goods){
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(Return_Goods good : goods){
			sb.append("{");
			sb.append("id:"+good.getId());
			sb.append(",fullName:'"+good.getFullName());
			sb.append("',seedingName:'"+good.getSeedingName());
			sb.append("',price:'"+good.getPrice());
			sb.append("',field:'"+good.getField());
			sb.append("',total:'"+good.getTotal());
			sb.append("',handlerName:'"+good.getHandlerName());
			sb.append("',amount:'"+good.getAmount());
			sb.append("',linkmanName:'"+good.getLinkmanName());
			sb.append("',date:'"+good.getDate());
			sb.append("',des:'"+good.getDes());
			
			sb.append("'},");
		}
		if(goods.size()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("]");
		
		return sb.toString();
	}

}
