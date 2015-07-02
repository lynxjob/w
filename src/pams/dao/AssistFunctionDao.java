package pams.dao;

import java.util.List;

import pams.model.AssistFunction;

public interface AssistFunctionDao extends SuperDao{
	
	public void save(AssistFunction function ,int parentId);

	public List<AssistFunction> list(int parentId);
	
	/**
	 * 获取子机构的数目
	 * 0:拿一级目录
	 */
	public Long getCount(int parentId);
	/**
	 * 获取整棵树的json字符串
	 */
	public String getJsonString();
	public String list();
	public boolean delete(int entityId);
	public boolean checkExistByName(String name);
}
