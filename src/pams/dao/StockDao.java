package pams.dao;

import java.util.List;

import pams.model.Stock;

/*
 * 进幼苗信息
 * */
public interface StockDao extends  SuperDao{
	/*
	 * 加载进幼苗信息列表
	 * */
	public List<Stock> load();

}
