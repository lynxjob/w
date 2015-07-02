package pams.dao;

import java.util.List;

import pams.model.Return_Goods;


/*
 * 退货单信息
 * */
public interface Return_GoodsDao extends SuperDao{
	/*
	 * 加载退货单信息列表
	 * */
	public List<Return_Goods> load();
}
