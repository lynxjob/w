package pams.dao;

import java.util.List;
/**
 * @author cjl
 * 公用操作接口
 */
public interface SuperDao 
{
	//保存
	public void save(Object entity);
	//更新
	public void update(Object entity);
	//删除一个
	public <T> void delete(Class<T> entityClass,int entityId);
	//删除多个
	public <T> void delete(Class<T> entityClass,String idsStr);
	//获取单个对象
	public <T> T get(Class<T> entityClass,int entityId);
	//获取总记录数
	public <T> Long getTotal(Class<T> entityClass);
	//获取分页数据，可排列
	public <T> List<T> list(Class<T> entityClass,final int start,final int limit,String oderBy);
	//获取分页数据
	public <T> List<T> list(Class<T> entityClass,final int start,final int limit);
}