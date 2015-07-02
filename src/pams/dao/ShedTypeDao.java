package pams.dao;

import java.util.List;

import pams.model.ShedType;

/**
 * 增加树形结构需要的方法
 */
public interface ShedTypeDao extends SuperDao 
{
	public void save(ShedType shed,int parentId);
	public boolean delete(int entityId);
	public List<ShedType> list(int parentId);
	/**
	 * 获取子机构的数目
	 * 0:拿一级目录
	 */
	public Long getCount(int parentId);
	/**
	 * 获取整棵树的json字符串
	 */
	public String getJsonString();
	public boolean checkExistByName(String name);
}