package pams.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import pams.dao.ShedTypeDao;
import pams.model.ShedType;

/**
 * @author cjl
 * 结构列表操作
 */
@Component("shedTypeManager")
public class ShedTypeManager 
{
	private ShedTypeDao shedTypeDao;

	@Resource(name="shedTypeDaoImpl")
	public void setShedTypeDao(ShedTypeDao shedDao )
	{
		this.shedTypeDao = shedDao; 
	}
	
	public String list()
	{
		return this.shedTypeDao.getJsonString();
	}
	
	public void save(ShedType org,int parentId)
	{
		this.shedTypeDao.save(org,parentId);
	}
	
	public ShedType get(int entityId)
	{
		return this.shedTypeDao.get(ShedType.class,entityId);
	}
	public void update(ShedType org)
	{
		this.shedTypeDao.update(org);
	}
	
	public void save(ShedType shedType){
		this.shedTypeDao.save(shedType);
	}
	public boolean delete(int entityId) 
	{
			return this.shedTypeDao.delete(entityId);
	}
	
	public List<ShedType> list(int parentId)
	{
		return this.shedTypeDao.list(parentId);
	}
	public Long getCount(int parentId)
	{
		return this.shedTypeDao.getCount(parentId);
	}
	
	public boolean checkExistByName(String name){
		return this.shedTypeDao.checkExistByName(name);
	}
}