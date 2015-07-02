package pams.dao;

import java.util.List;

import pams.model.TPower;

/**
 * @author cjl
 *权限记录Dao
 */
public interface TPowerDao extends SuperDao
{
	/**
	 * parentId=0 ,取一节节点
	 * parentId=-1，取出所有节点
	 * 以外，取出该节点的所有子节点
	 */
	public List<TPower> list(int parentId);
}