package pams.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import pams.dao.AssistFunctionDao;
import pams.model.AssistFunction;

@Component("assistFunctionManager")
public class AssistFunctionManager {

	private AssistFunctionDao assistFunctionDao;

	public AssistFunctionDao getAssistFunctionDao() {
		return assistFunctionDao;
	}
	@Resource(name="assistFunctionDaoImpl")
	public void setAssistFunctionDao(AssistFunctionDao assistFunctionDao) {
		this.assistFunctionDao = assistFunctionDao;
	}
	public void save(AssistFunction function ,int parentId)
	{
		this.assistFunctionDao.save(function, parentId);
	}
	public void save(AssistFunction function)
	{
		this.assistFunctionDao.save(function);
	}
	public String list()
	{
//		return this.assistFunctionDao.getJsonString();
		return this.assistFunctionDao.list();
	}
	public List<AssistFunction> list(int parentId)
	{
		return this.assistFunctionDao.list(parentId);
	}
	public AssistFunction get(int entityId)
	{
		return this.assistFunctionDao.get(AssistFunction.class, entityId);
	}
	public long getCount(int parentId)
	{
		return this.assistFunctionDao.getCount(parentId);
	}
	public boolean checkExistByName(String name){
		return this.assistFunctionDao.checkExistByName(name);
	}
	public boolean delete(int entityId) 
	{
			return this.assistFunctionDao.delete(entityId);
	}
}
