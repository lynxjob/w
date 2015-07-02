package pams.dao;

import java.util.List;

import pams.model.Return_Sale;

/*
 * 销售退单信息
 * */
public interface Return_SaleDao extends SuperDao {

	/*
	 * 加载退销售单信息列表
	 * */
	public List<Return_Sale> load();
}
