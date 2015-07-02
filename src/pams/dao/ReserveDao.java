package pams.dao;

import java.util.List;

import pams.model.Reserve;
/*
 * 库存信息
 * */
public interface ReserveDao extends SuperDao{
	/*
	 * 加载库存信息列表
	 * */
	public List<Reserve> load();
	/*
	 * 获取库存中作物名称列表
	 * */
	public List<String> getName();
	/*
	 * 根据名字获取作物的剩余数量
	 * @param name
	 * @return
	 * */
	public double getRemainder(String name);
	/*
	 * 根据名称获取作物的总收入
	 * @param
	 * @return
	 * */
	public double getTotal(String name);
	
	public int getBh(String name);
}
