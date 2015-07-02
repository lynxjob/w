package pams.dao;

import java.util.List;

import pams.model.Selling;

/*
 * 销售信息
 * */
public interface SellingDao extends SuperDao{
	/*
	 * 加载销售信息列表
	 * */
	public List<Selling> load();
}
